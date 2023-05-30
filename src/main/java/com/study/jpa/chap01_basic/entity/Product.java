package com.study.jpa.chap01_basic.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Setter @Getter @ToString @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
@Builder

@Entity
@Table(name="tbl_product")
public class Product {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="prod_id")
    private long Id;
    @Column(name="prod_nm", nullable = false, length = 30) //nullable- notnull 제약조건 ,  length = 30 - varchar: 30
    private String name;

    @Builder.Default
    private int price = 0; //price의 default 값을 0으로 주고 싶을 때
    @Enumerated(EnumType.STRING)
    private Category category;

    @CreationTimestamp //디폴트 타임스탬프
    @Column(updatable = false) //등록되고 나면 수정 불가
    private LocalDateTime createDate;

    @UpdateTimestamp //시간이 수정되도록 해줌!
    private LocalDateTime updateDate;

    public enum  Category {
        FOOD, FASHION, ELECTRONIC
    }


}
