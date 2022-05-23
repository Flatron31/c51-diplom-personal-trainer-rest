package com.example.c51diplompersonaltrainerrest.repository;

import com.example.c51diplompersonaltrainerrest.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
}
