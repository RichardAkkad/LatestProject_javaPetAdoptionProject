package com.postgresql.ytdemo2.Controller;

import com.postgresql.ytdemo2.Service.PetService;
import com.postgresql.ytdemo2.model.Dog;
import com.postgresql.ytdemo2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.postgresql.ytdemo2.repo.PetRepo;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
@Controller

public class PetController {

    @Autowired
    PetService petservice;


   @GetMapping("/homePage")
   public String homePage(){
       return "homePage";
   }


    @GetMapping("/addPet")//gets activated first
    public String addPetForm(Model model) {
        return petservice.prepareAddPetForm(model);// have to put "return here because we are returning in the method of the service
    }

    @PostMapping("/addPet")//when we press the save button then this gets activated and the binding of this new dog object with the actual inputs happens
    public String savePetForm(@ModelAttribute("request") Dog dog) {

        //System.out.println(dog.breed);

        return petservice.prepareSavePetForm(dog);

    }


    @GetMapping("/searchDog")
    public String SearchPage(){

        return "dogSearch";
    }



    @GetMapping({"/searchPetViaId"})
    public String ResultFromId(@RequestParam("id") Long id, Model model) {

        return petservice.prepareResultFromId(id,model);

    }


    @GetMapping("/filterResults")
    public String filterResults(@RequestParam int minAge,
                                @RequestParam int maxAge,
                                @RequestParam  String breed,
                                @RequestParam  String colour,
                                @RequestParam  String sex,
                                Model model){

        return petservice.prepareFilterResults(minAge,maxAge,breed,colour,sex,model);
        //we could print something to the console here if we want from the details retreived from the database or even create
        //another template and use the details we got back from the database
    }

    @GetMapping("deleteById")
    public String deleteById(){
        return "deleteById";
    }


    @GetMapping("/deletePetUsingId")
    public String deletePet(@RequestParam long id) {
        return petservice.prepareDelete(id);


    }


    @GetMapping("/updateById")
    public String updatePetById(Model model){
       return petservice.prepareUpdateByIdForm(model); // have to put "return here because we are returning in the method of the service class

    }

    @PostMapping("/updateById")
    public String updatePetById(@RequestParam Long id,@ModelAttribute("request2") Dog dog){
        // only need @RequestParam "id" passed back from the web browser once we press submit
         return petservice.prepareUpdateById(dog,id);


        }


}
