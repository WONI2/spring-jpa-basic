package com.study.jpa.chap05_practice.api;

import com.study.jpa.chap05_practice.dto.PageDTO;
import com.study.jpa.chap05_practice.dto.PostCreateDTO;
import com.study.jpa.chap05_practice.dto.PostDetailResponseDTO;
import com.study.jpa.chap05_practice.dto.PostListResponseDTO;
import com.study.jpa.chap05_practice.sevice.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostApiController {

    // 리소스 : 게시물 (Post)
/*
     게시물 목록 조회:  /posts       - GET
     게시물 개별 조회:  /posts/{id}  - GET
     게시물 등록:      /posts       - POST
     게시물 수정:      /posts/{id}  - PATCH
     게시물 삭제:      /posts/{id}  - DELETE
 */

    private final PostService postService;


    @GetMapping
    public ResponseEntity list(PageDTO pageDTO) {
        log.info("/api/v1/posts?page={}&size={}", pageDTO.getPage(), pageDTO.getSize());

       PostListResponseDTO dto = postService.getPosts(pageDTO);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        log.info("/api/v1/posts/{} GET", id);
        try {
            PostDetailResponseDTO dto =  postService.getDetail(id);
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
//    게시물 등록
    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody PostCreateDTO dto,
                                    BindingResult result) { //BindingResult : 검증에러 정보를 가진 객체
        log.info("/api/v1/posts POST -payload : {}", dto);

        if(dto == null ){
            return  ResponseEntity.badRequest().body("등록게시물의 정보를 전달해주세요");
        }

        if(result.hasErrors()) { //입력값 검증에 걸림
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(err -> {
                log.warn("invalid client data - {}", err.toString());
            });
        return ResponseEntity.badRequest().body(fieldErrors);
        }

//insert 시 서버 상의 문제가 생길 수 있으니 try/catch 해줄 것. 서버의 메세지는 service에서 받아옴
        try {
            PostDetailResponseDTO postDetailResponseDTO = postService.insert(dto);
            return ResponseEntity.ok().body(postDetailResponseDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("서버 에러 "+e.getMessage());
        }

    }



}
