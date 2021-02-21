package me.bxbc.service;

import me.bxbc.obj.Classification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Author: BI XI
 * Date 2021/2/9
 */

public interface TypeService {
    Classification saveType(Classification type);

    Classification getType(Long id);

    Page<Classification> listType(Pageable pageable);

    List<Classification> listType();

    List<Classification> listType(Integer size);

    Classification updateType(Long id, Classification type);

    Classification getTypeByName(String type);

    void deleteType(Long id);
}
