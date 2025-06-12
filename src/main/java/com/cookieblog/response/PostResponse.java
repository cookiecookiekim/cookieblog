package com.cookieblog.response;

import com.cookieblog.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 응답 전용 클래스
// 만약 클라이언트가 json title 값을 10글자만 보내달라고 한다면?
// 결론부터 말하자면 서비스에 subString(0,10)와 같이 만들어 놓지 말것!!!
// 추후 rss와 같이 확장되어 10글자 이상 내보내야 하는데 서비스에 만들어 놓으면 10글자만 나가게 됨.
// 그걸 해결하기 위해 전용 응답 클래스를 만드는 것.
@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;

    // 생성자 오버로딩 (계속 늘어나는 Builder 개선)
    public PostResponse (Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
//        this.title = title.substring(0,10); // 이렇게 하면 title이 10글자 미만일 때 오류남.
        this.title = title.substring(0,Math.min(title.length(),10)); // Math.min : 더 작은 값이 선택되어 오류 없이 안전함+
        this.content = content;
    }


//    public String getTitle(){
//        return this.title.substring(0,10);
//    }
}
