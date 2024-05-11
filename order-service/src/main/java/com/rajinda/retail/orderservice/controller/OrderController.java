package com.rajinda.retail.orderservice.controller;

import com.rajinda.retail.orderservice.service.OrderService;
import com.rajinda.retail.orderservice.advice.OrderException;
import com.rajinda.retail.orderservice.dto.OrderRequestDto;
import com.rajinda.retail.orderservice.dto.OrderResponseDto;
import com.rajinda.retail.orderservice.dto.OrderStatusDto;
import com.rajinda.retail.orderservice.dto.OrdersDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto orderRequestDto) throws OrderException {
        return orderService.createOder(orderRequestDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto updateOrder(@RequestBody OrderStatusDto orderStatusDto) throws OrderException {
        return orderService.updateOrder(orderStatusDto);
    }

    @GetMapping("/{did}")
    @ResponseStatus(HttpStatus.OK)
    public OrdersDto getOrders(@PathVariable Long did) throws OrderException {
        return orderService.getOder(did);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrdersDto> getOrders() throws OrderException {
        return orderService.findOrders();
    }
}
