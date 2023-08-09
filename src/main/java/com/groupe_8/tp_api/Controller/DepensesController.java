package com.groupe_8.tp_api.Controller;

import com.groupe_8.tp_api.Model.Depenses;
import com.groupe_8.tp_api.Service.DepensesService;
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
    public Depenses create(@RequestBody Depenses depenses){
        return depensesService.creer(depenses);
    }
    @PutMapping("/update")
    public  Depenses update(@RequestBody Depenses depenses){
      return depensesService.modifier(depenses);
    }
  @GetMapping("/read")
    public List<Depenses> read(){
       return depensesService.lire();
    }
  @DeleteMapping("/delete")
   public  String delete(@RequestBody Depenses depenses){
       return depensesService.Supprimer(depenses);
    }
}
