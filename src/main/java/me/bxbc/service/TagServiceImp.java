package me.bxbc.service;

import me.bxbc.dao.TagData;
import me.bxbc.notFoundException;
import me.bxbc.obj.Classification;
import me.bxbc.obj.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: BI XI
 * Date 2021/2/10
 */

@Service
@Transactional
public class TagServiceImp implements TagService{
    @Autowired
    private TagData tagData;

    @Override
    public Tag saveTag(Tag tag) {
        return tagData.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagData.getOne(id);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagData.findAll(pageable);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagData.getOne(id);
        if(t == null) {
            throw new notFoundException("该标签不存在，无法编辑");
        }
        BeanUtils.copyProperties(tag,t);
        return tagData.save(t);
    }

    @Override
    public void deleteTag(Long id) {
        tagData.deleteById(id);
    }

    @Override
    public Tag getTagByName(String tag) {
        return tagData.findFirstByTag(tag);
    }

    @Override
    public List<Tag> listTag() {
        return tagData.findAll();
    }


    // ids 是字符串形式的集合
    @Override
    public List<Tag> listTag(String ids) {

        return tagData.findAllById(convert2List(ids));
    }

    @Override
    public List<Tag> listTag(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return tagData.findTop(pageable);
    }

    @Override
    public List<Tag> listTag(List<Long> ids) {
        return tagData.findAllById(ids);
    }

    private List<Long> convert2List(String ids) {
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for(int i=0;i<idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Override
    public void deleteTagAndBlog(Long id) {
        tagData.deleteTB(id);
    }
}
