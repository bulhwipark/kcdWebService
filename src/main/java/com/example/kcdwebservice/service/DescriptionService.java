package com.example.kcdwebservice.service;

import com.example.kcdwebservice.dao.DescriptionDao;
import com.example.kcdwebservice.vo.DescriptionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DescriptionService {

    @Autowired
    DescriptionDao descriptionDao;


    public List<DescriptionVo> getDescriptionList(String sctId) {
        return descriptionDao.getDescriptionList(sctId);
    }
}
