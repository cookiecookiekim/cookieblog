package com.cookieblog.request;

import com.cookieblog.exception.InvalidRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

//@Setter // setter 메서드가 없어서 런 시, title이나 content가 null로 뜨기 때문에 넣어 줬음
//@Getter
//@ToString // 클래스로 받을 때 toString을 오버라이드 하지 않으면 값이 출력되지 않음
//public class PostCreate {
//
//    @NotBlank (message = "타이틀을 입력해 주세요") // Body = {"title":"must not be blank"}를 한글로 재 설정
//    private String title; // NotBlank는 null도 걸러줌.
//
//    @NotBlank (message = "콘텐츠를 입력해 주세요")
//    private String content;
//
//    @Builder // 만약 매개변수에 title 과 content의 위치가 바뀐다면 버그 찾기가 어렵다. 그러므로 Builder를 사용한다!
//    public PostCreate(String title, String content){
//        this.title = title;
//        this.content = content;
//    }
//
//}

@Setter
@Getter
@ToString
public class PostCreate {

    @NotBlank (message = "타이틀을 입력해 주세요")
    private String title; // NotBlank는 null도 걸러줌.

    @NotBlank (message = "콘텐츠를 입력해 주세요")
    private String content;

    @Builder
    public PostCreate(String content, String title){
        this.title = title;
        this.content = content;
    }

    public void validate() {
        if (getTitle().contains("바보")){
            throw new InvalidRequest("title", "제목에 바보를 포함할 수 없습니다.");
        }
    }

}

