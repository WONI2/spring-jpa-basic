package com.study.jpa.chap02_querymethod.repository;

import com.study.jpa.chap02_querymethod.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {

//    findBy~ 뒤에 CAMEL CASE도 반드시 지키고, FIELD명을 작성하는 것도 필수 (컬럼명X)
//    동명이인이 있을 수 있어서 리스트로 받아야!
    List<Student> findByName(String name); //(매개변수)
    List<Student> findByCityAndMajor(String city, String major);
    List<Student> findByMajorContaining(String major); //like query

//    네이티브 쿼리로도 사용 가능
    @Query(value = "SELECT * FROM tbl_student WHERE stu_name = :name", nativeQuery = true)
    Student findOneWithSQL(String name);

//    sql문에 "~~stu_name = :nm"으로 작성시 : Student findOneWithSQL(@param("nm") String name); 가능



    //JPQL
    //select 별칭 from 엔터티클래스명 as 별칭
    //where 별칭.필드명=?

    //native-sql : SELECT * FROM tbl_student
    //              WHERE stu_name=?
    //jpql : SELECT st FROM Student AS st
    //      WHERE st.name = ?

//    도시 이름으로 학생 조회
//    @Query(value = "SELECT *  FROM tbl_student WHERE city = ?1", nativeQuery = true) //?1 로 쓰면 첫번째 param에 자동 매칭

    //    jpql
    @Query("SELECT s FROM Student s WHERE s.city = ?1") //nativeQuery = true 는 쓰지 말 것
    Student getByCityWithJPQL(String city);




//    이름에 특정 글자가 들어가는 사람 찾기
    @Query("SELECT st FROM Student st WHERE st.name LIKE %:nm%")
    List<Student> searchByNameWithJSQL(@Param("nm") String name);

    //JPQL로 수정삭제 쿼리 쓰기
    @Modifying //조회가 아닐 경우 무조건 붙여줘야 함
    @Query("DELETE FROM Student s WHERE s.name = ?1")
    void deleteByNameWithJSQL(String name);


}
