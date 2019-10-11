package com.example.kcdwebservice.dao;

import com.example.kcdwebservice.vo.TempmapicdsctVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface TempmapicdsctDao {
    List<TempmapicdsctVo> select();
}
