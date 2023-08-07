package com.groupe_8.tp_api.Service;

import com.groupe_8.tp_api.Exception.NoContentException;
import com.groupe_8.tp_api.Model.Budget;
import com.groupe_8.tp_api.Model.Depenses;
import com.groupe_8.tp_api.Repository.BudgetRepository;
import com.groupe_8.tp_api.Repository.DepensesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
public class DepensesService {
    private final DepensesRepository depensesRepository;
    private  final BudgetRepository budgetRepository;
    public Depenses creer(Depenses depenses){

        depenses.setDate(LocalDate.now());
        return depensesRepository.save(depenses);
    }
    public List<Depenses> lire(){
        List<Depenses> depensesList = depensesRepository.findAll();
        if (depensesList.isEmpty())
            throw new NoContentException("Aucune depenses trouvée");
        return depensesList;

    }
    public Depenses modifier(long id, Depenses depenses){
        return depensesRepository.findById(id)
                .map(qu ->{
                    qu.setDescription(depenses.getDescription());
                    qu.setMontant(depenses.getMontant());
                    qu.setDate(LocalDate.now());
                    return depensesRepository.save(qu);
                }).orElseThrow(() -> new RuntimeException(("Dépenses non trouvé")));
    }
    public String Supprimer(long id){
        depensesRepository.deleteById(id);
        return "Depenses Supprimer";
    }
}
