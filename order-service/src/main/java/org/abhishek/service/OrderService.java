package org.abhishek.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abhishek.dto.*;
import org.abhishek.exception.BadRequestException;
import org.abhishek.exception.ConflictException;
import org.abhishek.exception.DatabaseException;
import org.abhishek.exception.ResourceNotFoundException;
import org.abhishek.model.Order;
import org.abhishek.model.OrderItemList;
import org.abhishek.repository.OrderItemRepository;
import org.abhishek.repository.OrderRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final WebClient.Builder webClientBuilder;

    @Transactional
    public void Create(OrderRequest orderRequest){
        List<OrderItem> orderItemList=new ArrayList<>();
        log.info("Starting the service.");
        try{
            List<OrderItem> inventoryItemList = orderRequest.getProductList().stream().map(productItem -> {
                InventoryResponse inventoryResponse = webClientBuilder.build()
                        .get()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .scheme("http")
//                                    .host("localhost")
                                        .host("inventory-service")
//                                    .port(8082)
                                        .path("/api/inventory/product")
                                        .queryParam("productId", productItem.getProductId())
                                        .build())
                        .retrieve()
                        .bodyToMono(InventoryResponse.class)
                        .block();
                log.info(productItem.toString());
                log.info(inventoryResponse.toString());
                if (productItem.getAmount() > inventoryResponse.getAmount()) {
                    throw new ResourceNotFoundException("The required amount for order of product with id:" + productItem.getProductId() + " is not sufficient only" + inventoryResponse.getAmount() + " items present.");
                }
                OrderItem orderItem = OrderItem
                        .builder()
                        .productId(productItem.getProductId())
                        .price(inventoryResponse.getPrice())
                        .amount(productItem.getAmount())
                        .build();
                orderItemList.add(orderItem);

                return OrderItem
                        .builder()
                        .productId(productItem.getProductId())
                        .price(inventoryResponse.getPrice())
                        .amount(inventoryResponse.getAmount() - productItem.getAmount())
                        .build();
            }).toList();

            inventoryItemList.forEach(orderItem -> {
                InventoryRequest inventoryRequest = InventoryRequest
                        .builder()
                        .amount(orderItem.getAmount())
                        .productId(orderItem.getProductId())
                        .price(orderItem.getPrice())
                        .build();
                webClientBuilder.build()
                        .put()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .scheme("http")
//                                    .host("localhost")
                                        .host("inventory-service")
//                                    .port(8082)
                                        .path("/api/inventory/product")
                                        .queryParam("productId", orderItem.getProductId())
                                        .build())
                        .bodyValue(inventoryRequest)
                        .retrieve()
                        .toBodilessEntity()
                        .block();
            });

            OrderItemList orderItemList1 = OrderItemList
                    .builder()
                    .orderItemList(orderItemList)
                    .build();
            OrderItemList savedOrderItemList = orderItemRepository.save(orderItemList1);
            Integer total = savedOrderItemList.getOrderItemList().stream()
                    .mapToInt(orderItem -> orderItem.getAmount() * orderItem.getPrice())
                    .sum();


            Order order = Order
                    .builder()
                    .orderItemList(savedOrderItemList.getId())
                    .total(total)
                    .userId(orderRequest.getUserId())
                    .build();
            orderRepository.save(order);
            log.info("ending the service");
        }
        catch (WebClientResponseException.NotFound ex) {
            throw new ResourceNotFoundException("Inventory service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            throw new BadRequestException("Invalid request sent to inventory service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            throw new DatabaseException("Inventory service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Order violates database constraints");
        }
        catch (DataAccessException ex) {
            throw new DatabaseException("Database operation failed");
        }
    }

    public void Delete(Long id){
        try{
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Order was not found with id: " + id));
            OrderItemList orderItemList = orderItemRepository.findById(order.getOrderItemList())
                    .orElseThrow(() -> new ResourceNotFoundException("Order with id: " + order.getId() + " was found but order item list with id: " + order.getOrderItemList() + " was not found."));
            log.info("Order with id: {} was deleted.", order.getId());
            orderItemRepository.delete(orderItemList);
            orderRepository.delete(order);
        }
        catch (WebClientResponseException.NotFound ex) {
            throw new ResourceNotFoundException("Inventory service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            throw new BadRequestException("Invalid request sent to inventory service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            throw new DatabaseException("Inventory service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Order violates database constraints");
        }
        catch (DataAccessException ex) {
            throw new DatabaseException("Database operation failed");
        }
    }

    public List<OrderResponse> Get(){
        try{
            List<Order> orderList = orderRepository.findAll();
            List<OrderResponse> orderResponseList = orderList.stream().map(order -> {
                OrderItemList orderItemList = orderItemRepository.findById(order.getOrderItemList())
                        .orElseThrow(() -> new ResourceNotFoundException("Order with id: " + order.getId() + " was found but order item list with id: " + order.getOrderItemList() + " was not found."));
                return mapToResponse(order, orderItemList);
            }).toList();
            log.info("All the order list fetched.");
            return orderResponseList;
        }
        catch (WebClientResponseException.NotFound ex) {
            throw new ResourceNotFoundException("Inventory service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            throw new BadRequestException("Invalid request sent to inventory service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            throw new DatabaseException("Inventory service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Order violates database constraints");
        }
        catch (DataAccessException ex) {
            throw new DatabaseException("Database operation failed");
        }
    }

    public OrderResponse Get(Long id){
        try{
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
            OrderItemList orderItemList = orderItemRepository.findById(order.getOrderItemList())
                    .orElseThrow(() -> new ResourceNotFoundException("Order with id: " + order.getId() + " was found but order item list with id: " + order.getOrderItemList() + " was not found."));
            OrderResponse orderResponse = mapToResponse(order, orderItemList);
            log.info("Order with id: {} was fetched.", order.getId());
            return orderResponse;
        }
        catch (WebClientResponseException.NotFound ex) {
            throw new ResourceNotFoundException("Inventory service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            throw new BadRequestException("Invalid request sent to inventory service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            throw new DatabaseException("Inventory service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Order violates database constraints");
        }
        catch (DataAccessException ex) {
            throw new DatabaseException("Database operation failed");
        }
    }


//    Helpers

    private OrderResponse mapToResponse(Order order, OrderItemList orderItemList){
        OrderResponse orderResponse=OrderResponse
                .builder()
                .id(order.getId())
                .total(order.getTotal())
                .userId(order.getUserId())
                .orderItemList(orderItemList.getOrderItemList())
                .build();
        return orderResponse;
    }
}
