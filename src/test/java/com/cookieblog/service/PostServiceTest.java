package com.cookieblog.service;

import com.cookieblog.domain.Post;
import com.cookieblog.exception.PostNotFound;
import com.cookieblog.repository.PostRepository;
import com.cookieblog.request.PostCreate;
import com.cookieblog.request.PostEdit;
import com.cookieblog.request.PostSearch;
import com.cookieblog.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @BeforeEach
    void clean () {
        postRepository.deleteAll();
    }

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("글 작성")
    void test1(){

        // given
        PostCreate postCreate = PostCreate
                .builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        postService.write(postCreate);

        // then (검증)
        // 촘 글 개수 1L
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2 () {
        // given
        Post requestPost = Post
                .builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(requestPost);

        // when
        PostResponse post = postService.get(requestPost.getId());

        // then
        assertNotNull(post);
        assertEquals(1L , postRepository.count());
        assertEquals("foo",post.getTitle());
        assertEquals("bar",post.getContent());
    }

//    @Test
//    @DisplayName("글 여러개 조회")
//    void test3 () {
//        // given
//
//        // IntStream.... for 문과 같은 방식..
//        List<Post> requestPosts = IntStream.range(1,20)
//                        .mapToObj(i -> Post.builder()
//                                    .title("쿠키블로그 제목 " + i)
//                                    .content("반포자이 " + i)
//                                    .build())
//                        .collect(Collectors.toList());
//
//        postRepository.saveAll(requestPosts);
////        postRepository.saveAll(List.of( // List.of로 여러개 한 번에 저장하기
////                Post.builder()
////                        .title("foo1")
////                        .content("bal1")
////                        .build()
////                        ,
////                Post.builder()
////                        .title("foo2")
////                        .content("bal2")
////                        .build()
////        ));
//
//        // 수동이기 때문에 첫 페이지인 것 인지 시키기
//        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
//
//        // when
//        List<PostResponse> posts = postService.getList(pageable);
//
//        // then
////        assertNotNull(posts);
//        assertEquals(10L , posts.size());
//        assertEquals("쿠키블로그 제목 19", posts.get(0).getTitle());
//    }

    // query dsl
    @Test
    @DisplayName("글 여러개 조회")
    void test3 () {
        // given

        // IntStream.... for 문과 같은 방식..
        List<Post> requestPosts = IntStream.range(1,20)
                .mapToObj(i -> Post.builder()
                        .title("쿠키블로그 제목 " + i)
                        .content("반포자이 " + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
//                .size(10)
                .build();

        // when
        List<PostResponse> posts = postService.getList(postSearch);

        // then
//        assertNotNull(posts);
        assertEquals(10L , posts.size());
        assertEquals("쿠키블로그 제목 19", posts.get(0).getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test4 () {
        // given
        Post post = Post.builder()
                        .title("쿠키")
                        .content("반포자이 ")
                        .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("쿠키걸")
                .build();

        postService.edit(post.getId(),postEdit);

        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(()-> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));

        Assertions.assertEquals("쿠키걸", changePost.getTitle());
    }

    @Test
    @DisplayName("글 내용 수정")
    void test5 () {
        // given
        Post post = Post.builder()
                .title("쿠키")
                .content("반포자이 ")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("쿠키걸")
                .content("초가집")
                .build();

        postService.edit(post.getId(),postEdit);

        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(()-> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));

        Assertions.assertEquals("초가집", changePost.getContent());
    }

    @Test
    @DisplayName("글 내용 수정")
    void test10 () {
        // given
        Post post = Post.builder()
                .title("쿠키")
                .content("반포자이 ")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("초가집")
                .build();

        postService.edit(post.getId(),postEdit);

        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(()-> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));

        Assertions.assertEquals("쿠키", changePost.getTitle());
        Assertions.assertEquals("초가집", changePost.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    void test11 () {
        // given
        Post post = Post.builder()
                .title("쿠키")
                .content("반포자이 ")
                .build();

        postRepository.save(post);

        // when
        postService.delete(post.getId());

        // then
        Assertions.assertEquals(0, postRepository.count());
    }

    // PostNotFound 클래스 만들어서 예외처리
    @Test
    @DisplayName("글 1개 조회 - 존재하지 않는 글")
    void test7 () {
        // given
        Post post = Post
                .builder()
                .title("쿠키")
                .content("반포자이")
                .build();

        postRepository.save(post);

        // expected
        Assertions.assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        });

    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    void test8 () {
        // given
        Post post = Post.builder()
                .title("쿠키")
                .content("반포자이 ")
                .build();

        postRepository.save(post);

        // then
        Assertions.assertThrows(PostNotFound.class, () -> {
            postService.delete(post.getId() + 1L);
        });
    }

    @Test
    @DisplayName("글 내용 수정 - 존재하지 않는 글")
    void test9 () {
        // given
        Post post = Post.builder()
                .title("쿠키")
                .content("반포자이 ")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("초가집")
                .build();

        // expected
        Assertions.assertThrows(PostNotFound.class, () -> {
            postService.edit(post.getId() + 1L, postEdit);
        });
    }
}