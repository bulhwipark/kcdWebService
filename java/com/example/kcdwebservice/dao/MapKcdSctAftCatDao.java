package com.example.kcdwebservice.dao;

import com.example.kcdwebservice.vo.MapKcdSctAftCatVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MapKcdSctAftCatDao {
    void attValInsert(MapKcdSctAftCatVo mapKcdSctAftCatVo);

    List<MapKcdSctAftCatVo> getList(MapKcdSctAftCatVo mapKcdSctAftCatVo);

    void attValDelete(MapKcdSctAftCatVo mapKcdSctAftCatVo);
}
