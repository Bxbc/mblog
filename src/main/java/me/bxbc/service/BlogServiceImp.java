package me.bxbc.service;

import me.bxbc.dao.BlogData;
import me.bxbc.notFoundException;
import me.bxbc.obj.Blog;
import me.bxbc.query.BlogQuery;
import me.bxbc.util.MarkDownUtils;
import me.bxbc.util.MyBeanUtlis;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * Author: BI XI
 * Date 2021/2/12
 */

@Service
@Transactional
public class BlogServiceImp implements BlogService{

    @Autowired
    private BlogData blogData;

    @Override
    public Blog getBlog(Long id) {
        return blogData.getOne(id);
    }

    // root 是 查询对象blog的映射
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogData.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%" + blog.getTitle() +"%" ));
                }
                if(blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.get("type").get("id"), blog.getTypeId()));
                }
                if(blog.isRecommend()) {
                    predicates.add((cb.equal(root.<Boolean>get("recommed"), blog.isRecommend())));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViewCount(0);
            return blogData.save(blog);
        } else {
            blog.setUpdateTime(new Date());
            return updateBlog(blog.getId(),blog);
        }

    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogData.getOne(id);
        if(b == null) {
            throw new notFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, b, MyBeanUtlis.getNullPropertyName(blog));
        return blogData.save(b);
    }

    @Override
    public void deleteBlog(Long id) {
        blogData.deleteById(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogData.findAll(pageable);
    }

    @Override
    public List<Blog> listBlog(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogData.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {

        return blogData.findByQuery(query, pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogData.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                // 关联查询
                Join join = root.join("tags");
                return cb.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogData.findGroupYear();
        Map<String, List<Blog>> blogMap = new HashMap<>();
        for(String year : years) {
            blogMap.put(year, blogData.findBlogByYears(year));
        }
        return blogMap;
    }

    @Override
    public Long countBlog() {
        return blogData.count();
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogData.getOne(id);
        if(blog == null) {
            throw new notFoundException("该博客不存在");
        }
        // 希望原数据库不变
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        String markdown = MarkDownUtils.markdown2HtmlExtensions(content);
        b.setContent(markdown);
        blogData.updateViewCount(id);
        return b;
    }
}
