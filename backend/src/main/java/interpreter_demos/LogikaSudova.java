package interpreter_demos;

import jep.Jep;
import jep.JepException;

//  BITAN UVJET: varijable moraju biti po redu, i granica je 10 varijabli
public class LogikaSudova {

    public static void main(String[] args) throws JepException {

        Jep jep = new Jep(false,"/home/iva/java_project-main/lucija/aplikacija/springboot-helloworld-tutorial/src/interpreter_python_scripts/");
        int n = 4;
        String binarni_string = "";
        int granica = (int) Math.pow(2, n);

        for(int i = 0; i < granica; i++){
            String binarni_broj = pretvori_u_binarni(i);
            binarni_string = dodaj_nule(binarni_broj, n);
           // System.out.println(binarni_broj);
           // System.out.println(binarni_string);
            boolean[] test = new boolean[10];
            test = napravi_niz(test, binarni_string);


            jep.set("ulaz", "!(P2&!!(P1->P0))");
            jep.set("brojVarijabli", n);
            jep.set("nizIstinitosti", test);
            jep.runScript("src/interpreter_python_scripts/logikaSudova.py");
            String java_string = jep.getValue("s").toString();
            System.out.println("ulaz:  " + binarni_string + "     izlaz: " + java_string);
        }
    }
    private static boolean[] napravi_niz(boolean[] test, String binarni_string) {
        char[] niz = binarni_string.toCharArray();
        int n = niz.length;
        for(int i = 0; i < n; i++){
            if(niz[i] == '0'){
                test[i] = false;
            }else{
                test[i] = true;
            }
        }
        return test;
    }

    private static String dodaj_nule(String binarni_broj, int n) {
        StringBuilder sb = new StringBuilder();
        n = n - binarni_broj.length();
        for(int i = 0; i < n; i++){
            sb.append('0');
        }
        sb.append(binarni_broj);
        return sb.toString();
    }

    private static String pretvori_u_binarni(int i) {
        return Integer.toBinaryString(i);
    }
}
