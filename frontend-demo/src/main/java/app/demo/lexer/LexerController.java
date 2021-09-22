package app.demo.lexer;

import app.demo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping(path="lexerControl/")
public class LexerController {

    @PostMapping("algebra")
    public boolean algebra(@RequestBody String string){
        System.out.println("Primio string:");
        System.out.println(string);
        return true;
    }
    @PostMapping("matrix")
    public boolean matrix(@RequestBody String string){
        System.out.println("Primio string:");
        System.out.println(string);
        return true;
    }

    @PostMapping("logic")
    public LogikaVrati logic(@RequestBody String string){
        System.out.println("Primio string:");
        System.out.println(string);

        boolean[][] vrati=new boolean[3][3];

        for (int i= 0 ;i < 3; i ++ )
            for(int j=0 ; j< 3 ; j++){
                Random r = new Random(123);
                vrati[i][j]= r.nextDouble() >= 0.7;

            }

        LogikaVrati v= new LogikaVrati(3,vrati);

        return v;
    }

}
