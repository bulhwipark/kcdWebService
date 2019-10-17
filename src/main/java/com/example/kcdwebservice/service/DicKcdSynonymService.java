package com.example.kcdwebservice.service;

import com.example.kcdwebservice.dao.DicKcdSynonymDao;
import com.example.kcdwebservice.vo.DicKcdSynonymVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DicKcdSynonymService {

    @Autowired
    private DicKcdSynonymDao dicKcdSynonymDao;

    public List<DicKcdSynonymVo> getList(DicKcdSynonymVo dicKcdSynonymVo) {
        return dicKcdSynonymDao.getList(dicKcdSynonymVo);
    }
}
