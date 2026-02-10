package org.abhishek.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abhishek.dto.InventoryRequest;
import org.abhishek.dto.InventoryResponse;
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
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public void Create(InventoryRequest inventoryRequest){
        try{
            Inventory inventory = mapFromRequest(inventoryRequest);
            Inventory savedInventory = inventoryRepository.save(inventory);
            log.info("Inventory with id: {} was saved", savedInventory.getId());
        }catch (WebClientResponseException.NotFound ex) {
            throw new ResourceNotFoundException("Inventory service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            throw new BadRequestException("Invalid request sent to inventory service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            throw new DatabaseException("Inventory service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            throw new DatabaseException("Database operation failed");
        }

    }

    @Transactional
    public void Update(InventoryRequest inventoryRequest, String id){
        try{
            Inventory inventory = inventoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id:" + id));
            inventory.setProductId(inventoryRequest.getProductId());
            inventory.setAmount(inventoryRequest.getAmount());
            inventory.setPrice(inventoryRequest.getPrice());
            log.info("Inventory with id: {} was updated.", inventory.getId());
            inventoryRepository.save(inventory);
        }catch (WebClientResponseException.NotFound ex) {
            throw new ResourceNotFoundException("Inventory service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            throw new BadRequestException("Invalid request sent to inventory service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            throw new DatabaseException("Inventory service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            throw new DatabaseException("Database operation failed");
        }
    }
    
    @Transactional
    public void UpdateByProductId(InventoryRequest inventoryRequest, String productId){
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
            throw new ResourceNotFoundException("Inventory service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            throw new BadRequestException("Invalid request sent to inventory service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            throw new DatabaseException("Inventory service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            throw new DatabaseException("Database operation failed");
        }
        
    }

    public void Delete(String id){
        try{
            Inventory inventory = inventoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id:" + id));
            log.info("Inventory with id: {} was deleted.", inventory.getId());
            inventoryRepository.delete(inventory);
        }catch (WebClientResponseException.NotFound ex) {
            throw new ResourceNotFoundException("Inventory service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            throw new BadRequestException("Invalid request sent to inventory service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            throw new DatabaseException("Inventory service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            throw new DatabaseException("Database operation failed");
        }
    }

    public InventoryResponse Get(String id){
        try{
            Inventory inventory = inventoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id:" + id));
            InventoryResponse inventoryResponse = mapToResponse(inventory);
            log.info("Inventory with id: {} was fetched.", inventory.getId());
            return inventoryResponse;
        }catch (WebClientResponseException.NotFound ex) {
            throw new ResourceNotFoundException("Inventory service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            throw new BadRequestException("Invalid request sent to inventory service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            throw new DatabaseException("Inventory service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            throw new DatabaseException("Database operation failed");
        }
    }

    public List<InventoryResponse> Get(){
        try{
            List<Inventory> inventoryList = inventoryRepository.findAll();
            List<InventoryResponse> inventoryResponseList = inventoryList.stream().map(this::mapToResponse).toList();
            log.info("All the inventory list fetched.");
            return inventoryResponseList;
        }catch (WebClientResponseException.NotFound ex) {
            throw new ResourceNotFoundException("Inventory service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            throw new BadRequestException("Invalid request sent to inventory service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            throw new DatabaseException("Inventory service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            throw new DatabaseException("Database operation failed");
        }
    }

    public InventoryResponse GetByProductId(String productId){
        try{
            Inventory inventory = inventoryRepository.findByProductId(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with product id:" + productId));
            InventoryResponse inventoryResponse = mapToResponse(inventory);
            log.info("Inventory with id : {} was found with the productId: {}", inventory.getId(), inventory.getProductId());
            return inventoryResponse;
        }catch (WebClientResponseException.NotFound ex) {
            throw new ResourceNotFoundException("Inventory service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            throw new BadRequestException("Invalid request sent to inventory service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            throw new DatabaseException("Inventory service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
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
