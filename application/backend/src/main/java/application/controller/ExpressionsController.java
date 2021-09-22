package application.controller;


import cern.colt.matrix.DoubleMatrix2D;
import jep.Jep;
import jep.JepException;
import matrice.lexer.Lexer;
import matrice.lexer.LexerException;
import matrice.parserinterpreter.PIException;
import matrice.parserinterpreter.PInterpreter;
import matrice.tokeni.Token;
import application.additional.BabyExpression;
import application.additional.WrapperValue;
import application.exception.ResourceNotFoundException;
import application.model.Expression;
import application.repository.ExpressionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import util's

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping( path = "/expressionControl")
public class ExpressionsController { //DONE. NE DIRATI

    @Autowired
    private ExpressionRepository expressionRepository;

    //-------------------------------------adders/solvers----------------------------

    //DONE
    @PostMapping(path = "arithmetic")
    public String addArithmetic(@RequestBody WrapperValue v) throws JepException {

        String input = v.getValue();

        System.out.println("Mateo mi dodaje aritmeticki izraz oblika ." + input + ".");

        int input_type=0;
        int number_of_variables=0;

        String output = "";

        try {
            //probamo rijesiti izraz

            Jep jep = new Jep(false, "/home/iva/java_project-main/lucija/aplikacija/springboot-helloworld-tutorial/src/interpreter_python_scripts");
            jep.set("ulaz", input);
            jep.runScript("src/interpreter_python_scripts/aritmetikaBrojeva.py");
            output = jep.getValue("s").toString();

            System.out.println(output);

        }catch(JepException e){ //ako nismo uspjeli
            System.out.println(e.getMessage());
            return output;
        }

        //sada ako dosad nisam returnala, pospremam rjesenje u bazu
        Expression tmp = new Expression(input, input_type, number_of_variables, output);
        expressionRepository.save(tmp);

        //vracam da je sve ok, mogla sam rijesiti
        return output;

    }

    @PostMapping(path = "logic")
    public ArrayList<String> addLogic(@RequestBody BabyExpression izraz){



        ArrayList<String> za_frontend = new ArrayList<String>();


        String input = izraz.getInput();
        int input_type=1;
        int number_of_variables= countVariables(input);
        String fixed_input=  input ;
        String output = "";

        System.out.println("Logicki izraz je ." + izraz.getInput() + ". sa ocitanim brojem varijabli ." + number_of_variables+ ".");


        int truth_table_number_of_rows = (int) Math.pow(2, number_of_variables);

        int l = number_of_variables+1;
        //ovo saljem frontendu radi lakseg prikaza
        String for_frontend;

        try { //probamo rijesiti izraz

            Jep jep = new Jep(false,"/home/iva/java_project-main/lucija/aplikacija/springboot-helloworld-tutorial/src/interpreter_python_scripts/");
            String binarni_string = "";

            // zapravo broj redaka u bazi 2 su ujedno sve kombinacije nula i jedinica
            // pa to iskoristavamo ovdje
            for(int i = 0; i < truth_table_number_of_rows; i++){

                //u stringu koji je binarni broj ce i-ti character biti vrijednost i-te varijable
                //za taj redak istinosne tablice
                String binarni_broj = pretvori_u_binarni(i);
                binarni_string = dodaj_nule(binarni_broj, number_of_variables);

                //imati cemo sigurno manje od 10 varijabli.
                //tako da onaj binarni broj koji predstavlja nase vrijednosti sada konvertiramo u true i false
                boolean[] test = new boolean[10];
                test = napravi_niz(test, binarni_string);


                jep.set("ulaz", input);
                jep.set("brojVarijabli", number_of_variables);
                jep.set("nizIstinitosti", test);
                jep.runScript("src/interpreter_python_scripts/logikaSudova.py");

                String java_string = jep.getValue("s").toString();
                String row_output = "ulaz:  " + binarni_string + "     izlaz: " + java_string;

                System.out.println("ulaz:  " + binarni_string + "     izlaz: " + java_string);
                za_frontend.add(row_output);

                output = output + row_output ;

            }

        }catch(JepException e){ //ako nismo uspjeli, samo vracam false i ne mijenjam nista na bazi

            return za_frontend;
        }

        //sada ako dosad nisam returnala, pospremam rjesenje u bazu
        Expression tmp = new Expression(fixed_input, input_type, number_of_variables, output);
        expressionRepository.save(tmp);

        //vracam da je sve ok, mogla sam rijesiti
        return za_frontend;

    }

