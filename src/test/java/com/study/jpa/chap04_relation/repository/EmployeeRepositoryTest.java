package com.study.jpa.chap04_relation.repository;

import com.study.jpa.chap04_relation.entity.Department;
import com.study.jpa.chap04_relation.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @BeforeEach
    void bulkInsert() {
        Department d1 = Department.builder()
                .name("영업부")
                .build();
        Department d2 = Department.builder()
                .name("개발부")
                .build();

        departmentRepository.save(d1);
        departmentRepository.save(d2);

        Employee e1 = Employee.builder()
                .name("커피맨")
                .department(d1)
                .build();
        Employee e2 = Employee.builder()
                .name("호빵맨")
                .department(d1)
                .build();
        Employee e3 = Employee.builder()
                .name("식빵맨")
                .department(d2)
                .build();
        Employee e4 = Employee.builder()
                .name("카레빵맨")
                .department(d2)
                .build();
            employeeRepository.save(e1);
            employeeRepository.save(e2);
            employeeRepository.save(e3);
            employeeRepository.save(e4);


    }

    @Test
    @DisplayName("특정 사원의 정보조회")

    void findOneTest() {
        //given
        Long id = 2L;
        //when
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("사원이 없음")
                );
        //then
        System.out.println("\n\n\n");
        System.out.println("employee = " + employee);
        System.out.println("\n\n\n");

        assertEquals("호빵맨", employee.getName());
    }


    @Test
    @DisplayName("부서정보조회")
    void findDeptTest() {

        //given
        Long id = 1L;
        //when
        Department department = departmentRepository.findById(id).orElseThrow();
        //then

        System.out.println("department = " + department); //사원 조회때는 left join이 됐는데, 여기서는 부서를 조회하며 join을 하지 않음
    }





}