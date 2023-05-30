package com.study.jpa.chap02_querymethod.repository;

import com.study.jpa.chap02_querymethod.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;
    @BeforeEach
    void insertData() {
        //given
        Student s1 = Student.builder()
                .name("춘식이")
                .city("서울시")
                .major("수학과")
                .build();
        Student s2 = Student.builder()
                .name("춘신춘왕")
                .city("부산시")
                .major("수학교육과")
                .build();
        Student s3 = Student.builder()
                .name("대길이")
                .city("한양도성")
                .major("체육교육과")
                .build();
        //when

        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);

    }

    @Test
    @DisplayName("이름이 춘식이인 학생의 정보를 조회해야 한다")
    void findByNameTest() {
        //given
        String name = "춘식이";

        //when
        List<Student> students = studentRepository.findByName(name);

        //then
        assertEquals(1, students.size());

        System.out.println("students = " + students);
    }

    @Test
    @DisplayName("city와 major로 찾기")
    void findByCityAndMajorTest() {
        //given
        String city = "부산시";
        String major = "수학교육과";

        //when
        List<Student> students = studentRepository.findByCityAndMajor(city, major);

        //then
        assertEquals(1, students.size());

        System.out.println("students = " + students);
    }

    @Test
    @DisplayName("전공에 수학이 들어간 것 ")
    void findByMajorContainingTest() {
        //given
        String major = "수학";

        //when
        List<Student> students = studentRepository.findByMajorContaining(major);

        //then
        assertEquals(2, students.size());

        System.out.println("\n\n\n\n");
        students.forEach(System.out::println);
        System.out.println("\n\n\n\n");

    }
    @Test
    @DisplayName("이름이 춘식이인 학생의 정보를 조회해야 한다")
    void nativeSQLTest() {
        //given
        String name = "춘식이";

        //when
        Student oneWithSQL = studentRepository.findOneWithSQL(name);

        //then
        assertEquals("서울시", oneWithSQL.getCity());

    }

    @Test
    @DisplayName("이름이 춘식이인 학생의 정보를 조회해야 한다")
    void findCityWithJSQLTest() {
        //given
        String city = "한양도성";

        //when
        Student student = studentRepository.getByCityWithJPQL(city);

        //then
        assertEquals("대길이", student.getName());

    }
    @Test
    @DisplayName("이름에 춘이 들어가는 학생이 2명일 것이다 ")
    void searchByNameWithJSQLTest() {
        //given
        String name = "춘";

        //when
        List<Student> students = studentRepository.searchByNameWithJSQL(name);

        //then
        assertEquals(2, students.size());

    }
    @Test
    @DisplayName("JSQL로 삭제하기")
    void deleteByNameWithJSQLTest() {
        //given
        String name = "대길이";
        //when
        studentRepository.deleteByNameWithJSQL(name);
        //then
        List<Student> students = studentRepository.findByName(name);

        assertEquals(0, students.size());
    }



}