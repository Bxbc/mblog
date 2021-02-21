package me.bxbc.dao;

import me.bxbc.obj.Classification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * Author: BI XI
 * Date 2021/2/9
 */


public interface TypeData extends JpaRepository<Classification, Long> {
    // 记住要按照命名规则来
    Classification findFirstByType(String type);

    @Query("select t from types t")
    List<Classification> findTop(Pageable pageable);

}
