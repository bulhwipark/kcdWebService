package com.example.kcdwebservice.dao;

import com.example.kcdwebservice.vo.CmKcdVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CmKcdDao {
    List<CmKcdVo> select();
}
