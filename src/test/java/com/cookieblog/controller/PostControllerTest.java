package com.cookieblog.controller;

import com.cookieblog.domain.Post;
import com.cookieblog.repository.PostRepository;
import com.cookieblog.request.PostCreate;
import com.cookieblog.request.PostEdit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;


//@SpringBootTest // 이거 달아줘야 test3 에러 안 나는데 대신 WebMvcTest(MockMve) 주입 못 받음.
@SpringBootTest
@AutoConfigureMockMvc // 이렇게 해야 test3 가능

//@WebMvcTest // 이거 넣어야 Autowired
// 클래스 선택 , ctrl + shift + t
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @BeforeEach // 테스트 전에 이전에 수행했던 기록 다 삭제해도록
    void clean(){
        postRepository.deleteAll();
    }

    // 이건 json이 아닌 Content-Type:"application/x-www-form-urlencoded;charset=UTF-8" 방식
    // 추후에 넘겨줄 데이터가 많으면 불편해짐.
//    @Test
//    @DisplayName("/posts 요첯 시 문자 출력한다.")
//    void test() throws Exception{
//        mockMvc.perform(post("/post")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .param("title", "글 제목입니다.") // MockMvcRequestBuilders.* 로 임포트 받아야 에러 안 뜸
//                        .param("content", "글 내용입니다."))
//                .andExpect(status().isOk()) // 기대하는 값 : andExpect
//                .andExpect(content().string("온뇽"))
//                .andDo(print()); // 테스트의 대한 요청 서머리 확인
//    }

    // application/json 으로 쭉 예정
//    @Test
//    @DisplayName("/posts 요첯 시 문자 출력한다.")
//    void test() throws Exception{
//
//        PostCreate request = new PostCreate("제목입니다.", "내용입니다.");
//
//        // ☆ ObjectMapper ☆ 자동으로 json 형식으로 변경해줌!! key : title, content
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(request);
//
//        System.out.println(json);
//
//        mockMvc.perform(post("/post")
//                        .contentType(MediaType.APPLICATION_JSON)
////                        .content("{\"title\": \"제목입니다.\", \"content\": \"내용입니다.\"}") // 이런 하드코딩은 수정하기 어렵다 (예시용)
//                                .content(json)
//                )
//                .andExpect(status().isOk()) // 기대하는 값 : andExpect
//                .andExpect(content().string("{}"))
//                .andDo(print()); // 테스트의 대한 요청 서머리 확인
//    }
    // json 데이터가 하나로 넘어 갔으므로 ModelAttribute로 받으면 null이 뜬다.
    // RequestBody로 받아야 한다.

    @Test
    @DisplayName("/posts 요청 시 문자 출력한다.")
    void test() throws Exception{

        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        System.out.println(json);

        mockMvc.perform(post("/post")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk()) // 기대값
                .andExpect(content().string(""))
                .andDo(print()); // 테스트의 대한 요청 서머리 확인
    }

    // 얘 왜 에러나지...?
//    @Test
//    @DisplayName("/posts 요청 시 title 값 필수")
//    void test2() throws Exception{
//        mockMvc.perform(post("/post")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"title\": null, \"content\": \"내용입니다.\"}")
//                )
//                .andExpect(status().isBadRequest()) // 기대하는 값 : andExpect
////                .andExpect(content().string("{\"title\":\"타이틀을 입력해 주세요\"}"))
//                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해 주세요"))
//                .andDo(print()); // 테스트의 대한 요청 서머리 확인
//    }


    @Test
    @DisplayName("/posts 요청 시 DB에 값이 저장된다.")
    void test3() throws Exception{
        mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"제목입니다\", \"content\": \"내용입니다.\"}")
                )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        // PostRepository 주입 받고, 데이터 1개가 저장 되는지 검증 (Assertions)
        Assertions.assertEquals(1L ,postRepository.count());

    }

