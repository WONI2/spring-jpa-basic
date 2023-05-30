package com.study.jpa.chap01_basic.repository;

import com.study.jpa.chap01_basic.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false) //실무에서는 true로 할 것
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @BeforeEach //테스트 실행하기 전에 실행되는 것. 데이터를 넣어놓고 계속 실행 함
    void  insertDummyData() {
        //given
        Product p1 = Product.builder()
                .name("아이폰")
                .category(Product.Category.ELECTRONIC)
                .price(1000000)
                .build();
        Product p2 = Product.builder()
                .name("사과")
                .category(Product.Category.FOOD)
                .price(1000)
                .build();

        Product p3 = Product.builder()
                .name("운동화")
                .category(Product.Category.FASHION)
                .price(100000)
                .build();

        Product p4 = Product.builder()
                .name("TV")
                .category(Product.Category.ELECTRONIC)
                .build();

        //when
        Product save1 = productRepository.save(p1);
        productRepository.save(p2);
        Product save3 = productRepository.save(p3);
        productRepository.save(p4);

    }

    @Test
    @DisplayName("5번째 상품을 저장한다")
    void  saveTest() {
        Product build = Product.builder()
                .name("장화")
                .category(Product.Category.FASHION)
                .price(50000)
                .build();
        Product save = productRepository.save(build);
        assertNotNull(save);

    }

    @Test
    @DisplayName("id가 2번인 데이터를 삭제해야 한다")
    void removeTest() {
        //given
        long id = 2L;

        //when
        productRepository.deleteById(id);
        //then
    }



    @Test
    @DisplayName("상품 전체 조회를 하면 상품의 개수가 4개여야 한다")
    void findAllTest() {
        //given

        //when
        List<Product> all = productRepository.findAll();

        //then
        all.forEach(System.out::println);
        assertEquals(4, all.size());

    }
    @Test
    @DisplayName("3번 상품을 조회하면 상품명이 운동화다")
    void findOneTest() {
        //given
        Long id = 3L;
        //when
        Optional<Product> product = productRepository.findById(id);
        //Optional null 체크 편의성을 위함, .ifPresent(p -> {}); 로 확인 가능

        //then
        product.ifPresent(p -> {
            assertEquals("운동화", p.getName());
        });

        Product foundProduct = product.get(); //Optional에서 꺼낼 때는 get으로 가져올것
        assertNotNull(foundProduct);

        System.out.println("foundProduct = " + foundProduct);

    }

    @Test
    @DisplayName("4번 상품의 이름과 가격을 변경한다 ")
    void modifyTest(){

        //given
        long id = 2L;
        String newName = "귤";
        int newPrice = 3000;
        //when
//        jpa에서 update는 따로 메서드를 지원x 조회한 후 setter로 변경하면 자동으로 update

        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(p-> {
            p.setName(newName);
            p.setPrice(newPrice);

            productRepository.save(p);
        });

        assertTrue(product.isPresent()); //존재여부 확인
        Product p = product.get();
        assertEquals("귤", p.getName());

        //then
    }




}