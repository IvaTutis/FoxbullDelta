package application.model;

import javax.persistence.*;

@Entity
@Table(name = "constants")
public
class Constant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private float value;

    //konstruktori
    public Constant(){}

    public Constant(String new_name, float new_value){
        super();
        this.name=new_name;
        this.value=new_value;
    }

    //getters
    public long getId() { return this.id; }
    public String getName() { return this.name; }
    public float getValue() { return this.value; }

    //setters
    public void setId(long new_ID){ this.id = new_ID; }
    public void setName(String new_name){this.name=new_name;}
    public void setValue(float new_value){this.value=new_value;}

}
