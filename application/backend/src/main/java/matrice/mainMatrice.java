package matrice;

import cern.colt.matrix.DoubleMatrix2D;
import matrice.lexer.Lexer;
import matrice.parserinterpreter.PInterpreter;
import matrice.tokeni.Token;

import java.util.List;

public class mainMatrice {

    public static void main(String[] args) {
        Lexer lex = Lexer.getInstanceLexer();
        lex.Tokeniziraj("{[2,2],[1,1]}+{[1,3],[3,2]}");
        List<Token> tokeni = lex.getTokeni();

        for(Token t : tokeni){
            System.out.println(t);
        }
        System.out.println();
        System.out.println();
        PInterpreter interpreter = PInterpreter.getInstance();
        DoubleMatrix2D matrica = interpreter.parsiraj(tokeni);

        System.out.println(matrica);
    }
}
