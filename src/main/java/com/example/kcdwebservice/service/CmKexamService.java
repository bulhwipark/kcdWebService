package com.example.kcdwebservice.service;

import com.example.kcdwebservice.dao.CmKexamDao;
import com.example.kcdwebservice.vo.CmKexamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmKexamService {

    @Autowired
    private CmKexamDao cmKexamDao;


    public List<CmKexamVo> kexam_selectAll(CmKexamVo cmKexamVo) {
        return cmKexamDao.kexam_selectAll(cmKexamVo);
    }

    public CmKexamVo selectKexCdInfo(String kexCd) {
        return cmKexamDao.selectKexCdInfo(kexCd);
    }
}
