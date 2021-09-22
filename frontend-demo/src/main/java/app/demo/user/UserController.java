package app.demo.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping(path="userControl/")
public class UserController {

    private final UserService userService;

    @PostMapping("login")
    public boolean login(@RequestBody User user){

        //name not necessary
        System.out.println("login");
        System.out.println(user);

        if(user.getEmail().equals("a@a.com"))
            return true;
        return false;
    }

    @PostMapping("addUser")
    public boolean register(@RequestBody User user){
        //studentService.addNewStudent(student);
        return true;
    }
    @DeleteMapping(path= "{userId}")
    public void deleteStudent(@PathVariable("userId") Long id){
        //studentService.deleteStudent(id);
    }
    @PutMapping(path="{userId}")
    public void updateStudent(
            @PathVariable("userId")         Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email)
    {
        //studentService.updateStudent(studentId,name,email);
    }

    @GetMapping("getInfo")
    public String getInfo(){
        return "hello";
    }


    @Autowired
    public UserController(UserService  userService) {
        this.userService = userService;
    }

    @PostMapping("lexString")
    public String lexString(@RequestBody String expression){

        System.out.println(expression);

        return "success";

    }

}
