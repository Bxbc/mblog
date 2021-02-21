package me.bxbc.dao;

import me.bxbc.obj.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Author: BI XI
 * Date 2021/2/17
 */

public interface CommentData extends JpaRepository<Comment, Long> {
    List<Comment> findByBlogId(Long id, Sort sort);

    List<Comment> findByBlogIdAndFatherCommentsNull(Long id, Sort sort);

    @Query(value = "delete from comments c where c.blog_id=?1", nativeQuery = true)
    void deleteAllByBlogId(Long id);

    @Query(value = "set foreign_key_checks = 0", nativeQuery = true)
    void closeForeignKeyCheck();

    @Query(value = "set foreign_key_checks = 1", nativeQuery = true)
    void openForeignKeyCheck();
}
