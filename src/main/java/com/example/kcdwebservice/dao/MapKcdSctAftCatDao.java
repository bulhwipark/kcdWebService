package com.example.kcdwebservice.dao;

import com.example.kcdwebservice.vo.MapKcdSctAftCatVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MapKcdSctAftCatDao {
    void attValInsert(MapKcdSctAftCatVo mapKcdSctAftCatVo);
}
