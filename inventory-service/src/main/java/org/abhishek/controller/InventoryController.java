package org.abhishek.controller;


import lombok.RequiredArgsConstructor;
import org.abhishek.dto.InventoryRequest;
import org.abhishek.dto.InventoryResponse;
import org.abhishek.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void Create(@RequestBody InventoryRequest inventoryRequest){
        inventoryService.Create(inventoryRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void Update(
            @RequestParam("id") String id,
            @RequestBody InventoryRequest inventoryRequest
    ){
        inventoryService.Update(inventoryRequest, id);
    }

    @PutMapping("/product")
    @ResponseStatus(HttpStatus.OK)
    public void UpdateByProductId(
            @RequestParam("productId") String productId,
            @RequestBody InventoryRequest inventoryRequest
    ){
        inventoryService.UpdateByProductId(inventoryRequest, productId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void Delete(
            @RequestParam("id") String id
    ){
        inventoryService.Delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> Get(){
        return inventoryService.Get();
    }

    @GetMapping("/item")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse GetOne(
            @RequestParam("id") String id
    ){
        return inventoryService.Get(id);
    }

    @GetMapping("/product")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse GetByProductId(
            @RequestParam("productId") String productId
    ){
        return inventoryService.GetByProductId(productId);
    }

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public String Status(){
        return "Running";
    }
}
