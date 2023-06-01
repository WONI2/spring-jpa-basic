package com.study.jpa.chap04_relation.repository;

import com.study.jpa.chap04_relation.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT DISTINCT d FROM Department d JOIN FETCH d.employees") //JPQL 문법. select 다음에 별칭을 쓸 것. JOIN할 대상을 FETCH + 대상으로 작성
//    중복 데이터가 나올 수 있기 때문에 DISTINCT를 붙여줄 것
    List<Department> findAllIncludeEmployees();
}
