package com.study.jpa.chap02_querymethod.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Setter //실무적 측면에서 setter는 신중히. 객체 불변성이 깨질 수 있기 때문
@Getter @ToString
@EqualsAndHashCode(of = "id") //id만 비교해서 같은지 판단 {"name", "id"} 로 쓰면 id와 name 모두를 비교
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_student")
public class Student {

    @Id
    @Column(name= "stu_id")
    @GeneratedValue(generator = "letsUid")
    @GenericGenerator(strategy = "uuid", name = "letsUid")
    private String id;

    @Column(name = "stu_name", nullable = false)
    private String name;

    private String city;

    private String major;

}
