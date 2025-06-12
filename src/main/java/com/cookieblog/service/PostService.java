package com.cookieblog.service;

import com.cookieblog.domain.Post;
import com.cookieblog.domain.PostEditor;
import com.cookieblog.exception.PostNotFound;
import com.cookieblog.repository.PostRepository;
import com.cookieblog.request.PostCreate;
import com.cookieblog.request.PostEdit;
import com.cookieblog.request.PostSearch;
import com.cookieblog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate){ // 글 저장 메서드
        // PostCreate는 Entity가 아니므로 변환해야함
        // postCreate -> Entity
        Post post = Post.builder()
                        .title(postCreate.getTitle())
                        .content(postCreate.getContent())
                        .build();

        postRepository.save(post);
    }

    // 글 조회 서비스
    public PostResponse get (Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

//    public List<PostResponse> getList(Pageable pageable) {
////        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC , "id")); // Sort.by는 id 기준으로 내림차순으로...
//        return postRepository.findAll(pageable).stream()
//                .map(post -> new PostResponse(post)) // 생성자 오버로딩 (계속 늘어나는 Builder 개선)
//                .collect(Collectors.toList());
//    }

    // query dsl로 설정
    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    // 수정하기
    @Transactional
    public PostResponse edit (Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

//        if (postEdit.getTitle() != null){
//            editorBuilder.title(postEdit.getTitle()).build();
//        }
//
//        // 만약 content가 null이라면 아래 수행 안 되니까 에러 안 남
//        if (postEdit.getContent() != null) {
//            editorBuilder.content(postEdit.getContent()).build();
//        }

        post.edit(postEditor);
//        postRepository.save(post); // @Transactional 있으면 굳이 안 써도 됨

        return new PostResponse(post);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        // 존재 하는 경우
        postRepository.delete(post);
    }
}
