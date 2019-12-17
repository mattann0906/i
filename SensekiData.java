package com.example.kaisen.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.jdbc.core.BeanPropertyRowMapper.newInstance;

@Repository

public class SensekiData {

    @Autowired
    private JdbcTemplate jdbc;

    public void insert(Senseki senseki){
        var sql = "insert into RESULT values(?)";
        jdbc.update(sql,senseki.getSyouhai());
    }

    public List<Senseki>select(){
        var sql = "select * from result";
        return jdbc.query(sql,newInstance(Senseki.class));
    }
}
