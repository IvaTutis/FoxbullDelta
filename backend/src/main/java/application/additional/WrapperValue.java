package application.additional;

public class WrapperValue {

    private String value;

    public WrapperValue(){}
    public WrapperValue(String newValue){this.value=newValue;}

    //get
    public String getValue(){return this.value;}
    //set
    public void setValue(String new_value){this.value=new_value;}

}
