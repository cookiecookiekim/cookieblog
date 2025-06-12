package com.cookieblog.repository;

import com.cookieblog.domain.Post;
import com.cookieblog.domain.QPost;
import com.cookieblog.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // page 10개만 가져오는 메소드
    @Override
    public List<Post> getList(PostSearch postSearch){
        return jpaQueryFactory.selectFrom(QPost.post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(QPost.post.id.desc())  // 마지막에 작성한 글이 맨 처음 나오게 하기 위해 추가 설정
                .fetch();
    }
}
