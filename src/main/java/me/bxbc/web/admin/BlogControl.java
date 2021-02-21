package me.bxbc.web.admin;

import com.sun.org.apache.xpath.internal.operations.Mod;
import me.bxbc.obj.Blog;
import me.bxbc.obj.Tag;
import me.bxbc.obj.User;
import me.bxbc.query.BlogQuery;
import me.bxbc.service.BlogService;
import me.bxbc.service.CommentService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 * Author: BI XI
 * Date 2021/2/12
 */

@Controller
@RequestMapping("/admin")
public class BlogControl {

    private static final String INPUT = "admin/blog-post";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";


    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model, BlogQuery blog) {
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable, blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String searchBlog(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                             Model model, BlogQuery blog) {
        model.addAttribute("page",blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    private void setTagAndType(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    @GetMapping("/blogs/add")
    public String addBlog(Model model) {
        model.addAttribute("blog", new Blog());
        setTagAndType(model);
        return INPUT;
    }

    @GetMapping("/blogs/{id}/add")
    public String editBlog(@PathVariable Long id, Model model) {
        setTagAndType(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return INPUT;
    }

    private List<Long> convertNewTags(String ids) {
        List<Long> nids = new ArrayList<>();
        if(!"".equals(ids) && ids != null){
            String[] idarray = ids.split(",");
            for(int i=0;i<idarray.length;i++){
                try{
                    Long id = Long.parseLong(idarray[i]);
                    nids.add(id);
                } catch(NumberFormatException e) {
                    Tag t = new Tag();
                    t.setTag(idarray[i]);
                    Tag r = tagService.saveTag(t);
                    if(r != null) {
                        nids.add(r.getId());
                    }
                }
            }
        }
//        System.out.println(nids.toString());
        return nids;
    }

    // 新增和编辑博客共用一个方法
    @PostMapping("/blogs")
    public String postBlog(Blog blog, HttpSession session, RedirectAttributes attributes) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(convertNewTags(blog.getTagIds())));
        Blog b = blogService.saveBlog(blog);
        if(b == null) {
            attributes.addFlashAttribute("message","操作失败");
        } else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }

    // 删除方法
    @GetMapping("/blogs/{id}/delete")
    public String blogDelete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "成功删除博客");
        return REDIRECT_LIST;
    }

}
