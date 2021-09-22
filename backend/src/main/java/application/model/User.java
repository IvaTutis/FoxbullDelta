package application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String Name;

    @Column(name = "email")
    private String Email;

    @Column(name = "password")
    private String Password;

    //default constructor
    public User() {

    }

    //a constructor
    public User(String new_name, String new_email, String new_pass) {
        super();
        this.Name = new_name;
        this.Email = new_email;
        this.Password = new_pass;
    }

    //setters
    public void setId(long id) {
        this.id = id;
    }
    public void setName(String new_name) {
        this.Name = new_name;
    }
    public void setEmail(String new_email) {
        this.Email = new_email;
    }
    public void setPassword(String new_password) {
        this.Password = new_password;
    }

    //getters
    public long getId() {
        return id;
    }
    public String getName() {
        return this.Name;
    }
    public String getEmail() {
        return this.Email;
    }
    public String getPassword() { return this.Password; }

    //to string
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + Name + '\'' +
                ", email='" + Email + '\'' +
                ", password=" + Password +
                '}';
    }
}