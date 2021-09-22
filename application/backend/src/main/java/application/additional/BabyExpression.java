package application.additional;

import javax.persistence.Column;

public class BabyExpression {

    @Column(name = "input")
    private String input;

    @Column(name = "number_of_variables")
    private int number_of_variables; //=0 unless it's a logical expression with >0 variables

    //constructors
    public BabyExpression(){
    }

    public BabyExpression(String new_input){
        this.input=new_input;
        this.number_of_variables=0;
    }

    public BabyExpression(String new_input,int new_number_of_variables){
        this.input=new_input;
        this.number_of_variables=new_number_of_variables;
    }

    //getters
    public int getNumberOfVariables(){return this.number_of_variables;}
    public String getInput() { return this.input; }

    //setters
    public void setInput(String new_input) { this.input = new_input; }
    public void setNumberOfVariables(int new_number_of_variables) { this.number_of_variables=new_number_of_variables;}
}
