package me.bxbc.dao;

import me.bxbc.obj.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Author: BI XI
 * Date 2021/2/10
 */
public interface TagData extends JpaRepository<Tag, Long> {
    Tag findFirstByTag(String tag);

    @Query("select t from tags t")
    List<Tag> findTop(Pageable pageable);

    @Query(value = "delete from blog_tags b where b.blogs_id=?1", nativeQuery = true)
    void deleteTB(Long id);
}
