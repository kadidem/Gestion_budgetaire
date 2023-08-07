package com.groupe_8.tp_api.Controller;

import com.groupe_8.tp_api.Model.Budget;
import com.groupe_8.tp_api.Model.Categorie;
import com.groupe_8.tp_api.Model.Utilisateur;
import com.groupe_8.tp_api.Repository.BudgetRepository;
import com.groupe_8.tp_api.Service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/Budget")
public class BudgetController {

    @Autowired
    BudgetService budgetService;

    @Autowired
    BudgetRepository budgetRepository;

    @PostMapping("/ajouter")
    public ResponseEntity<Budget> ajouterBudget(@Valid @RequestBody Budget budget){
        return new ResponseEntity<>(budgetService.createBudget(budget), HttpStatus.OK);
    }

    @PutMapping("/modifier")
    public ResponseEntity<Budget> modifierBudget(@Valid @RequestBody Budget budget){
        return  new ResponseEntity<>(budgetService.updateBudget(budget), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Budget>> listeBudget(){
        return  new ResponseEntity<>(budgetService.allBudget(),HttpStatus.OK);
    }

    @GetMapping("{id}")
    public  ResponseEntity<Budget> budgetParId(@PathVariable long id){
        return new ResponseEntity<>(budgetService.getBudgetById(id),HttpStatus.OK);
    }

    @DeleteMapping("/supprimer")
    public ResponseEntity<String> supprimerBudget(@Valid @RequestBody Budget budget){
        return new ResponseEntity<>(budgetService.deleteBudget(budget),HttpStatus.OK);
    }

    @PostMapping("/test")
    public ResponseEntity<Budget> test(@RequestBody Budget budget){
        Utilisateur utilisateur = budget.getUtilisateur();
        Categorie categorie = budget.getCategorie();
        Budget budgetVerif = budgetRepository.findByUtilisateurAndCategorieAndDateFinIsAfter(utilisateur,categorie,LocalDate.now());
        return  new ResponseEntity<>(budgetVerif, HttpStatus.OK);
    }
}
