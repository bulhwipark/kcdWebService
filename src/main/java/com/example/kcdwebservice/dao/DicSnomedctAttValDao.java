package com.example.kcdwebservice.dao;

import com.example.kcdwebservice.vo.DicSnomedctAttValVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DicSnomedctAttValDao {
    List<DicSnomedctAttValVo> getAttrList();

    List<DicSnomedctAttValVo> getValList(String sctId);
}
