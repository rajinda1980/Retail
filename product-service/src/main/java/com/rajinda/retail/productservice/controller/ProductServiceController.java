package com.rajinda.retail.productservice.controller;

import com.rajinda.retail.productservice.advicer.ProductException;
import com.rajinda.retail.productservice.dto.ProductDto;
import com.rajinda.retail.productservice.dto.ProductResponse;
import com.rajinda.retail.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductServiceController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addProduct(@RequestBody ProductDto product) throws ProductException {
        log.info("Receive new product: {}", product.toString());
        return productService.createProduct(product);
    }

    @GetMapping("/{code}")
    public ProductResponse getProduct(@PathVariable String code) throws ProductException {
        return productService.getProduct(code);
    }

    @GetMapping
    public List<ProductResponse> findProducts() throws ProductException {
        return productService.findProducts();
    }

    @PutMapping
    public String updateProduct(@RequestBody ProductDto productDto) throws ProductException {
        return productService.updateProduct(productDto);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id) throws ProductException {
        return productService.deleteProduct(id);
    }
}
