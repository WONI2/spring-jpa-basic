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

    @Column(nullable = false)
    private String title;

    private String content;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDate;
    @UpdateTimestamp
    private LocalDateTime updateDate;

//    게시글에 달린 해시태그 목록
    @OneToMany(mappedBy = "post")
    @Builder.Default //빌더를 사용하면서 기본값을 새로 주고 싶다면 사용 해야 함
    private List<HashTag> hashTags = new ArrayList<>();

//    양방향 매핑에서 리스트쪽에 테이터를 추가하는 편의 메서드 생성해야 함. 새로 갱신되지 않기 때문
    public void addHashTag(HashTag hashTag) {
        hashTags.add(hashTag);
        if(this != hashTag.getPost()) {
            hashTag.setPost(this);
        }
    }

}
