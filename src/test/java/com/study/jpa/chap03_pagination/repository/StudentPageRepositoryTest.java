package com.study.jpa.chap03_pagination.repository;

import com.study.jpa.chap02_querymethod.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// 만약 서비스 클래스를 사용한다면 해당 클래스에 TRANSATIONAL 반드시 붙일 것
@Transactional //JPA에는 I , U, D 시에 트랜잭션 처리가 필수
@Rollback(false)
class StudentPageRepositoryTest {


    @Autowired
    StudentPageRepository studentPageRepository;

    @BeforeEach
    void  bulkInsert() {

        //학생 147명 저장
        for (int i = 1; i <= 147 ; i++) {
            Student s = Student.builder()
                    .name("커피맨" + i)
                    .city("도시시" + i)
                    .major("바리스타" + i)
                    .build();

            studentPageRepository.save(s);
        }
    }
        @Test
        @DisplayName("기본 페이징 테스트")
        void basicPagingTest() {
            //given
            int pageNo = 1;
            int amount = 10;
            //페이지 정보 생성
            //페이지 번호가 zero-based 즉 1페이지를 보려면 0이라고 써야 함
            Pageable pageInfo
                    = PageRequest.of(pageNo-1, amount,
//                    Sort.by("name").descending()
                Sort.by(Sort.Order.desc("name"), Sort.Order.asc("city")) //정렬기준이 여러개일 때
            );
            //sort 정렬 기준 필드명.


            //when
            Page<Student> students = studentPageRepository.findAll(pageInfo);


            // 페이징 완료된 데이터셋
            List<Student> studentList = students.getContent();
            //총페이지수
            int totalPages = students.getTotalPages();

            //총 학생 수
            long totalElements = students.getTotalElements();

            //이전으로 돌아가기 가능한지
            Pageable prev = students.getPageable().previousOrFirst();




            Pageable next = students.getPageable().next();


            //then
            System.out.println("\n\n\n");
            studentList.forEach(System.out::println);
             System.out.println("\n\n\n");
            System.out.println("totalElements = " + totalElements);
            System.out.println("\n\n\n");
            System.out.println("totalPages = " + totalPages);
            System.out.println("\n\n\n");
            System.out.println("prev = " + prev);
            System.out.println("next = " + next);

        }

        @Test
        @DisplayName("이름검색+페이징")
        void searchAndPaginationTest() {
            //given
            int pageNo = 1;
            int size = 10;
            //페이지 정보 생성
            //페이지 번호가 zero-based 즉 1페이지를 보려면 0이라고 써야 함
            Pageable pageInfo
                    = PageRequest.of(pageNo-1, size);
            //when
            Page<Student> students = studentPageRepository.findByNameContaining("3", pageInfo);
            //이름에 숫자 3이 들어간 사람만 검색

            //then
            System.out.println("\n\n\n");
            students.getContent().forEach(System.out::println);
            System.out.println("\n\n\n");
        }








}