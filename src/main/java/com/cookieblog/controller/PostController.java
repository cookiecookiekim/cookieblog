package com.cookieblog.controller;

import com.cookieblog.domain.Post;
import com.cookieblog.exception.InvalidRequest;
import com.cookieblog.request.PostCreate;
import com.cookieblog.request.PostEdit;
import com.cookieblog.request.PostSearch;
import com.cookieblog.response.PostResponse;
import com.cookieblog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController // 데이터 기반으로 API 응답을 위해 일반 Controller 대신 RestController 사용
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 글 등록
//    @PostMapping("/post")
//    public String post (@RequestParam String title, @RequestParam String content) {
//        log.info("title={}, content={}", title, content);
//        return "온뇽";
//    }

//    @PostMapping("/post")
//    public String post (@RequestParam Map<String, String> params) {
//        log.info("params={}", params);
//        return "온뇽";
//    }

    // PostCreate 클래스로 받을 때 (x-www-form-urlencoded 방식)
//    @PostMapping("/post")
//    public String post(@ModelAttribute PostCreate params){
//        log.info("params={}",params);
//        return "온뇽";
//    }

    // json으로
//    @PostMapping("/post")
//    public String post(@RequestBody PostCreate params){
//        log.info("params={}",params);
//        return "온뇽";
//    }

//    @PostMapping("/post")
//    public Map<String , String> post(@RequestBody @Valid PostCreate params) {
        // valid 검증 시, 빈값 넘어오면 컨트롤러까지 안 넘어오고 spring에서 잡아줌
        // NotBlank 걸었을 때엔 컨트롤러로 안 넘어오는데 클라이언트 개발자에게 얘기해 줘야 한다. 그럴 땐 컨트롤러로 넘어올 수 있도록 BindingResult 클래스 사용

    // 데이터를 검증하는 이유

    // 1. client 개발자가 깜빡할 수 있고, 실수로 값을 안 보낼 수 있다.
    // 2. client bug로 값이 누락될 수 있다.
    // 3. 외부에 해커가 값을 임의로 조작해서 보낼 수 있다.
    // 4. DB에 값을 저장할 때 의도치 않은 오류가 발생할 수 있다.
    // 5. 서버 개발자의 편안함을 위해서.

//        log.info("params={}", params.toString());

        // 검증 (에러 발생)
        // 수십 가지의 데이터면 이 방법은 빡세다.
        // 개발 팁으로는 → 무언가 3번 이상 반복 작업할 때 내가 잘못하고 있는 건 아닐지 의심하기. (자동화 고려)
        // 생각보다 검증해야할 게 많다. (꼼꼼하지 않을 수 있다.)
//        String title = params.getTitle();
//        if(title == null || title.equals("")){
//            throw new Exception("타이틀 값이 없음");
//        }
//        String content = params.getContent();
//        if(title == null || title.equals("")){
//        }

        // < 아래 코드의 단점 >
        // 여러개의 에러 처리 어렵다 (현재 0번째만 하고 있음)
        // HashMap 사용했는데 응답 클래스 만들어 주는 게 좋음
//        if (result.hasErrors()) {
//            List<FieldError> fieldErrors = result.getFieldErrors(); // result에 error들을 가져온다
//            FieldError firstFieldError = fieldErrors.get(0); // 0번째 데이터 가져온다
//            String fieldName = firstFieldError.getField(); // title 자체가 getField 안에 들어감
//            String errorMessage = firstFieldError.getDefaultMessage(); // ...에러 메시지가 들어감
//
//            Map<String , String> error = new HashMap<>();
//            error.put(fieldName, errorMessage);
//            return error;
//        }

        // 만약 빈 값이 아니라 "           " 이런 값이 들어 온다면??? NotBlank
//        return Map.of(); // default 리턴 값 지정
        //  Body = {"title":"must not be blank"} 출력
//    }

//    @PostMapping("/post")
//    public Map<String , String> post(@RequestBody @Valid PostCreate request) {
//        postService.write(request);
//        return Map.of(); // default 리턴 값 지정
//    }

    @PostMapping("/post")
    public void post(@RequestBody @Valid PostCreate request) {
        // Case 1. 저장한 데이터 Entity -> response로 응답하기
        // Case 2. 저장한 데이터의 primary_id -> response 응답하기
        //  클라이언트에서 수신한 ID를 글 조회 API를 통해 데이터를 수신받음
        // Case 3. 응답 필요 없음 → 클라이언트에서 모든 POST(글) 데이터 CONTEXT 잘 관리함
        request.validate();
        postService.write(request);

        System.out.println("hello world2");
    }

    // 게시글 하나 조회할 때
    @GetMapping("/post/{postId}")
    public PostResponse get (@PathVariable Long postId) {

        return postService.get(postId);
    }

    // 여러 개의 게시글 가져올 때
    @GetMapping ("/post")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch){ //@PageableDefault로 받으면 yml에 글 개수를 지정해도 default로 10개 지정됨
                                                    //@PageableDefault로 받으로면 @PageableDefault(size = 5) 이렇게 설정하면 됨!
        return postService.getList(postSearch);
    }

    // 응답도 주도록 수정 (원래는 void)
    @PatchMapping ("/post/{postId}")
    public PostResponse edit(@PathVariable Long postId , @RequestBody @Valid PostEdit request){
        return postService.edit(postId, request);
    }

    @DeleteMapping ("/post/{postId}")
    public void delete (@PathVariable Long postId) {
        postService.delete(postId);
    }
}
