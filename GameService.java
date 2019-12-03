package com.example.kaisen.model;


import org.springframework.stereotype.Service;

@Service
public class GameService {

    public int nyuunyokusyori(String tate,String yoko) {

        if(tate.isEmpty()||yoko.isEmpty()){
            return 1;
        }


        for (int i = 0; i < tate.length(); i++) {

            if (Character.isDigit(tate.charAt(i)) == true) {
                continue;
            } else {
                return 0;
            }
        }

        for (int n = 0; n < yoko.length(); n++){

            if(Character.isDigit(yoko.charAt(n)) == true){
                continue;
            } else {
                return 0;
            }
        }

        int Tate = Integer.parseInt(tate);
        int Yoko = Integer.parseInt(yoko);

        if(Tate>0&&Tate<=5&&Yoko>0&&Yoko<=5){
            return 2;
        }else {
            return 1;
        }


        }
    }

