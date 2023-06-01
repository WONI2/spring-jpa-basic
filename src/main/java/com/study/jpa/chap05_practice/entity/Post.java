package com.study.jpa.chap05_practice.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter
@ToString(exclude = {"hashTags"})
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name="tbl_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_no")
    private Long id;

    @Column(nullable = false)
    private String writer;

    private String content;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDate;
    @UpdateTimestamp
    private LocalDateTime updateDate;

//    게시글에 달린 해시태그 목록
    @OneToMany(mappedBy = "post")
    private List<HashTag> hashTags = new ArrayList<>();

}
