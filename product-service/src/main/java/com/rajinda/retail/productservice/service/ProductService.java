package com.rajinda.retail.productservice.service;

import com.rajinda.retail.productservice.advicer.ProductException;
import com.rajinda.retail.productservice.dto.ProductDto;
import com.rajinda.retail.productservice.dto.ProductResponse;
import com.rajinda.retail.productservice.model.Product;
import com.rajinda.retail.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public String createProduct(ProductDto productDto) throws ProductException {
        try {
            productRepository.save(mapProduct(productDto));
            return "Product is created";
        } catch (Exception e) {
            throw new ProductException(e.getMessage());
        }
    }

    public ProductResponse getProduct(String code) throws ProductException {
        try {
            Product product =
                    productRepository.findByCode(code)
                            .orElseThrow(() -> new ProductException("Product is not available"));
            return mapProductResponse(product);
        } catch (Exception e) {
            throw new ProductException(e.getMessage());
        }
    }

    public List<ProductResponse> findProducts() throws ProductException {
        try {
            List<Product> products = productRepository.findAll();
            return products.stream()
                            .map(this::mapProductResponse)
                            .toList();

        } catch (Exception e) {
            throw new ProductException(e.getMessage());
        }
    }

    public String updateProduct(ProductDto productDto) throws ProductException {
        try {
            Optional<Product> optProduct = productRepository.findById(productDto.getId());
            if (optProduct.isEmpty()) {
                throw new Exception("Product does not exist for " + productDto.getCode() + ". Product ID : " + productDto.getId());
            }

            Product product = optProduct.get();
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            productRepository.save(product);
            return "Product is updated";

        } catch (Exception e) {
            throw new ProductException(e.getMessage());
        }
    }

    public String deleteProduct(String id) throws ProductException {
        try {
            Product product =
                    productRepository.findById(id)
                            .orElseThrow(() -> new ProductException("Product is not available"));
            productRepository.delete(product);
            return "Product is deleted";
        } catch (Exception e) {
            throw new ProductException(e.getMessage());
        }
    }

    private Product mapProduct(ProductDto productDto) {
        return Product.builder()
                .code(productDto.getCode())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .build();
    }

    private ProductResponse mapProductResponse(Product product) {
        return ProductResponse.builder()
                .id(String.valueOf(product.getId()))
                .cone(product.getCode())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
