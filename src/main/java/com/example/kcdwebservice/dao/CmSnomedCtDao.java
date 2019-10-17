package com.example.kcdwebservice.dao;


import com.example.kcdwebservice.vo.CmSnomedCtVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CmSnomedCtDao {

    List<CmSnomedCtVo> getList(String sctId);
}
