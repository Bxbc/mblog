package me.bxbc.service;

import me.bxbc.obj.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Author: BI XI
 * Date 2021/2/10
 */

public interface TagService {
    Tag saveTag(Tag tag);
    Tag getTag(Long id);
    Page<Tag> listTag(Pageable pageable);
    List<Tag> listTag();
    List<Tag> listTag(String ids);
    List<Tag> listTag(List<Long> ids);
    List<Tag> listTag(Integer size);
    Tag updateTag(Long id, Tag tag);
    Tag getTagByName(String tag);
    void deleteTag(Long id);
    void deleteTagAndBlog(Long id);
}
