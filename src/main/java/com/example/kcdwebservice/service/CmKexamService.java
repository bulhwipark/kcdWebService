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

    public List<CmKexamVo> kexam_selectMapping(CmKexamVo cmKexamVo) {
        return cmKexamDao.kexam_selectMapping(cmKexamVo);
    }

    public List<CmKexamVo> medi_selectNoMapping(CmKexamVo cmKexamVo) {
        return cmKexamDao.kexam_selectNoMapping(cmKexamVo);
    }

    public String kexam_totalCnt(String mappingStatus, CmKexamVo cmKexamVo) {
        return cmKexamDao.kexam_totalCnt(mappingStatus, cmKexamVo);
    }
}
