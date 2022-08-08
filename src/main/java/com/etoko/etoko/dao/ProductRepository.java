package com.etoko.etoko.dao;

import com.etoko.etoko.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}