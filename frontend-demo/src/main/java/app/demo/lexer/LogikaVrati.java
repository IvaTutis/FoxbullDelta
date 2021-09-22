package app.demo.lexer;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Table(name="logikaVrati")
public class LogikaVrati {

    @Column(name= "brojVar")
    private int brojVar;

    @Column(name= "tablica")
    private boolean[][]  tablica;

    public LogikaVrati() {
    }

    public LogikaVrati(int brojVar, boolean[][] tablica) {
        this.brojVar = brojVar;
        this.tablica = tablica;
    }

    public int getBrojVar() {
        return brojVar;
    }

    public void setBrojVar(int brojVar) {
        this.brojVar = brojVar;
    }

    public boolean[][] getTablica() {
        return tablica;
    }

    public void setTablica(boolean[][] tablica) {
        this.tablica = tablica;
    }
}
