package me.bxbc.web.admin;

import me.bxbc.obj.Classification;
import me.bxbc.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * Author: BI XI
 * Date 2021/2/9
 */

@Controller
@RequestMapping("/admin")
public class TypeControl {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        //  让前端可以使用返回的page
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/type";
    }

    // Model 对象用来联系前后端的数据交流
    @GetMapping("/types/add")
    public String addNewType(Model model) {
        Classification t = new Classification();
        // 当前端需要引用对象的某个具体变量时，没法给前端传一个空对象
//        t.setId(Long.getLong("0"));
//        t.setType("学习笔记");
        model.addAttribute("mytype",t);
        return "admin/type-edit";
    }

    //  更改分类类型，需要传递一个需要更改分类的id
    @GetMapping("/types/{id}/add")
    public String editType(@PathVariable Long id, Model model) {
        model.addAttribute("mytype",typeService.getType(id));
        return "admin/type-edit";
    }

    // Model 对象用来联系前后端的数据交流
    // 获取路径传递过来的参数来得到一个Classification类对象

    @PostMapping("/types")
    public String postType(@Valid Classification type, BindingResult result, RedirectAttributes attributes,
                           Model model) {
        // 重复校验
        // System.out.println(type.getType());
        Classification repeat = typeService.getTypeByName(type.getType());
        if(repeat != null) {
            // System.out.println("---0---");
            result.rejectValue("type","type","不能重复添加分类");
            // System.out.println("---1---");
        }
        // 非空校验
        if(result.hasErrors()) {
            model.addAttribute("mytype", type);
            List<ObjectError> errorList = result.getAllErrors();
            for(ObjectError error : errorList){
                model.addAttribute("message",error.getDefaultMessage());
            }
            // System.out.println("---2---");
            return "admin/type-edit";
        }

        Classification t = typeService.saveType(type);
        if(t == null) {
            attributes.addFlashAttribute("message","新增失败");
        } else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }

    // 提交更改
    @PostMapping("/types/{id}")
    public String postEditType(@Valid Classification type, BindingResult result, RedirectAttributes attributes,
                           Model model, @PathVariable Long id) {
        // 重复校验
        // System.out.println(type.getType());
        Classification repeat = typeService.getTypeByName(type.getType());
        if(repeat != null) {
            // System.out.println("---0---");
            result.rejectValue("type","type","不能重复添加分类");
            // System.out.println("---1---");
        }
        // 非空校验
        if(result.hasErrors()) {
            model.addAttribute("mytype", type);
            List<ObjectError> errorList = result.getAllErrors();
            for(ObjectError error : errorList){
                model.addAttribute("message",error.getDefaultMessage());
            }
            // System.out.println("---2---");
            return "admin/type-edit";
        }

        Classification t = typeService.updateType(id,type);
        if(t == null) {
            attributes.addFlashAttribute("message","编辑失败");
        } else {
            attributes.addFlashAttribute("message","编辑成功");
        }
        return "redirect:/admin/types";
    }

    // 删除分类数据
    @GetMapping("/types/{id}/delete")
    public String deletetype(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
