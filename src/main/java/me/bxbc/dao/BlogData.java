package me.bxbc.dao;

import me.bxbc.obj.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: BI XI
 * Date 2021/2/12
 */

@Repository
public interface BlogData extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    @Query("select b from blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update blog b set b.viewCount = b.viewCount + 1 where b.id = ?1")
    int updateViewCount(Long id);

    @Query(value = "select date_format(b.update_time,'%Y') as year from blog b order by year desc",nativeQuery = true)
    List<String> findGroupYear();

    @Query(value = "select * from blog b where date_format(b.update_time,'%Y') = ?1", nativeQuery = true)
    List<Blog> findBlogByYears(String year);


}
