package com.study.jpa.chap04_relation.entity;


import lombok.*;

import javax.persistence.*;


@Setter @Getter
@EqualsAndHashCode(of="id")

//jpa 연관관계 매핑에서는 연관관계 데이터는 ToString에서 반드시 제외해야 한다
@ToString(exclude = {"department"})
@AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
@Table(name="tbl_emp")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="emp_id")
    private  Long id;

    @Column(name="emp_name", nullable = false)
    private String name;

    //사원입장에서는 부서가 one, 내가 Many의 입장이기 때문에.
    @ManyToOne(fetch = FetchType.LAZY) //FetchType.EAGER가 default 값. 반드시 join을 하도록 만듦.
    // FetchType.LAZY는 필요한 경우에만 join
    @JoinColumn(name="dept_id") //fk이름을 지정
    private Department department; //단방향 연관관계 때문에 만드는 것. 객체 지향은 부서의 모든 정보를 가질 수 있음




}
