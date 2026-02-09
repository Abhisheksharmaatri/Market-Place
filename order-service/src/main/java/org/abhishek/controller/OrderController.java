package org.abhishek.controller;


import lombok.RequiredArgsConstructor;
import org.abhishek.dto.OrderRequest;
import org.abhishek.dto.OrderResponse;
import org.abhishek.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void Create(@RequestBody OrderRequest orderRequest,
                       @RequestHeader("X-User-Id") String userId,
                       @RequestHeader("X-User-Role") String role){
        orderService.Create(orderRequest, userId);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public void Delete(@RequestParam("id") Long id){
        orderService.Delete(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> Get(){
        return orderService.Get();
    }

    @GetMapping("/item")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse GetOne(
            @RequestParam("id") Long id
    ){
        return orderService.Get(id);
    }
}
