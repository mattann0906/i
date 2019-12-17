package com.example.kaisen.model;

public class Senseki {

    private String syouhai;

    public Senseki(){
        this.syouhai="";
    }

    public Senseki(String syouhai){
        this.syouhai = syouhai;
    }

    public void setSyouhai(String syouhai){ this.syouhai=syouhai;}

    public String getSyouhai(){return syouhai;}
}
