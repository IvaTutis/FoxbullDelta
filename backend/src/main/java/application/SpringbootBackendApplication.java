package application;

//importing repositories

import application.model.Constant;
import application.model.Expression;
import application.model.User;
import application.repository.ConstantRepository;
import application.repository.ExpressionRepository;
import application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class SpringbootBackendApplication implements CommandLineRunner {

	//a timezone function for optional later use
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	//the main that makes the whole application run
	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ConstantRepository constantRepository;

	@Autowired
	private ExpressionRepository expressionRepository;

	//executes when app starts
	@Override
	public void run(String... args) throws Exception {
		//some default users
		this.userRepository.save(new User("Iva", "iva.tutis@gmail.com", "password"));

		//some default expressions
		this.expressionRepository.save(new Expression("2+2", 0,0,"4"));

		//some default constants
		this.constantRepository.save(new Constant("pi", (float)3.14));
	}
}