package com.example.kaisen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.kaisen.model.GameService;

import javax.servlet.http.HttpSession;
import java.util.Random;




@Controller
public class WebController {

    @Autowired
    private GameService gameService;

    @Autowired
    private HttpSession httpSession;


    @GetMapping("home")
    public String home(Model model){
        var Setumei = "あなたの戦艦の位置を決めてください";



        model.addAttribute("setumei",Setumei);
        return "homepage";
    }

    @PostMapping("junbi")
    public String junbi(String tate, String yoko, Model model){

        Random rnd = new Random();

        var comtate = String.valueOf(rnd.nextInt(4)+1);
        var comyoko = String.valueOf(rnd.nextInt(4)+1);

        var myAttack = Integer.parseInt(tate)*Integer.parseInt(yoko);
        var comAttack = Integer.parseInt(comtate)*Integer.parseInt(comyoko);




        System.out.println("me:"+tate+","+yoko);
        System.out.println("com:"+comtate+","+comyoko);



        var joukyou = "戦闘開始";

        var hantei = gameService.nyuunyokusyori(tate,yoko);

        if(hantei==2) {


            model.addAttribute("tate", tate);
            model.addAttribute("yoko", yoko);
            model.addAttribute("joukyou",joukyou);
            model.addAttribute("myAttack",myAttack);
            model.addAttribute("comAttack",comAttack);

            httpSession.setAttribute("tate",tate);
            httpSession.setAttribute("yoko",yoko);
            httpSession.setAttribute("comtate",comtate);
            httpSession.setAttribute("comyoko",comyoko);

            return "junbi";

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

    @PostMapping("game")
    public String game(String tate,String yoko, Model model){

        var tateAT = tate;
        var yokoAT = yoko;

        Random rnd = new Random();
        var comtateAT = String.valueOf(rnd.nextInt(5)+1);
        var comtoyoAT = String.valueOf(rnd.nextInt(5)+1);

        System.out.println("my Attack "+tateAT+","+yokoAT);
        System.out.println(" ");
        System.out.println("com Attack"+comtateAT+","+comtoyoAT);
        System.out.println(" ");

        var tate0 = (String)httpSession.getAttribute("tate");
        var yoko0 = (String)httpSession.getAttribute("yoko");
        var comtate = (String)httpSession.getAttribute("comtate");
        var comyoko = (String)httpSession.getAttribute("comyoko");

        model.addAttribute("tate", tate0);
        model.addAttribute("yoko", yoko0);

        var hantei = gameService.nyuunyokusyori(tateAT,yokoAT);

        if(hantei==2) {

            if(tateAT.equals(comtate)&&yokoAT.equals(comyoko)){

                if(comtateAT.equals(tate0)&&comtoyoAT.equals(yoko0)){
                    var joukyou = "引き分け";
                    model.addAttribute("joukyou",joukyou);
                    return "win";
                }

                var joukyou = "ほな 戴きます";
                model.addAttribute("joukyou",joukyou);
                return "win";

            } else if(comtateAT.equals(tate0)&&comtoyoAT.equals(yoko0)){

                var joukyou = "あ な た の ま け";
                model.addAttribute("joukyou",joukyou);
                return "win";
            }

            var joukyou = "二人共はずれ";
            model.addAttribute("joukyou",joukyou);

            return "game";

        }else if(hantei==0){
            var joukyou = "数字を入力して下さい";
            model.addAttribute("joukyou",joukyou);
            return "game";
        }else {
            var joukyou = "0~4を入力して下さい";
            model.addAttribute("joukyou",joukyou);
            return "game";
        }





    }

}
