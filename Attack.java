package com.example.kaisen.model;

import org.springframework.stereotype.Service;

@Service
public class Attack {

    public int myAttack (String yokoAT, String tateAT) {

        var AT = 0;
        var i = 0;
        var j = 0;

        int tate = Integer.parseInt(tateAT);
        int yoko = Integer.parseInt(yokoAT);

        for (i = 0; i < tate - 1; i++) {
            AT = AT + 5;
        }
        for (j = 0; j < yoko; j++) {
            AT = AT + 1;
        }
        return AT;
    }

    public int comAttack (String comyokoAT, String comtateAT){

        var comAT = 0;
        var i=0;
        var j=0;

        int tate = Integer.parseInt(comtateAT);
        int yoko = Integer.parseInt(comyokoAT);

        for(i=0;i<tate-1;i++) {
            comAT = comAT + 5;
        }
            for(j=0;j<yoko;j++){
                comAT=comAT+1;
            }


        return comAT;
    }
}