//    @Test
//    @DisplayName("ExceptionController로 테스트")
//    void test99() throws Exception{
//        mockMvc.perform(post("/post")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"title\": null, \"content\": \"내용입니다.\"}")
//                )
//                .andExpect(status().isBadRequest()) // 기대하는 값 : andExpect
////                .andExpect(content().string("{\"title\":\"타이틀을 입력해 주세요\"}"))
//                .andExpect(jsonPath("$.code").value("400"))
//                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
//                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해 주세요"))
//                .andDo(print()); // 테스트의 대한 요청 서머리 확인
//    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        // given
        Post post = Post.builder()
                .title("123456789123456")
                .content("bar")
                .build();

        postRepository.save(post);

        // expected (When + then)
        mockMvc.perform(get("/post/{postId}", post.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567891"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());
    }

//    @Test
//    @DisplayName("글 여러개 조회")
//    void test5() throws Exception {
//        // given
//        Post post1 = postRepository.save(Post.builder()
//                .title("title_1")
//                .content("content_1")
//                .build());
//
//        Post post2 = postRepository.save(Post.builder()
//                .title("title_2")
//                .content("content_2")
//                .build());
//
//        // expected (When + then)
//        mockMvc.perform(get("/post")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()", Matchers.is(2))) // 총 개시글의 개수가 2개인지
//                .andExpect(jsonPath("$[0].id").value(post1.getId()))
//                .andExpect(jsonPath("$[0].title").value(post1.getTitle()))
//                .andExpect(jsonPath("$[0].content").value(post1.getContent()))
//                .andExpect(jsonPath("$[1].id").value(post2.getId()))
//                .andExpect(jsonPath("$[1].title").value(post2.getTitle()))
//                .andExpect(jsonPath("$[1].content").value(post2.getContent()))
//                .andDo(print());
//    }

//    @Test
//    @DisplayName("글 여러개 조회")
//    void test5() throws Exception {
//        // given
//        // IntStream.... for 문과 같은 방식..
//        List<Post> requestPosts = IntStream.range(1,31)
//                .mapToObj(i -> Post.builder()
//                        .title("쿠키블로그 제목 " + i)
//                        .content("반포자이 " + i)
//                        .build())
//                .collect(Collectors.toList());
//        postRepository.saveAll(requestPosts);
//
//        // expected (When + then)
//        mockMvc.perform(get("/post?page=1&sort=id,desc") // "/post?page=1&sort=id,desc&size=5" 원래 이랬는데 yml에 5개 지정
//                        // controller에서 RequestParam으로 받으면 yml에 one-indexed-parameters: true가 안 먹힘.
//                        // -> PageableDefault로 받아야 1 넘겼을 때 순서대로 나옴.
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()", Matchers.is(5)))
//                .andExpect(jsonPath("$[0].id").value(30))
//                .andExpect(jsonPath("$[0].title").value("쿠키블로그 제목 30"))
//                .andExpect(jsonPath("$[0].content").value("반포자이 30"))
//                .andDo(print());
//    }

    @Test
    @DisplayName("글 여러개 조회")
    void test5() throws Exception {
        // given
        // IntStream.... for 문과 같은 방식..
        List<Post> requestPosts = IntStream.range(1,20)
                .mapToObj(i -> Post.builder()
                        .title("쿠키블로그 제목 " + i)
                        .content("반포자이 " + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        // expected (When + then)
        mockMvc.perform(get("/post?page=1&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
                .andExpect(jsonPath("$[0].title").value("쿠키블로그 제목 19"))
                .andExpect(jsonPath("$[0].content").value("반포자이 19"))
                .andDo(print());
    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다.")
    void test6() throws Exception {
        // given
        // IntStream.... for 문과 같은 방식..
        List<Post> requestPosts = IntStream.range(1,20)
                .mapToObj(i -> Post.builder()
                        .title("쿠키블로그 제목 " + i)
                        .content("반포자이 " + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        // expected (When + then)
        mockMvc.perform(get("/post?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
                .andExpect(jsonPath("$[0].title").value("쿠키블로그 제목 19"))
                .andExpect(jsonPath("$[0].content").value("반포자이 19"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test7() throws Exception {
        // given
        Post post = Post.builder()
                .title("쿠키")
                .content("반포자이 ")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("쿠키걸")
                .content("반포자이")
                .build();

        // expected (When + then)
        // 수정이니까 patch로
        mockMvc.perform(patch("/post/{postId}", post.getId()) // PATCH / post/{postid}
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(postEdit)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 삭제")
    void test8() throws Exception {
        // given
        Post post = Post.builder()
                .title("쿠키")
                .content("반포자이 ")
                .build();

        postRepository.save(post);

        // expected
        mockMvc.perform(delete("/post/{postId}", post.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("존재하지 않는 게시글 조회")
    void test9 () throws Exception {
        mockMvc.perform(get("/post/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 수정")
    void test10 () throws Exception {
        // given
        PostEdit postEdit = PostEdit.builder()
                .title("쿠키걸")
                .content("반포자이")
                .build();

        // expected
        mockMvc.perform(patch("/post/{postId}",1L) // PATCH / post/{postid}
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(postEdit)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 작성 시 제목에 '바보'는 포함될 수 없다.")
    void test11() throws Exception{

        PostCreate request = PostCreate.builder()
                        .title("나는 바보입니다.")
                        .content("반포자이")
                        .build();

        String json = jacksonObjectMapper.writeValueAsString((request));

        // when
        mockMvc.perform(post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

}