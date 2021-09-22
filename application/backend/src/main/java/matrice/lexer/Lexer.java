package matrice.lexer;

import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import matrice.tokeni.Token;
import matrice.tokeni.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private static Lexer lexer;
    private List<Token> tokeni;
    private char[] data;
    private int currentIndex;

    private Lexer() {
        tokeni = new ArrayList<>();
        currentIndex = 0;
    }

    public  static Lexer getInstanceLexer(){
        if(lexer == null){
            lexer = new Lexer();
        }
        return lexer;
    }

    public  void Tokeniziraj(String s){
        data = s.toCharArray();

        while(currentIndex < data.length){
            char current = data[currentIndex];

            if(current ==' '){
                currentIndex++;
            }
            else if(current == '{'){
                DoubleMatrix2D matrica = napraviMatricu();
                tokeni.add(new Token(TokenType.MATRICA, matrica));
            }else if(current == '+'){
                tokeni.add(new Token(TokenType.PLUS, null));
                currentIndex++;
            }else if(current == '-'){
                tokeni.add(new Token(TokenType.MINUS, null));
                currentIndex++;
            }else if(current == '*'){
                tokeni.add(new Token(TokenType.PUTA, null));
                currentIndex++;
            }else if(current == '('){
                tokeni.add(new Token(TokenType.OTVORENA, null));
                currentIndex++;
            }else if(current == ')'){
                tokeni.add(new Token(TokenType.ZATVORENA, null));
                currentIndex++;
            }else{
                throw new LexerException("Nedopusteni znak!");
            }
        }
        tokeni.add(new Token(TokenType.EOF, null));
    }

    private DoubleMatrix2D napraviMatricu(){
        int red = 1;
        boolean stupacFlag = false;
        int stupac = 0;
        List<Number> brojevi = new ArrayList<>();

        currentIndex++; //sad se nalazimo na znaku poslije {

        char current = data[currentIndex];
        while(currentIndex < data.length){

            current = data[currentIndex];
            if(current == ' '){
                currentIndex++;
                continue;
            }
            if(current == '['){
                currentIndex++;
                while(current != ']'){
                    current = data[currentIndex];
                    if(Character.isDigit(current)){
                        StringBuilder sb = new StringBuilder();
                        int tockaFlag = 0;
                        while(Character.isDigit(current) || current == '.'){
                            if(current == '.' && tockaFlag == 0){
                                //System.out.println("current:" + current);
                                tockaFlag++;
                            }else if (tockaFlag == 1 && current =='.'){
                                throw  new LexerException("U broju se nalazi previse tocaka");
                            }
                            sb.append(current);
                            currentIndex++;
                            current = data[currentIndex];
                        }
                        if(tockaFlag == 0){
                            brojevi.add(Integer.parseInt(sb.toString()));
                        }else{
                            brojevi.add(Double.parseDouble(sb.toString()));
                        }
                        if(!stupacFlag){
                            stupac++;
                        }
                    }else if( Character.isDigit(data[currentIndex - 1]) && current == ',' && currentIndex + 1 < data.length && Character.isDigit(data[currentIndex + 1])){

                        currentIndex++;
                        current = data[currentIndex];
                    }else{
                        throw  new LexerException("Nedopusten znak");
                    }
                }
                currentIndex++;
                stupacFlag = true;
                //red++;
            }else if(current == ',' && currentIndex + 1  < data.length && data[currentIndex +1] == '['){
                currentIndex++;
                //System.out.println("Ovdje");
                red++;
            }else if(current == '}'){
                currentIndex++;
                break;
            }else{
                throw new LexerException("Nedopusten znak");
            }

        }
        DoubleMatrix2D matrica = DoubleFactory2D.dense.make(red,  stupac);

        int k = 0;
        for(int i = 0; i < red; i++){
            for(int j = 0; j < stupac; j++){
                if(brojevi.get(k)  instanceof Integer){
                    matrica.set(i, j, (int)brojevi.get(k));
                }else if(brojevi.get(k)  instanceof Double){
                    matrica.set(i, j, (Double)brojevi.get(k));
                }
                k++;
            }
        }
        return matrica;
    }

    public List<Token> getTokeni(){
        return tokeni;
    }
}
