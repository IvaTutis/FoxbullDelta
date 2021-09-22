package application.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "expressions")
public class Expression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "input")
    private String input;

    @Column(name = "input_type")
    private int input_type; //=0 for arithmetic types, =1 for logic types i =2 for matrix types

    @Column(name = "number_of_variables")
    private int number_of_variables; //=0 unless it's a logical expression with >0 variables

    @Column(name = "output")
    private String output;

    //constructors
    public Expression(){
    }

    public Expression(String new_input, int new_input_type , String new_output){
        super();
        this.input=new_input;
        this.output=new_output;
        this.input_type=new_input_type;
        this.number_of_variables=0;
    }

    public Expression(String new_input, int new_input_type ,int new_number_of_variables, String new_output){
        super();
        this.input=new_input;
        this.output=new_output;
        this.input_type=new_input_type;
        this.number_of_variables=new_number_of_variables;
    }

    //getters
    public long getId() { return this.id; }
    public String getOutput() { return this.output; }
    public int getNumberOfVariables(){return this.number_of_variables;}
    public int getInputType(){return this.input_type;}
    public String getInput() { return this.input; }

    //setters
    public void setId(long new_ID){ this.id = new_ID; }
    public void setOutput(String new_output) { this.output = new_output; }
    public void setInput(String new_input) { this.input = new_input; }
    public void setNumberOfVariables(int new_number_of_variables) { this.number_of_variables=new_number_of_variables;}
    public void setInputType(int new_input_type){this.input_type = new_input_type;}
}
