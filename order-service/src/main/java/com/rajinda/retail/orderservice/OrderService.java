package com.rajinda.retail.orderservice;

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

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrderResponseDto createOder(OrderRequestDto requestDto) throws OrderException {
        Order order = mapOrder(requestDto);
        requestDto.getItems()
                .forEach(it -> mapOrderItem(it, order));

        orderRepository.save(order);
        return mapOrderResponseDto(order);
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
