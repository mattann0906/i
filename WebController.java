package com.example.kaisen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.kaisen.model.GameService;

import java.time.LocalDateTime;


@Controller
public class WebController {

    @Autowired
    private GameService gameService;


    @GetMapping("home")
    public String home(Model model){
        var Setumei = "あなたの戦艦の位置を決めてください";
        model.addAttribute("setumei",Setumei);
        return "homepage";
    }

    @PostMapping("game")
    public String game(String tate, String yoko, Model model){
        var Tate = tate;
        var Yoko = yoko;

        var hantei = gameService.nyuunyokusyori(Tate,Yoko);

        if(hantei==2) {


            model.addAttribute("tate", Tate);
            model.addAttribute("yoko", Yoko);

            return "game";

        }else if(hantei==0){
            var Setumei = "数字を入力して下さい";
            model.addAttribute("setumei",Setumei);
            return "homepage";
        }else {
            var Setumei = "0~4を入力して下さい";
            model.addAttribute("setumei",Setumei);
            return "homepage";
        }

    }

}
