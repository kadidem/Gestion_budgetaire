package com.groupe_8.tp_api.Controller;

import com.groupe_8.tp_api.Model.Depenses;
import com.groupe_8.tp_api.Service.DepensesService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Depenses")
@AllArgsConstructor
public class DepensesController {
    @Autowired
    private DepensesService depensesService;
   @PostMapping("/create")
    public Depenses create(Depenses depenses){
        return depensesService.creer(depenses);
    }
    @PostMapping("/update")
    public  Depenses update(@PathVariable long id,@RequestBody Depenses depenses){
      return depensesService.modifier(id, depenses);
    }
    public  String delete(long id){
       return depensesService.Supprimer(id);
    }
}