    //DONE
    @PostMapping(path = "matrix")
    public String addMatrix(@RequestBody WrapperValue v){


        String input =v.getValue();


        System.out.println("Mateo mi dodaje matriu oblika ." + input+ ".");

        int input_type=2;
        int number_of_variables=0;

        String output = "";

        try {
            try { //probamo rijesiti izraz

                //lexiram do tokena
                Lexer lex = Lexer.getInstanceLexer();
                lex.Tokeniziraj(input);
                List<Token> tokeni = lex.getTokeni();

                //interpretiram tokene
                PInterpreter interpreter = PInterpreter.getInstance();
                DoubleMatrix2D matrica = interpreter.parsiraj(tokeni);

                output = matrica.toString();


            } catch (LexerException e){ // nismo uspjeli
                System.out.println(e.getMessage());
                return output;
            }
        }catch(PIException e){
            System.out.println(e.getMessage());
            return output;
        }



        //sada ako dosad nisam returnala, pospremam rjesenje u bazu
        Expression tmp = new Expression(input, input_type, number_of_variables, output);
        expressionRepository.save(tmp);

        //vracam da je sve ok, mogla sam rijesiti
        return output;

    }



    //--------------------------------update---------------------------------------

    // update expressions rest api
    @PutMapping("update")
    public ResponseEntity <Expression> updateExpression (@PathVariable Long id, @RequestBody Expression  expressionDetails) {

        Expression expression = new Expression();

        try {
            //try to find the user to update
             expression = expressionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Expression not exist with id :" + id));
        }catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //we set expression details
        expression.setInput(expressionDetails.getInput());
        expression.setOutput(expressionDetails.getOutput());
        expression.setInputType(expressionDetails.getInputType());
        expression.setNumberOfVariables(expressionDetails.getNumberOfVariables());

        //we save it to the repository
        Expression updatedExpression = expressionRepository.save(expression);

        //return signal
        return ResponseEntity.ok(updatedExpression);
    }

    //---------------------------------------getters---------------------------------------

    //get all expressions
    @GetMapping("get")
    public List<Expression> getAllExpressions() {
        return expressionRepository.findAll();
    }

    // get expression by id rest api
    @GetMapping("get/{id}")
    public ResponseEntity<Expression> getExpressionById(@PathVariable Long id) {


        Expression expression = new Expression();

        try {
            //try to find the user to update
            expression = expressionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Expression not exist with id :" + id));
        }catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(expression);
    }


    //-----------------------------------------delete----------------------------------

    // delete expression rest api //DONE
    @DeleteMapping("delete/{id}")
    public ResponseEntity <Map< String, Boolean >> deleteExpression(@PathVariable Long id) {

        Expression expression = new Expression();

        try {
            //try to find the user to update
            expression = expressionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Expression not exist with id :" + id));
        }catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        //actually delete the expression from the repo
        expressionRepository.delete(expression);

        //return signal
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);

    }


    // delete all //DONE
    @DeleteMapping(path="deleteAll")
    public void deleteAll(){
        expressionRepository.deleteAll();
    }

    //-----------------------------------dodatne funkcije za konverziju za interpretere-----------

    public int countVariables(String input){

        int count = 0;

        for (int i = 0; i < input.length(); i++) {
            if (Character.isLetter(input.charAt(i)))
                count++;
        }

        return count;
    }

    private static String pretvori_u_binarni(int i) {
        return Integer.toBinaryString(i);
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

    //-------------------------additional input class---------------------

    public class babyExpression{



    }

}
