package org.abhishek.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abhishek.dto.ProductRequest;
import org.abhishek.dto.ProductResponse;
import org.abhishek.exception.BadRequestException;
import org.abhishek.exception.ConflictException;
import org.abhishek.exception.DatabaseException;
import org.abhishek.exception.ResourceNotFoundException;
import org.abhishek.model.Product;
import org.abhishek.repository.ProductRepository;
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
public class ProductService {
    private final ProductRepository productRepository;

    public void Create(ProductRequest productRequest){
        log.info("Started the CREATE service for Product.");
        try{
            Product product = mapFromRequest(productRequest);
            log.info("Saving the product object for CREATE service for Product");
            Product savedProduct = productRepository.save(product);
            log.info("Product with Id: {} was saved.", savedProduct.getId());
        }
        catch (WebClientResponseException.NotFound ex) {
            log.info(ex.toString());
            log.info("Product service resource not found");
            throw new ResourceNotFoundException("Product service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            log.info(ex.toString());
            log.info("Invalid request sent to product service");
            throw new BadRequestException("Invalid request sent to product service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            log.info(ex.toString());
            log.info("Product service unavailable");
            throw new DatabaseException("Product service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            log.info(ex.toString());
            log.info("Product violates database constraints");
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            log.info(ex.toString());
            log.info("Database operation failed");
            throw new DatabaseException("Database operation failed");
        }
    };

    @Transactional
    public void Update(ProductRequest productRequest, String id){
        log.info("Started the UPDATE service for Product with id: {}.", id);
        try{
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
            log.info("Product for UPDATE service was found for Product with id: {}", id);
            product.setName(productRequest.getName());
            product.setDescription(productRequest.getDescription());
            log.info("Product with id: {} was sent for UPDATE service to database.", product.getId());
            productRepository.save(product);
            log.info("Product with id: {} was updated.", product.getId());
        }
        catch (WebClientResponseException.NotFound ex) {
            log.info(ex.toString());
            log.info("Product service resource not found");
            throw new ResourceNotFoundException("Product service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            log.info(ex.toString());
            log.info("Invalid request sent to product service");
            throw new BadRequestException("Invalid request sent to product service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            log.info(ex.toString());
            log.info("Product service unavailable");
            throw new DatabaseException("Product service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            log.info(ex.toString());
            log.info("Product violates database constraints");
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            log.info(ex.toString());
            log.info("Database operation failed");
            throw new DatabaseException("Database operation failed");
        }
    };

    public void Delete(String id){
        log.info("Started the CREATE service for Product.");
        try{
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
            log.info("Product with id was found: {}, and then sent for DELETE service to database.", product.getId());
            productRepository.delete(product);
            log.info("Product with id: {} was deleted.", product.getId());
        }
        catch (WebClientResponseException.NotFound ex) {
            log.info(ex.toString());
            log.info("Product service resource not found");
            throw new ResourceNotFoundException("Product service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            log.info(ex.toString());
            log.info("Invalid request sent to product service");
            throw new BadRequestException("Invalid request sent to product service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            log.info(ex.toString());
            log.info("Product service unavailable");
            throw new DatabaseException("Product service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            log.info(ex.toString());
            log.info("Product violates database constraints");
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            log.info(ex.toString());
            log.info("Database operation failed");
            throw new DatabaseException("Database operation failed");
        }
    };

    public ProductResponse Get(String id){
        log.info("Started the CREATE service for Product.");
        try{
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
            log.info("Product with id was found: {}, and then sent for GET service to Frontend.", product.getId());
            ProductResponse productResponse = mapToResponse(product);
            log.info("Product with id: {} was fectched.", product.getId());
            return productResponse;
        }
        catch (WebClientResponseException.NotFound ex) {
            log.info(ex.toString());
            log.info("Product service resource not found");
            throw new ResourceNotFoundException("Product service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            log.info(ex.toString());
            log.info("Invalid request sent to product service");
            throw new BadRequestException("Invalid request sent to product service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            log.info(ex.toString());
            log.info("Product service unavailable");
            throw new DatabaseException("Product service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            log.info(ex.toString());
            log.info("Product violates database constraints");
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            log.info(ex.toString());
            log.info("Database operation failed");
            throw new DatabaseException("Database operation failed");
        }
    };

    public List<ProductResponse> Get(){
        log.info("Started the CREATE service for Product.");
        try{
            List<Product> productList = productRepository.findAll();
            List<ProductResponse> productResponseList = productList.stream().map(this::mapToResponse).toList();
            log.info("All Product wer found and then sent for GET ALL service to Frontend.");
            log.info("All the products were fetched for GET ALL request.");
            return productResponseList;
        }
        catch (WebClientResponseException.NotFound ex) {
            log.info(ex.toString());
            log.info("Product service resource not found");
            throw new ResourceNotFoundException("Product service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            log.info(ex.toString());
            log.info("Invalid request sent to product service");
            throw new BadRequestException("Invalid request sent to product service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            log.info(ex.toString());
            log.info("Product service unavailable");
            throw new DatabaseException("Product service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            log.info(ex.toString());
            log.info("Product violates database constraints");
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            log.info(ex.toString());
            log.info("Database operation failed");
            throw new DatabaseException("Database operation failed");
        }
    };

//    Helpers
    private ProductResponse mapToResponse(Product product){
        ProductResponse productResponse=ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .build();
        return productResponse;
    }

    private Product mapFromRequest(ProductRequest productRequest){
        Product product=Product
                .builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .build();
        return product;
    }

}
