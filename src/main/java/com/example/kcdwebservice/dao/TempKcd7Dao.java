package com.example.kcdwebservice.dao;

import com.example.kcdwebservice.vo.TempKcd7Vo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TempKcd7Dao {
    List<TempKcd7Vo> select();
}
