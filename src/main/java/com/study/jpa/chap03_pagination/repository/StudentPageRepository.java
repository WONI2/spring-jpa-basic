package com.study.jpa.chap03_pagination.repository;

import com.study.jpa.chap02_querymethod.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentPageRepository extends JpaRepository<Student, String> {

    //학생 전체 조회(조건 없이) 페이징 -- 기본 기능.
    @Override
    Page<Student> findAll(Pageable pageable);
    //학생 전체 조회(학생 이름에 특정 단어가 포함된 것을 조회) 페이징 -- 기본 기능.
    Page<Student> findByNameContaining(String name, Pageable pageable);  //param이 많더라도 pageable은 마지막에 넣어줘야!



}
