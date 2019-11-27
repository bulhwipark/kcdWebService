package com.example.kcdwebservice.dao;

import com.example.kcdwebservice.vo.DescriptionVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DescriptionDao {
    List<DescriptionVo> getDescriptionList(String sctId);
}
