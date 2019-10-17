package com.example.kcdwebservice.service;

import com.example.kcdwebservice.dao.CmSnomedCtDao;
import com.example.kcdwebservice.vo.CmSnomedCtVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmSnomedCtService {
    @Autowired
    private CmSnomedCtDao cmSnomedCtDao;
    public List<CmSnomedCtVo> getList(String sctId) {
        return cmSnomedCtDao.getList(sctId);
    }
}
