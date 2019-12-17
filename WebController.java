package com.example.kaisen;

import com.example.kaisen.model.Attack;
import com.example.kaisen.model.SensekiServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.kaisen.model.GameService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;




@Controller
public class WebController {

    @Autowired
    private GameService gameService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private Attack attack;

    @Autowired
    private SensekiServise sensekiServise;

    ArrayList<Integer> myAT;
    ArrayList<Integer> comAT;

    private int tesuu;

    @GetMapping("title")
    public String title(Model model){
        return "title";
    }

    @GetMapping("home")
    public String home(Model model){
        var Setumei = "あなたの戦艦の位置を決めてください";

        //list初期化
        myAT = new ArrayList<>();
        comAT = new ArrayList<>();

        tesuu = 1;

        model.addAttribute("setumei",Setumei);
        return "homepage";
    }

    @PostMapping("junbi")
    public String junbi(String tate, String yoko, Model model){


        //comのWの位置を決める
        Random rnd = new Random();

        var comtate = String.valueOf(rnd.nextInt(5)+1);
        var comyoko = String.valueOf(rnd.nextInt(5)+1);


        //デバック
        System.out.println("me:"+tate+","+yoko);
        System.out.println("com:"+comtate+","+comyoko);



        var joukyou = "戦闘開始";

        //入力された文字を判定
        var hantei = gameService.nyuunyokusyori(tate,yoko);

        if(hantei==2) {


            model.addAttribute("tate", tate);
            model.addAttribute("yoko", yoko);
            model.addAttribute("joukyou",joukyou);
            model.addAttribute("tesuu",tesuu);

            //Wの位置を記録
            httpSession.setAttribute("tate",tate);
            httpSession.setAttribute("yoko",yoko);
            httpSession.setAttribute("comtate",comtate);
            httpSession.setAttribute("comyoko",comyoko);

            return "junbi";

            //エラー判定
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
    public String game(String tate,String yoko, Model model) {


        //入力された文字を判定
        var hantei = gameService.nyuunyokusyori(tate, yoko);

        if (hantei == 2) {

            var tateAT = tate;
            var yokoAT = yoko;

            var myjuuhukuhantei = 0;
            var comjuuhukuhantei = 0;


            //comの攻撃
            Random rnd = new Random();
            var comtateAT = String.valueOf(rnd.nextInt(5) + 1);
            var comyokoAT = String.valueOf(rnd.nextInt(5) + 1);

            //攻撃位置を1~25に振り分ける
            var myAttack = attack.myAttack(tateAT, yokoAT);
            var comAttack = attack.comAttack(comtateAT, comyokoAT);


            //同じ位置の攻撃を省きつつリストに攻撃履歴を追加
            for (int n = 0; n < myAT.size(); n++) {
                if (myAttack == myAT.get(n)) {
                    myjuuhukuhantei = 1;
                }
            }

            for (int j = 0; j < comAT.size(); j++) {
                if (comAttack == comAT.get(j)) {
                    comjuuhukuhantei = 1;
                }
            }

            if (myjuuhukuhantei == 0) {
                myAT.add(myAttack);
            }

            if (comjuuhukuhantei == 0) {
                comAT.add(comAttack);
            }

            //デバック
            System.out.println(myAttack);
            System.out.println(comAttack);

            System.out.println("myAttackLIST:" + Arrays.asList(myAT));
            System.out.println("comAttackLIST:" + Arrays.asList(comAT));

            System.out.println("my Attack " + tateAT + "," + yokoAT);
            System.out.println(" ");
            System.out.println("com Attack" + comtateAT + "," + comyokoAT);
            System.out.println(" ");


            //Wの位置を取り出す
            var tate0 = (String) httpSession.getAttribute("tate");
            var yoko0 = (String) httpSession.getAttribute("yoko");
            var comtate = (String) httpSession.getAttribute("comtate");
            var comyoko = (String) httpSession.getAttribute("comyoko");

            model.addAttribute("tate", tate0);
            model.addAttribute("yoko", yoko0);
            model.addAttribute("comtate", comtate);
            model.addAttribute("comyoko", comyoko);

            model.addAttribute("myAttack", myAT);
            model.addAttribute("comAttack", comAT);


            System.out.println(hantei);


            if (tateAT.equals(comtate) && yokoAT.equals(comyoko)) {

                if (comtateAT.equals(tate0) && comyokoAT.equals(yoko0)) {
                    var joukyou = "引き分け";
                    model.addAttribute("joukyou", joukyou);
                    model.addAttribute("tesuu", tesuu);
                    var result = tesuu + "手目で引き分けました";
                    sensekiServise.kiroku(result);
                    return "draw";
                }

                var joukyou = "win";
                model.addAttribute("joukyou", joukyou);
                model.addAttribute("tesuu", tesuu);
                var result = "プレイヤーが" + tesuu + "手目で勝ちました";
                System.out.println(result);
                sensekiServise.kiroku(result);
                return "win";

            } else if (comtateAT.equals(tate0) && comyokoAT.equals(yoko0)) {

                var joukyou = "あ な た の ま け";
                model.addAttribute("joukyou", joukyou);
                model.addAttribute("tesuu", tesuu);
                var result = "CPUが" + tesuu + "手目で勝ちました";
                sensekiServise.kiroku(result);
                return "lose";
            }

            var joukyou = "二人共はずれ";
            model.addAttribute("joukyou", joukyou);
            tesuu++;
            model.addAttribute("tesuu", tesuu);

            return "game";

            //入力エラーが起こると更新していない数値を渡し手数を進めさせない
        } else if (hantei == 0) {
            var joukyou = "数字を入力して下さい";
            var tate0 = (String) httpSession.getAttribute("tate");
            var yoko0 = (String) httpSession.getAttribute("yoko");
            model.addAttribute("tate", tate0);
            model.addAttribute("yoko", yoko0);
            model.addAttribute("joukyou", joukyou);
            model.addAttribute("myAttack", myAT);
            model.addAttribute("comAttack", comAT);
            model.addAttribute("tesuu", tesuu);
            return "game";
        } else {
            var joukyou = "0~4を入力して下さい";
            var tate0 = (String) httpSession.getAttribute("tate");
            var yoko0 = (String) httpSession.getAttribute("yoko");
            model.addAttribute("tate", tate0);
            model.addAttribute("yoko", yoko0);
            model.addAttribute("joukyou", joukyou);
            model.addAttribute("myAttack", myAT);
            model.addAttribute("comAttack", comAT);
            model.addAttribute("tesuu", tesuu);
            return "game";
        }
    }







    @GetMapping("result")
    public String result(Model model){
        var allresult = sensekiServise.findAll();
        model.addAttribute("allresult",allresult);
        System.out.println("aaaa");

        return "result";
    }

}
