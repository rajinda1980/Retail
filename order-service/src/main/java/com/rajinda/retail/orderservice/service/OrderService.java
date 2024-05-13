package com.rajinda.retail.orderservice.service;

import com.rajinda.retail.orderservice.advice.OrderException;
import com.rajinda.retail.orderservice.dto.*;
import com.rajinda.retail.orderservice.model.Order;
import com.rajinda.retail.orderservice.model.OrderItem;
import com.rajinda.retail.orderservice.repository.OrderItemRepository;
import com.rajinda.retail.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final WebClient.Builder webClientBuilder;

    @Transactional
    public OrderResponseDto createOder(OrderRequestDto requestDto) throws OrderException {

        List<String> codes = requestDto.getItems().stream().map(OrderItemDto::getItemId).toList();
        if (null == codes) throw new OrderException("Order items are not available");

        InventoryResponseDto[] invResponse =
                    webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8080/api/inventory/isInStock",
                                    uriBuilder -> uriBuilder.queryParam("codes", codes).build())
                            .retrieve()
                            .bodyToMono(InventoryResponseDto[].class)
                            .block();

        boolean isInStock = false;
        if (invResponse != null && codes.size() == invResponse.length) {
            isInStock = Arrays.stream(invResponse).allMatch(InventoryResponseDto::isInStock);
        }

        if (isInStock) {
            Order order = mapOrder(requestDto);
            requestDto.getItems()
                    .forEach(it -> mapOrderItem(it, order));

            orderRepository.save(order);
            return mapOrderResponseDto(order);
        } else {
            throw new OrderException("Items are not available in stock");
        }
    }

    public OrderResponseDto updateOrder(OrderStatusDto orderStatusDto) throws OrderException {
        Order order =
                orderRepository.findById(orderStatusDto.getOrderDid())
                        .orElseThrow(() -> new OrderException("Order not found for " + orderStatusDto.getOrderDid()));
        order.setStatus(orderStatusDto.getStatus());
        orderRepository.save(order);
        return mapOrderResponseDto(order);
    }

    public OrdersDto getOder(Long orderDid) throws OrderException {
        Order order =
                orderRepository.findById(orderDid)
                        .orElseThrow(() -> new OrderException("Order not found for " + orderDid));
        return mapOrderDto(order);
    }

    public List<OrdersDto> findOrders() throws OrderException {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                        .map(this::mapOrderDto)
                        .collect(Collectors.toList());
    }

    private Order mapOrder(OrderRequestDto orderRequestDto) {
        return Order.builder()
                .orderId(orderRequestDto.getOrderId())
                .createdBy(orderRequestDto.getCreatedBy())
                .createdDate(orderRequestDto.getCreatedDate())
                .status("CREATED")
                .build();
    }

    private void mapOrderItem(OrderItemDto orderItemDto, Order order) {
        OrderItem orderItem =
                OrderItem.builder()
                        .itemId(orderItemDto.getItemId())
                        .qty(orderItemDto.getQty())
                        .price(orderItemDto.getPrice())
                        .order(order)
                        .build();
        order.add(orderItem);
    }

    private OrderResponseDto mapOrderResponseDto(Order order) {
        int totalQty =
                order.getItems().stream()
                        .mapToInt(OrderItem::getQty)
                        .reduce(0, Integer::sum);
        BigDecimal totalPrice =
                order.getItems().stream()
                        .map(o -> o.getPrice().multiply(new BigDecimal(o.getQty())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .createdBy(order.getCreatedBy())
                .createdDate(order.getCreatedDate())
                .status(order.getStatus())
                .totalQty(totalQty)
                .totalPrice(totalPrice)
                .build();
    }

    private OrdersDto mapOrderDto(Order order) {
        Set<OrderItemDto> items =
                order.getItems().stream()
                        .map(o ->
                                OrderItemDto.builder()
                                .itemId(o.getItemId())
                                .price(o.getPrice())
                                .qty(o.getQty())
                                        .build()
                        ).collect(Collectors.toSet());

        return OrdersDto.builder()
                .did(order.getDid())
                .orderId(order.getOrderId())
                .createdDate(order.getCreatedDate())
                .createdBy(order.getCreatedBy())
                .status(order.getStatus())
                .items(items)
                .build();

    }
}
