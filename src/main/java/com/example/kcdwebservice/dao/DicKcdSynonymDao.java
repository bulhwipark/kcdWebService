package com.example.kcdwebservice.dao;

import com.example.kcdwebservice.vo.DicKcdSynonymVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DicKcdSynonymDao {

    List<DicKcdSynonymVo> getList(DicKcdSynonymVo dicKcdSynonymVo);
}
