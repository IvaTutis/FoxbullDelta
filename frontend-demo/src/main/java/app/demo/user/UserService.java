package app.demo.user;

import org.springframework.stereotype.Service;

import java.util.List;


@Service //same as @Component
public class UserService {


    public List<User> getStudents(){
        return List.of(
                new User(
                        1L,
                        "Mariam",
                        "mar@gmail.com",
                        "1234"
                ),
                new User(
                        2L,
                        "Mike",
                        "m@m.com",
                        "1234"
                )
        );
    }

    public void addNewStudent(User user) {
        System.out.println("Adding new student over POST:");
        System.out.println(user);

    }

    public void deleteStudent(Long id) {
        System.out.println("Deliting student with id: " + id);

    }

    public void updateStudent(Long studentId, String name, String email) {
        System.out.println("Updating student with id: " + studentId);
    }
}
