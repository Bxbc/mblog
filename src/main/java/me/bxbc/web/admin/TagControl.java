package me.bxbc.web.admin;

import me.bxbc.obj.Tag;
import me.bxbc.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * Author: BI XI
 * Date 2021/2/10
 */

@Controller
@RequestMapping("/admin")
public class TagControl {
    @Autowired
    private TagService tagService;

    @GetMapping("/tag")
    public String tags(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        //  让前端可以使用返回的page
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tag";
    }

    // Model 对象用来联系前后端的数据交流
    @GetMapping("/tag/add")
    public String addNewTag(Model model) {
        Tag t = new Tag();
        // 当前端需要引用对象的某个具体变量时，没法给前端传一个空对象
//        t.setId(Long.getLong("0"));
//        t.setTag("java");
        model.addAttribute("mytag",t);
        return "admin/tag-edit";
    }

    //  更改分类类型，需要传递一个需要更改分类的id
    @GetMapping("/tag/{id}/add")
    public String editTag(@PathVariable Long id, Model model) {
        model.addAttribute("mytag",tagService.getTag(id));
        return "admin/tag-edit";
    }

    // Model 对象用来联系前后端的数据交流
    // 获取路径传递过来的参数来得到一个Classification类对象

    @PostMapping("/tag")
    public String postTag(@Valid Tag tag, BindingResult result, RedirectAttributes attributes,
                           Model model) {
        // 重复校验
        Tag repeat = tagService.getTagByName(tag.getTag());
        if(repeat != null) {
            System.out.println("---0---");
            result.rejectValue("tag","tag","不能重复添加分类");
            System.out.println("---1---");
        }
        // 非空校验
        if(result.hasErrors()) {
            model.addAttribute("mytag", tag);
            List<ObjectError> errorList = result.getAllErrors();
            for(ObjectError error : errorList){
                model.addAttribute("message",error.getDefaultMessage());
            }
            System.out.println("---2---");
            return "/admin/tag-edit";
        }

        Tag t = tagService.saveTag(tag);
        if(t == null) {
            attributes.addFlashAttribute("message","新增失败");
        } else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/tag";
    }

    // 提交更改
    @PostMapping("/tag/{id}")
    public String postEditTag(@Valid Tag tag, BindingResult result, RedirectAttributes attributes,
                               Model model, @PathVariable Long id) {
        // 重复校验

        Tag repeat = tagService.getTagByName(tag.getTag());
        if(repeat != null) {
            System.out.println("---4---");
            result.rejectValue("tag","tag","不能重复添加分类");
            // System.out.println("---1---");
        }
        // 非空校验
        if(result.hasErrors()) {
            model.addAttribute("mytag", tag);
            List<ObjectError> errorList = result.getAllErrors();
            for(ObjectError error : errorList){
                model.addAttribute("message",error.getDefaultMessage());
            }
            System.out.println("---5---");
            return "/admin/tag-edit";
        }

        Tag t = tagService.updateTag(id,tag);
        if(t == null) {
            attributes.addFlashAttribute("message","编辑失败");
        } else {
            attributes.addFlashAttribute("message","编辑成功");
        }
        return "redirect:/admin/tag";
    }

    // 删除分类数据
    @GetMapping("/tag/{id}/delete")
    public String deleteTag(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tag";
    }
}
