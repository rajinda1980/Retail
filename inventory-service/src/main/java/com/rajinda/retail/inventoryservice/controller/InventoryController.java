package com.rajinda.retail.inventoryservice.controller;

import com.rajinda.retail.inventoryservice.advice.InventoryException;
import com.rajinda.retail.inventoryservice.dto.InventoryResponseDto;
import com.rajinda.retail.inventoryservice.dto.ItemDto;
import com.rajinda.retail.inventoryservice.dto.ItemResponseDto;
import com.rajinda.retail.inventoryservice.dto.ItemUpdateDto;
import com.rajinda.retail.inventoryservice.service.InventoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/inventory")
@AllArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponseDto addItem(@RequestBody ItemDto itemDto) throws InventoryException {
        return inventoryService.createItem(itemDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ItemResponseDto updateItem(@RequestBody ItemUpdateDto itemUpdateDto) throws InventoryException {
        return inventoryService.updateItem(itemUpdateDto);
    }

    @GetMapping("/{did}")
    @ResponseStatus(HttpStatus.OK)
    public ItemResponseDto getItem(@PathVariable Long did) throws InventoryException {
        return inventoryService.getItem(did);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemResponseDto> findItems() throws InventoryException {
        return inventoryService.findItems();
    }

    @DeleteMapping("/{did}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteItem(@PathVariable Long did) throws InventoryException {
        return inventoryService.deleteITem(did);
    }

    @GetMapping("/isInStock")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponseDto> isInStock(@RequestParam List<String> codes) throws InventoryException {
        log.info("Check products in inventory. Codes: {}", codes);
        return inventoryService.findInventoryItem(codes);
    }
}
