package com.cookieblog.request;

// 페이징 처리할 때 추후, 검색 옵션이라든지 등등 추가될 걸 생각해서 생성

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PostSearch {

    private final int MAX_SIZE = 20;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    public PostSearch (Integer page , Integer size) {
        this.page = page;
        this.size = size;
    }

    public long getOffset(){ // Math.max 쓰는 게 0 넘겼을 때 정상적으로 1이 아니라 음수가 나오니까 에러남
        return (long) (Math.max(1,page) - 1) * Math.max(MAX_SIZE, size);
    }
}
