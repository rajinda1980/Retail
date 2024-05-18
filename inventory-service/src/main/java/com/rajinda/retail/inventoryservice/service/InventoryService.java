package com.rajinda.retail.inventoryservice.service;

import com.rajinda.retail.inventoryservice.dto.InventoryResponseDto;
import com.rajinda.retail.inventoryservice.repository.ItemRepository;
import com.rajinda.retail.inventoryservice.advice.InventoryException;
import com.rajinda.retail.inventoryservice.dto.ItemDto;
import com.rajinda.retail.inventoryservice.dto.ItemResponseDto;
import com.rajinda.retail.inventoryservice.dto.ItemUpdateDto;
import com.rajinda.retail.inventoryservice.model.Item;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class InventoryService {

    private final ItemRepository itemRepository;

    public ItemResponseDto createItem(ItemDto itemDto) throws InventoryException {
        Item item = mapItem(itemDto);
        itemRepository.save(item);
        return mapItemResponseDto(item);
    }

    public ItemResponseDto updateItem(ItemUpdateDto itemUpdateDto) throws InventoryException {
        Item item =
                itemRepository.findById(itemUpdateDto.getDid())
                        .orElseThrow(() -> new InventoryException("Item not found for " + itemUpdateDto.getDid()));
        item.setName(itemUpdateDto.getName());
        item.setQty(itemUpdateDto.getQty());
        item.setRol(itemUpdateDto.getRol());
        item.setUpdatedDate(LocalDateTime.now());
        itemRepository.save(item);

        return mapItemResponseDto(item);
    }

    public ItemResponseDto getItem(Long did) throws InventoryException {
        Item item =
                itemRepository.findById(did)
                        .orElseThrow(() -> new InventoryException("Item not found for " + did));
        return mapItemResponseDto(item);
    }

    public List<ItemResponseDto> findItems() throws InventoryException {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(this::mapItemResponseDto).toList();
    }

    public String deleteITem(Long did) throws InventoryException {
        Item item =
                itemRepository.findById(did)
                        .orElseThrow(() -> new InventoryException("Item not found for " + did));
        itemRepository.delete(item);
        return "Item is deleted";
    }

    public List<InventoryResponseDto> findInventoryItem(List<String> codes) throws InventoryException {
        List<Item> items =
                itemRepository.findByCodeIn(codes);
        if (items.size() > 0) {
            log.info("Items found in inventory");
            return items.stream().map(this::mapInventoryResponseDto).toList();
        } else {
            return new ArrayList<>();
        }
    }

    private Item mapItem(ItemDto itemDto) {
        return Item.builder()
                .code(itemDto.getCode())
                .name(itemDto.getItemName())
                .qty(itemDto.getQty())
                .rol(itemDto.getRol())
                .createdDate(LocalDateTime.now())
                .build();
    }

    private ItemResponseDto mapItemResponseDto(Item item) {
        return ItemResponseDto.builder()
                .did(item.getDid())
                .code(item.getCode())
                .itemName(item.getName())
                .qty(item.getQty())
                .rol(item.getRol())
                .createdDate(item.getCreatedDate())
                .updatedDate(item.getUpdatedDate())
                .build();
    }

    private InventoryResponseDto mapInventoryResponseDto(Item item) {
        return InventoryResponseDto.builder()
                .code(item.getCode())
                .isInStock(item.getQty() > 0 ? Boolean.TRUE : Boolean.FALSE)
                .build();
    }
}
