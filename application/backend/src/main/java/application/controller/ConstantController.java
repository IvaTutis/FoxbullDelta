package application.controller;

//spring framework stuff
import application.exception.ResourceNotFoundException;
import application.model.Constant;
import application.repository.ConstantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//model import
//repository import

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "constantControl/")
public class ConstantController {

    @Autowired
    private ConstantRepository constantRepository;

    //-------------------------------------adders & updaters-------------------

    @PostMapping(path = "add")
    public  ResponseEntity <Constant> addConstant(@RequestBody Constant  constantDetails){
        Constant temp = new Constant(constantDetails.getName(), constantDetails.getValue());

        //we save it to the repository
        Constant updatedConstant = constantRepository.save(temp);

        //return signal
        return ResponseEntity.ok(updatedConstant);
    }

    // update constant rest api
    @PutMapping(path="update/{id}")
    public ResponseEntity <Constant> updateConstant (@PathVariable Long id, @RequestBody Constant  constantDetails) {

        //try to find the constant to update
        Constant temp  = constantRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Constant not exist with id :" + id));

        //we set constant details
        temp.setName(constantDetails.getName());
        temp.setValue(constantDetails.getValue());

        //we save it to the repository
        Constant updated = constantRepository.save(temp);

        //return signal
        return ResponseEntity.ok(updated);
    }


    //----------------------------------------getters---------------------------

    //get all constants
    @GetMapping(path = "getAll")
    public List<Constant> getAllConstants() {
        return constantRepository.findAll();
    }

    // get constant by id rest api
    @GetMapping(path = "get/{id}")
    public ResponseEntity<Constant> getConstantById(@PathVariable Long id) {
        Constant constant = constantRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Constant not exist with id :" + id));
        return ResponseEntity.ok(constant);
    }

    //-----------------------------------------delete----------------------------------

    // delete constant est api
    @DeleteMapping(path="delete/{id}")
    public ResponseEntity <Map< String, Boolean >> deleteConstant(@PathVariable Long id) {

        //find the constant to delete
        Constant constant = constantRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Constant not exist with id :" + id));

        //actually delete the constant from the repo
        constantRepository.delete(constant);

        //return signal
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    // delete all
    @DeleteMapping(path="deleteAll")
    public void deleteAll(){
        constantRepository.deleteAll();
    }

}
