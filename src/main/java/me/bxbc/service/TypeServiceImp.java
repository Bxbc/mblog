package me.bxbc.service;

import me.bxbc.dao.TypeData;
import me.bxbc.notFoundException;
import me.bxbc.obj.Classification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: BI XI
 * Date 2021/2/9
 */

@Service
public class TypeServiceImp implements TypeService{
    @Autowired
    private TypeData typeData;

    // 数据库操作都用事务注解
    @Transactional
    @Override
    public Classification saveType(Classification type) {
        return typeData.save(type);
    }

    @Transactional
    @Override
    public Classification getType(Long id) {
        return typeData.getOne(id);
    }

    @Transactional
    @Override
    public Page<Classification> listType(Pageable pageable) {
        return typeData.findAll(pageable);
    }

    @Transactional
    @Override
    public Classification updateType(Long id, Classification type) {
        Classification t = typeData.getOne(id);
        if(t == null ) {
            throw new notFoundException("该类型不存在，无法编辑");
        }
        BeanUtils.copyProperties(type,t);
        return typeData.save(t);
    }
    @Transactional
    @Override
    public void deleteType(Long id) {
        typeData.deleteById(id);
    }

    @Override
    public Classification getTypeByName(String type) {
        return typeData.findFirstByType(type);
    }

    @Override
    public List<Classification> listType() {
        return typeData.findAll();
    }

    @Override
    public List<Classification> listType(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return typeData.findTop(pageable);
    }
}
