package org.abhishek.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abhishek.dto.InventoryRequest;
import org.abhishek.dto.InventoryResponse;
import org.abhishek.dto.ProductResponse;
import org.abhishek.exception.BadRequestException;
import org.abhishek.exception.ConflictException;
import org.abhishek.exception.DatabaseException;
import org.abhishek.exception.ResourceNotFoundException;
import org.abhishek.model.Inventory;
import org.abhishek.repository.InventoryRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final WebClient.Builder webClientBuilder;

    public void Create(InventoryRequest inventoryRequest){
        log.info("Started the CREATE request for Inventory.");
        try{
            ProductResponse productResponse=webClientBuilder.build()
                    .get()
                    .uri(
                            uriBuilder -> uriBuilder
                                    .scheme("https")
                                    .host("market-place-product-o37u.onrender.com")
                                    .path("/api/product/item")
                                    .queryParam("id", inventoryRequest.getProductId()) // <-- IMPORTANT
                                    .build()
                    )
                    .retrieve()
                    .bodyToMono(ProductResponse.class)
                    .block();
            log.info("Product Response found a product with given id");
            Inventory inventory = mapFromRequest(inventoryRequest);
            log.info("Saving the inventory");
            Inventory savedInventory = inventoryRepository.save(inventory);
            log.info("Inventory with id: {} was saved", savedInventory.getId());
        }catch (WebClientResponseException.NotFound ex) {
            log.info("Inventory service resource not found");
            throw new ResourceNotFoundException("Inventory service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            log.info("Invalid request sent to Inventory service");
            throw new BadRequestException("Invalid request sent to Inventory service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            log.info("Inventory service unavailable");
            throw new DatabaseException("Inventory service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            log.info("Product violates database constraints");
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            log.info("Database operation failed");
            throw new DatabaseException("Database operation failed");
        }

    }

    @Transactional
    public void Update(InventoryRequest inventoryRequest, String id){
        log.info("Started the UPDATE request for Inventory with id: {}",id);
        try{
            Inventory inventory = inventoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id:" + id));
            log.info("Inventory found for updating");
            inventory.setProductId(inventoryRequest.getProductId());
            inventory.setAmount(inventoryRequest.getAmount());
            inventory.setPrice(inventoryRequest.getPrice());
            log.info("Inventory with id: {} was updated.", inventory.getId());
            inventoryRepository.save(inventory);
        }catch (WebClientResponseException.NotFound ex) {
            log.info("Inventory service resource not found");
            throw new ResourceNotFoundException("Inventory service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            log.info("Invalid request sent to Inventory service");
            throw new BadRequestException("Invalid request sent to Inventory service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            log.info("Inventory service unavailable");
            throw new DatabaseException("Inventory service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            log.info("Product violates database constraints");
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            log.info("Database operation failed");
            throw new DatabaseException("Database operation failed");
        }
    }
    
    @Transactional
    public void UpdateByProductId(InventoryRequest inventoryRequest, String productId){
        log.info("Started the UPDATE BY PRODUCT ID for Inventory with productId: {}", productId);
        try{
            System.out.println(inventoryRequest.getAmount());
            System.out.println(inventoryRequest.getPrice());
            Inventory inventory = inventoryRepository.findByProductId(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with productId:" + productId));
            inventory.setProductId(inventoryRequest.getProductId());
            inventory.setAmount(inventoryRequest.getAmount());
            inventory.setPrice(inventoryRequest.getPrice());
            log.info("Inventory with id: {} was updated.", inventory.getId());
            inventoryRepository.save(inventory);
        }catch (WebClientResponseException.NotFound ex) {
            log.info("Inventory service resource not found");
            throw new ResourceNotFoundException("Inventory service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            log.info("Invalid request sent to Inventory service");
            throw new BadRequestException("Invalid request sent to Inventory service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            log.info("Inventory service unavailable");
            throw new DatabaseException("Inventory service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            log.info("Product violates database constraints");
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            log.info("Database operation failed");
            throw new DatabaseException("Database operation failed");
        }
        
    }

    public void Delete(String id){
        log.info("Started Delete for inventory with id: {}", id);
        try{
            Inventory inventory = inventoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id:" + id));
            log.info("Inventory with id: {} was deleted.", inventory.getId());
            inventoryRepository.delete(inventory);
        }catch (WebClientResponseException.NotFound ex) {
            log.info("Inventory service resource not found");
            throw new ResourceNotFoundException("Inventory service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            log.info("Invalid request sent to Inventory service");
            throw new BadRequestException("Invalid request sent to Inventory service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            log.info("Inventory service unavailable");
            throw new DatabaseException("Inventory service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            log.info("Product violates database constraints");
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            log.info("Database operation failed");
            throw new DatabaseException("Database operation failed");
        }
    }

    public InventoryResponse Get(String id){
        log.info("Started GET for inventory with id: {}", id);
        try{
            Inventory inventory = inventoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id:" + id));
            log.info("The inventory for GET request found for id: {}", id);
            InventoryResponse inventoryResponse = mapToResponse(inventory);
            log.info("Inventory with id: {} was fetched.", inventory.getId());
            return inventoryResponse;
        }catch (WebClientResponseException.NotFound ex) {
            log.info("Inventory service resource not found");
            throw new ResourceNotFoundException("Inventory service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            log.info("Invalid request sent to Inventory service");
            throw new BadRequestException("Invalid request sent to Inventory service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            log.info("Inventory service unavailable");
            throw new DatabaseException("Inventory service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            log.info("Product violates database constraints");
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            log.info("Database operation failed");
            throw new DatabaseException("Database operation failed");
        }
    }

    public List<InventoryResponse> Get(){
        log.info("Started GET ALL for Inventory");
        try{
            List<Inventory> inventoryList = inventoryRepository.findAll();
            log.info("The Inventory List for GET ALL found.");
            List<InventoryResponse> inventoryResponseList = inventoryList.stream().map(this::mapToResponse).toList();
            log.info("All the inventory list fetched.");
            return inventoryResponseList;
        }catch (WebClientResponseException.NotFound ex) {
            log.info("Inventory service resource not found");
            throw new ResourceNotFoundException("Inventory service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            log.info("Invalid request sent to Inventory service");
            throw new BadRequestException("Invalid request sent to Inventory service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            log.info("Inventory service unavailable");
            throw new DatabaseException("Inventory service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            log.info("Product violates database constraints");
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            log.info("Database operation failed");
            throw new DatabaseException("Database operation failed");
        }
    }

    public InventoryResponse GetByProductId(String productId){
        log.info("Started GET BY PRODUCT ID for inventory with product id: {}", productId);
        try{
            Inventory inventory = inventoryRepository.findByProductId(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with product id:" + productId));
            log.info("Inventory request for GET BY PRODUCT ID was found with id: {}.", productId);
            InventoryResponse inventoryResponse = mapToResponse(inventory);
            log.info("Inventory with id : {} was found with the productId: {}", inventory.getId(), inventory.getProductId());
            return inventoryResponse;
        }catch (WebClientResponseException.NotFound ex) {
            log.info("Inventory service resource not found");
            throw new ResourceNotFoundException("Inventory service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            log.info("Invalid request sent to Inventory service");
            throw new BadRequestException("Invalid request sent to Inventory service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            log.info("Inventory service unavailable");
            throw new DatabaseException("Inventory service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            log.info("Product violates database constraints");
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            log.info("Database operation failed");
            throw new DatabaseException("Database operation failed");
        }
    }

//    Helpers
    private Inventory mapFromRequest(InventoryRequest inventoryRequest){
        Inventory  inventory=Inventory
                .builder()
                .productId(inventoryRequest.getProductId())
                .price(inventoryRequest.getPrice())
                .amount(inventoryRequest.getAmount())
                .build();
        return inventory;
    }

    private InventoryResponse mapToResponse(Inventory inventory){
        InventoryResponse inventoryResponse=InventoryResponse
                .builder()
                .id(inventory.getId())
                .productId(inventory.getProductId())
                .price(inventory.getPrice())
                .amount(inventory.getAmount())
                .build();
        return inventoryResponse;
    }
}
