package com.example.kcdwebservice.dao;

import com.example.kcdwebservice.vo.CmKexamVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CmKexamDao {

    List<CmKexamVo> kexam_selectAll(CmKexamVo cmKexamVo);

    CmKexamVo selectKexCdInfo(String kexCd);
}
