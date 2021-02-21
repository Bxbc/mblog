package me.bxbc.web;

import me.bxbc.obj.Classification;
import me.bxbc.obj.Tag;
import me.bxbc.query.BlogQuery;
import me.bxbc.service.BlogService;
import me.bxbc.service.TagService;
import me.bxbc.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Author: BI XI
 * Date 2021/2/18
 */


@Controller
public class TagShowControl {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tagshow/{id}")
    public String tagShow(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                           @PathVariable Long id, Model model) {
        List<Tag> tags = tagService.listTag(10000);
        if(id == -1) {
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(id, pageable));
        model.addAttribute("activeTagId", id);
        return "tag";
    }
}
