package com.rajinda.retail.productservice.repository;

import com.rajinda.retail.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    /*@Query("{'id':?0}")
    Optional<Product> findById(String id);*/
    Optional<Product> findByCode(String code) throws Exception;
}
