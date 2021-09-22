package matrice.parserinterpreter;

import cern.colt.function.DoubleDoubleFunction;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import matrice.tokeni.Token;
import matrice.tokeni.TokenType;

import java.util.List;

public class PInterpreter {

    private static PInterpreter instance;
    private List<Token> tokeni;
    private int currentIndex;

    DoubleDoubleFunction plus = new DoubleDoubleFunction() {
        public double apply(double a, double b) { return a+b; }
    };

    DoubleDoubleFunction minus = new DoubleDoubleFunction() {
        public double apply(double a, double b) { return a-b; }
    };

    private PInterpreter(){
        currentIndex = 0;
    }

    public static PInterpreter getInstance(){
        if(instance == null){
            instance = new PInterpreter();
        }
        return instance;
    }

    /*
    *   izraz -> član | izraz PLUS član | izraz MINUS član
        član -> član PUTA faktor | faktor
        faktor -> MATRICA | OTVORENA izraz ZATVORENA
    * */
    public DoubleMatrix2D parsiraj(List<Token> lista){
        tokeni = lista;
        DoubleMatrix2D result = izraz();
        return result;
    }
    public DoubleMatrix2D izraz(){
        DoubleMatrix2D trenutni = clan();
        while(true){
            if(tokeni.get(currentIndex).getType().equals(TokenType.PLUS)){
                currentIndex++;
                trenutni = Zbroj(trenutni, clan());
            }else if(tokeni.get(currentIndex).getType().equals(TokenType.MINUS)){
                currentIndex++;
                trenutni = Minus(trenutni, clan());
            }else{
                break;
            }
        }
        return trenutni;
    }

    public DoubleMatrix2D clan(){
        DoubleMatrix2D trenutni = faktor();
        while(true){
            if(tokeni.get(currentIndex).getType().equals(TokenType.PUTA)){
                currentIndex++;
                trenutni = Umnozak(trenutni, faktor());
            }else if(tokeni.get(currentIndex).getType().equals(TokenType.OTVORENA)){
                trenutni = Umnozak(trenutni, faktor());
            }else{
                return trenutni;
            }
        }
    }

    public DoubleMatrix2D faktor(){
        if(tokeni.get(currentIndex).getType().equals(TokenType.MATRICA)){
            DoubleMatrix2D result = (DoubleMatrix2D) tokeni.get(currentIndex).getValue();
            currentIndex++;
            return result;
        }else if(tokeni.get(currentIndex).getType().equals(TokenType.OTVORENA)){
            currentIndex++;
            DoubleMatrix2D result = izraz();
            currentIndex++;
            if(!tokeni.get(currentIndex).getType().equals(TokenType.ZATVORENA)){
                throw new PIException();
            }
            return result;
        }else{
            throw new PIException();
        }
    }


    private DoubleMatrix2D Umnozak(DoubleMatrix2D trenutni, DoubleMatrix2D faktor) {
        return Algebra.DEFAULT.mult(trenutni, faktor);
    }

    private DoubleMatrix2D Minus(DoubleMatrix2D trenutni, DoubleMatrix2D clan) {
        return trenutni.assign(clan, minus);
    }

    private DoubleMatrix2D Zbroj(DoubleMatrix2D trenutni, DoubleMatrix2D clan) {
        return trenutni.assign(clan, plus);
    }

}
