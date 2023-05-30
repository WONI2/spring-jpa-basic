package com.study.jpa.chap01_basic.repository;

import com.study.jpa.chap01_basic.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

//추상메서드 만들 필요없이
public interface ProductRepository extends JpaRepository<Product, Long> { // <ENTITY, PK TYPE>
 }
