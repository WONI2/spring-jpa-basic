package com.study.jpa.chap05_practice.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString(exclude = {"post"})
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="tbl_hash_tag")
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tag_no")
    private Long id;

    private String tagName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //cascade = CascadeType.ALL : 게시물이 지워질 때 같이 지워지도록 반대편에서 orphan~~
    @JoinColumn(name="post_no")
    private Post post;//연관관계 주인인 단방향
}
