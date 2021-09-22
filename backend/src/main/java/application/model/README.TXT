Ovo je folder model. U njemu se nalaze springboot modeli,
koji se direktno odražavaju u klasama.

U trenutnoj uporabi su:

1.
@Table(name = "expressions")
Model Expression
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

2.
@Table(name = "users")
model User

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String Name;

    @Column(name = "email")
    private String Email;

    @Column(name = "password")
    private String Password;


Za verziju 2.0 // mateo i lucija za app.depo u rujnu

3.
@Table(name = "constants")
model Constant

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private float value;