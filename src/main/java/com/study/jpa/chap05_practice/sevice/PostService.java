package com.study.jpa.chap05_practice.sevice;


import com.study.jpa.chap05_practice.dto.*;
import com.study.jpa.chap05_practice.entity.HashTag;
import com.study.jpa.chap05_practice.entity.Post;
import com.study.jpa.chap05_practice.repository.HashTagRepository;
import com.study.jpa.chap05_practice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional //JPA 레파지토리는 반드시 트랜잭션 아노테이션 필수
public class PostService {

    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;


    public PostListResponseDTO getPosts(PageDTO pageDTO) {

//        pageable 객체 생성
        Pageable pageable = PageRequest.of(
                pageDTO.getPage()-1,
                pageDTO.getSize(),
                Sort.by("createDate").descending()
        );

        //        db에서 게시물 목록 조회
        Page<Post> posts = postRepository.findAll(pageable);
        //게시물 정보만 꺼내기
        List<Post> postList = posts.getContent();
        List<PostDetailResponseDTO> postDetailResponseDTOList
                =postList.stream().map(post -> new PostDetailResponseDTO(post)).collect(Collectors.toList());


//        db에서 조회한 정보를 JSON형태에 맞는 DTO로 변환

        return  PostListResponseDTO.builder()
                                            .count(postDetailResponseDTOList.size()) //페이징 하고나서 조회된 게시물 수.
                                            .pageInfo(new PageResponseDTO<Post>(posts)) //전체 게시물 수를 담고 있음
                                            .posts(postDetailResponseDTOList)
                                            .build();


    }

    public PostDetailResponseDTO getDetail(Long id) {

        Post postEntity = postRepository.findById(id).orElseThrow(
                () -> new RuntimeException(
                        id + "번 게시물이 없음"
                )
        );

    return new PostDetailResponseDTO(postEntity);
    }

    public PostDetailResponseDTO insert(final PostCreateDTO dto)
    throws RuntimeException {

//        게시물 저장
        Post saved = postRepository.save(dto.toEntity());
//        hashtag 저장
        List<String> hashTags = dto.getHashTags();
        if(hashTags != null && hashTags.size() > 0) {
            hashTags.forEach(ht -> {
                HashTag savedTag = hashTagRepository.save(
                        HashTag.builder()
                                .tagName(ht)
                                .post(saved)
                                .build());
                saved.addHashTag(savedTag);

            });

        }


        return new PostDetailResponseDTO(saved);

    }
}
