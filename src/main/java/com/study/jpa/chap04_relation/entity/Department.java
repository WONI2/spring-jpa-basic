package com.study.jpa.chap04_relation.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(of="id")
@ToString(exclude = {"employees"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="tbl_dept")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dept_id")
    private  Long id;

    @Column(name="dept_name", nullable = false)
    private String name;


//    양방향 매핑에서는 상대방 엔터티의 갱신에 관여할 수 없다. 단순히 읽기전용(조회)으로만 사용해야 함.
    @OneToMany(mappedBy = "department") //상대 entity의 조인되는 fk의 필드명을 적어줌
    //초기화 반드시 필요
    private List<Employee> employees = new ArrayList<>();


}
