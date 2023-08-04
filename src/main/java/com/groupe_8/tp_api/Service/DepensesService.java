package com.groupe_8.tp_api.Service;

import com.groupe_8.tp_api.Model.Depenses;
import com.groupe_8.tp_api.Repository.DepensesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class DepensesService {
    private final DepensesRepository depensesRepository;
    public Depenses creer(Depenses depenses){
        return depensesRepository.save(depenses);
    }
    public List<Depenses> lire(){
        return depensesRepository.findAll();
    }
    public Depenses modifier(long id, Depenses depenses){
        return depensesRepository.findById(id)
                .map(qu ->{
                    qu.setDescription(depenses.getDescription());
                    qu.setMontant(depenses.getMontant());
                    qu.setDate(depenses.getDate());
                    return depensesRepository.save(qu);
                }).orElseThrow(() -> new RuntimeException(("Dépenses non trouvé")));
    }
    public String Supprimer(long id){
        depensesRepository.deleteById(id);
        return "Depenses Supprimer";
    }
}
