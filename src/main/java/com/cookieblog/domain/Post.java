package com.cookieblog.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob // java에서는 String이지만 DB에서는 Long Text 형태로 생성되도록
    private String content;

    @Builder
    public Post (String title, String content){
        this.title = title;
        this.content = content;
    }

//    public String getTitle (){
//        return this.title.substring(0,10);
//    } 여기에 이렇게 작성하면 추후에 문제가 될 수 있다. PostResponse 참조

    // 이렇게 해야 파라미터 순서가 바뀌어도 정확함, PostEditor 생성 이유
    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(title)
                .content(content);
    }

    // 위에가 너무 어려워서 이렇게
    public void edit (PostEditor postEditor) {
        this.title = postEditor.getTitle();
        this.content = postEditor.getContent();
    }
}
