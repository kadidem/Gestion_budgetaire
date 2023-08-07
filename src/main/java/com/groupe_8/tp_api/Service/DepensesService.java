package com.groupe_8.tp_api.Service;

import com.groupe_8.tp_api.Exception.BadRequestException;
import com.groupe_8.tp_api.Exception.NoContentException;
import com.groupe_8.tp_api.Model.*;
import com.groupe_8.tp_api.Repository.BudgetRepository;
import com.groupe_8.tp_api.Repository.DepensesRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
public class DepensesService {
    @Autowired
    private  DepensesRepository depensesRepository;
    @Autowired
    private   BudgetRepository budgetRepository;
    @Autowired
    private  BudgetService budgetService;
    public Depenses creer(Depenses depenses){
        LocalDate dateDepenses = depenses.getDate();
        Categorie categorie = depenses.getCategorie();
        Utilisateur user = depenses.getUtilisateur();
        Type type = depenses.getType();
        Depenses depensesVerif = null;
        Budget budget = budgetRepository.findByUtilisateurAndCategorieAndDateFinIsAfter(user,categorie,LocalDate.now());
        if(dateDepenses.isBefore(budget.getDateDebut()) || dateDepenses.isAfter(LocalDate.now()))
            throw new BadRequestException("Entrez une date correcte");

        switch (type.getTitre()){
            case "quotidient" :
                depensesVerif = depensesRepository.findByUtilisateurAndTypeAndDate(user,type,dateDepenses);
                if (depensesVerif != null)
                    throw  new BadRequestException("Desole vous avez deja effectué votre depenses journilière de " +categorie.getTitre());

                budgetService.updateMontantRestant(depenses);
                break;
            case  "hebdomadaire" :
                depensesVerif = depensesRepository.findFirstByUtilisateurAndCategorieAndTypeOrderByDateDesc(user,
                        categorie,type);
                if (depensesVerif.getDate().plusDays(7).isAfter(LocalDate.now()))
                    throw  new BadRequestException("Desole vous avez deja effectué votre depenses hebdomadaire de " +categorie.getTitre());

                budgetService.updateMontantRestant(depenses);
                break;
            case "mensuelle" :
                depensesVerif = depensesRepository.findFirstByUtilisateurAndCategorieAndTypeOrderByDateDesc(user,
                        categorie,type);
                if (depensesVerif.getDate().plusDays(30).isAfter(LocalDate.now()))
                    throw  new BadRequestException("Desole vous avez deja effectué votre depenses mensuelle de " +categorie.getTitre());

                budgetService.updateMontantRestant(depenses);
                break;
            default:
                throw  new BadRequestException("Ce type de depense n'existe pas");

        }
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
