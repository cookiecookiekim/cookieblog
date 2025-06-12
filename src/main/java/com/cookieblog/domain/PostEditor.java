package com.cookieblog.domain;

// Post에서 parameter 순서가 바뀌면 에러 잡기가 힘드므로, 정확성을 위해 생성

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;

@Getter
public class PostEditor {

    // 수정할 수 있는 필드들만 정의
    private String title = null;
    private String content = null;

    public PostEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // build/classes/java/main/com/cookieblog/domain/PostEditor 에서 복붙
    public static PostEditor.PostEditorBuilder builder() {
        return new PostEditorBuilder();
    }

    @Generated
    public String getTitle() {
        return this.title;
    }

    @Generated
    public String getContent() {
        return this.content;
    }

    @Generated
    public static class PostEditorBuilder {
        @Generated
        private String title;
        @Generated
        private String content;

        @Generated
        PostEditorBuilder() {
        }

        @Generated
        public PostEditorBuilder title(final String title) {
            if (title != null) {
                this.title = title;
            }
            return this;
        }

        @Generated
        public PostEditorBuilder content(final String content) {
            if (content != null) {
                this.content = content;
            }
            return this;
        }

        @Generated
        public PostEditor build() {
            return new PostEditor(this.title, this.content);
        }

        @Generated
        public String toString() {
            return "PostEditor.PostEditorBuilder(title=" + this.title + ", content=" + this.content + ")";
        }
    }
}
