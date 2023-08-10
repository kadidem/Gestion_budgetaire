package com.groupe_8.tp_api.Controller;

import com.groupe_8.tp_api.Model.Depenses;
import com.groupe_8.tp_api.Service.DepensesService;
<<<<<<< HEAD
import jakarta.validation.Valid;
=======
import io.swagger.v3.oas.annotations.Operation;
>>>>>>> 8bd145898740d020a5424ab97adc2e35bdfb653d
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Depenses")
@AllArgsConstructor
public class DepensesController {
    @Autowired
    private DepensesService depensesService;
   @PostMapping("/create")
<<<<<<< HEAD
    public Depenses create(@Valid @RequestBody Depenses depenses){
        return depensesService.creer(depenses);
    }
    @PutMapping("/update")
    public  Depenses update(@Valid @RequestBody Depenses depenses){
=======
   @Operation(summary = "création de dépense")
    public Depenses create(@RequestBody Depenses depenses){
        return depensesService.creer(depenses);
    }
    @PutMapping("/update")
    @Operation(summary = "Modification d'un dépense")
    public  Depenses update(@RequestBody Depenses depenses){
>>>>>>> 8bd145898740d020a5424ab97adc2e35bdfb653d
      return depensesService.modifier(depenses);
    }
  @GetMapping("/read")
  @Operation(summary = "affichage d'un dépense")
    public List<Depenses> read(){
       return depensesService.lire();
    }
    @GetMapping("/read/{id}")
    public Depenses readById(@Valid @PathVariable long id){
       return depensesService.lireById(id);
    }
  @DeleteMapping("/delete")
<<<<<<< HEAD
   public  String delete(@Valid @RequestBody Depenses depenses){
=======
  @Operation(summary = "Suppression d'un dépense")
   public  String delete(@RequestBody Depenses depenses){
>>>>>>> 8bd145898740d020a5424ab97adc2e35bdfb653d
       return depensesService.Supprimer(depenses);
    }
}
