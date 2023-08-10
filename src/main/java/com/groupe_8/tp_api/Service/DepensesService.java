package com.groupe_8.tp_api.Service;

import com.groupe_8.tp_api.Exception.BadRequestException;
import com.groupe_8.tp_api.Exception.NoContentException;
import com.groupe_8.tp_api.Model.*;
import com.groupe_8.tp_api.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class DepensesService {

    @Autowired
    private  DepensesRepository depensesRepository;
    @Autowired
    private  BudgetRepository budgetRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private  BudgetService budgetService;

    public Depenses creer(Depenses depenses){
        LocalDate dateDepenses = depenses.getDate();
        Budget budget = budgetRepository.findByIdBudget(depenses.getBudget().getIdBudget());
        if (budget == null)
            throw  new EntityNotFoundException("Vous n'avez aucun budget pour ce categorie de depenses");
        if (budget.getDateFin().isBefore(LocalDate.now()))
            throw new BadRequestException("ce budget n'est plus valide");

        Categorie categorie = budget.getCategorie();
        Utilisateur user = utilisateurRepository.findByIdUtilisateur(depenses.getUtilisateur().getIdUtilisateur());
        Type type = typeRepository.findByIdType(depenses.getType().getIdType());
        Depenses depensesVerif = null;
        if(categorie == null)
            throw new BadRequestException("Desolé cette catégorie n'existe pas");

        if (user == null)
            throw new BadRequestException("User invalid");

        if(dateDepenses.isBefore(budget.getDateDebut()) || dateDepenses.isAfter(LocalDate.now()))
            throw new BadRequestException("Entrez une date correcte");

        switch (type.getTitre()){
            case "quotidient" :
                depensesVerif = depensesRepository.findByUtilisateurAndBudgetAndTypeAndDate(user,budget,type,dateDepenses);
                if (depensesVerif != null)
                    throw  new BadRequestException("Desole vous avez deja effectué votre depenses journalière de " +categorie.getTitre());

                budgetService.updateMontantRestant(depenses);
                break;
            case  "hebdomadaire" :
                depensesVerif = depensesRepository.findFirstByUtilisateurAndBudgetAndTypeOrderByDateDesc(user,
                        budget,type);
                if (depensesVerif != null){
                    if (depensesVerif.getDate().plusDays(7).isAfter(LocalDate.now()))
                        throw  new BadRequestException("Desole vous avez deja effectué votre depenses hebdomadaire de " +categorie.getTitre());

                }
                budgetService.updateMontantRestant(depenses);
                break;
            case "mensuelle" :
                depensesVerif = depensesRepository.findFirstByUtilisateurAndBudgetAndTypeOrderByDateDesc(user,
                        budget,type);
                if (depensesVerif != null){
                    if (depensesVerif.getDate().plusDays(30).isAfter(LocalDate.now()))
                        throw  new BadRequestException("Desole vous avez deja effectué votre depenses mensuelle de " +categorie.getTitre());
                }
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
    public Depenses modifier(Depenses depenses){
         Depenses depensesVerif = depensesRepository.findByIdDepenses(depenses.getIdDepenses());
         if (depensesVerif == null)
             throw  new EntityNotFoundException("cette depenses n'existe pas");
         if (!depensesVerif.getDate().equals(depenses.getDate()))
             throw new EntityNotFoundException("Vous ne pouvez pas changer la date lors de la modification");
        if (depenses.getMontant() != depensesVerif.getMontant())
            budgetService.updateMontantRestant(depenses,depensesVerif);

        return depensesRepository.save(depenses);
    }
    public String Supprimer(Depenses depenses){
        Depenses depensesVerif = depensesRepository.findByIdDepenses(depenses.getIdDepenses());
        if (depensesVerif == null)
            throw  new EntityNotFoundException("cette depenses n'existe pas");
        budgetService.updateMontantRestant(depenses,"sup");

        depensesRepository.delete(depenses);

        return "Depenses Supprimer";
    }
}
