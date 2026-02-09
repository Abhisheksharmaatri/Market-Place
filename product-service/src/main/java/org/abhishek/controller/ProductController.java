package org.abhishek.controller;


import lombok.RequiredArgsConstructor;
import org.abhishek.dto.ProductRequest;
import org.abhishek.dto.ProductResponse;
import org.abhishek.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void Create(@RequestBody ProductRequest productRequest){
        productService.Create(productRequest);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void Update(
            @RequestParam("id") String id,
            @RequestBody ProductRequest productRequest){
        productService.Update(productRequest,id);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public void Delete(
            @RequestParam("id") String id
    ){
        productService.Delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> Get(){
        return productService.Get();
    }

    @GetMapping("/item")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse GetOne(
            @RequestParam("id") String id
    ){
        return productService.Get(id);
    }

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public String Status(){
        return "Running";
    }
}
