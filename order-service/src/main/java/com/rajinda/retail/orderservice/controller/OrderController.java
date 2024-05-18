package com.rajinda.retail.orderservice.controller;

import com.rajinda.retail.orderservice.service.OrderService;
import com.rajinda.retail.orderservice.advice.OrderException;
import com.rajinda.retail.orderservice.dto.OrderRequestDto;
import com.rajinda.retail.orderservice.dto.OrderResponseDto;
import com.rajinda.retail.orderservice.dto.OrderStatusDto;
import com.rajinda.retail.orderservice.dto.OrdersDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "orderFail")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) throws OrderException {
        log.info("Receive create order request. Order ID : {}", orderRequestDto.getOrderId());
        return CompletableFuture.supplyAsync(() -> orderService.createOder(orderRequestDto));
    }

    public CompletableFuture<OrderResponseDto> orderFail(OrderRequestDto orderRequestDto, Throwable exception) {
        return CompletableFuture.supplyAsync(() -> OrderResponseDto.builder().build());
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
