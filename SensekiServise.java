package com.example.kaisen.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class SensekiServise {

    @Autowired
    private SensekiData data;

    public void kiroku(String result){
        var senseki = new Senseki(result);

        data.insert(senseki);
    }

    public List<Senseki> findAll(){


        try{
            return data.select();
        }catch (DataAccessException e){
            e.printStackTrace();
        }
        return emptyList();
    }
}
