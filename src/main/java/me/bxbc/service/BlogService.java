package me.bxbc.service;

import me.bxbc.obj.Blog;
import me.bxbc.query.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Author: BI XI
 * Date 2021/2/12
 */

public interface BlogService {
    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    Page<Blog> listBlog (Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog (Pageable pageable);

    Page<Blog> listBlog (String query, Pageable pageable);

    // 自定义查询数量
    List<Blog> listBlog(Integer size);

    // 根据tag查询blog
    Page<Blog> listBlog(Long tagId, Pageable pageable);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    // 返回根据年限匹配的归档博客
    Map<String, List<Blog>> archiveBlog();

    // 获得博客数量
    Long countBlog();


    void deleteBlog(Long id);
}
