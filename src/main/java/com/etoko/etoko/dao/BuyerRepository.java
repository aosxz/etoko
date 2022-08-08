package com.etoko.etoko.dao;

import com.etoko.etoko.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer, Integer> {
}