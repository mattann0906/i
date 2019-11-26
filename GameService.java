package com.example.kaisen.model;


import org.springframework.stereotype.Service;

@Service
public class GameService {

    public boolean nyuunyokusyori(String tate,String yoko){

        if((tate.equals("0")||tate.equals("1")||tate.equals("2")||tate.equals("3")||tate.equals("4")) && (yoko.equals("0")||yoko.equals("1")||yoko.equals("2")||yoko.equals("3")||yoko.equals("4"))){
            return true;
        }else {
            return false;
        }
    }
}
