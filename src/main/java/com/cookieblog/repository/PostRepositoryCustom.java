package com.cookieblog.repository;

import com.cookieblog.domain.Post;
import com.cookieblog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
