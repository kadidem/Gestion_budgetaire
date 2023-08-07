package com.groupe_8.tp_api.Service;

import com.groupe_8.tp_api.Exception.BadRequestException;
import com.groupe_8.tp_api.Exception.NoContentException;
import com.groupe_8.tp_api.Model.Budget;
import com.groupe_8.tp_api.Repository.BudgetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class BudgetService {

    @Autowired // Injection de dependance
    private BudgetRepository budgetRepository; // Interface BudgetRepository

    //Fonction pour la création d'un budget
    public Budget createBudget(Budget budget){

        LocalDate toDay = LocalDate.now(); // Obtention de la date du jour en type LocalDate
        LocalDate dateDebut = budget.getDateDebut(); // Capture de la date de début du buget à inserer
        LocalDate dateFin ; // Déclaration du variable de type LocalDate qui vas nous servir de personnaliser la date de fin du budget à inserer
        int jourMaxDuMois = toDay.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth(); // Obtention du dernier jour du mois actuel

        /* Vérification si la date le mois et l'année du date de debut du budget à inserer est différent de du mois et de l'année de la date actuelle,
         alors le système lèvera une exception */
        if ((dateDebut.getMonthValue() != toDay.getMonthValue()) || (dateDebut.getYear() != toDay.getYear()))
            throw new BadRequestException("Désolé veuillez choisir une date correct ");

        /* Vérification si le jour de la date du budget à inserer est eest inferieur ou égale à 0 ou si c'est suppérieur au jour de la date actuelle,
         alors le système lèvera une exception */
        if (dateDebut.getDayOfMonth() <=0 || dateDebut.getDayOfMonth() > jourMaxDuMois)
            throw new BadRequestException("Désolé veuillez choisir une date dans le mois actuel ");

        // Vérification si un autre budget existe dans la base de donnée avec la  même catégorie que le budget à inserer
        Budget budgetVerif = budgetRepository.findByCategorie(budget.getCategorie());

        /* Si budget budgetVerif est égale à null, alors cela veut dire qu'il n'existe pas de budget dans la base de donnée avec la même catégorie
         que le budget à inserer, alors on insere notre budget */
        if (budgetVerif == null) {
            dateFin = dateDebut.plusDays(30); // On ajoute 30 jours à la date de debut du budget à inserer, et on l'affecte à la variable dateFin
            budget.setDateFin(dateFin); // On donne cette date à la date de fin du budget à inserer
            return budgetRepository.save(budget); // On sauvegarde cet budget dans notre base de donnée
        }

        // Si le budgetVerif est différent de null, alors on capture la date fin du budgetVerif dans une variable dateFinVerif de type LocalDate
        LocalDate dateFinVerif = budgetVerif.getDateFin();

        /* Vérification si la date actuelle est superieure à dateFinVerif, ce qui veut dire que le budgetVerif n'est pas expirer pour le moment,
         le système lèvera une exception */
        if(toDay.isBefore(dateFinVerif))
            throw new BadRequestException("Désolé vous avez déjà un budget en cours pour cette catégorie");


        dateFin = dateDebut.plusDays(30);
        budget.setDateFin(dateFin);
        return budgetRepository.save(budget);

    }

    public Budget updateBudget(Budget budget){

        Budget budgetVerif = budgetRepository.findByIdBudget(budget.getIdBudget());
        if (budgetVerif == null)
            throw new EntityNotFoundException("Désolé cet budget n'exis");

        return budgetRepository.save(budget);
    }

    public List<Budget> allBudget(){
        List<Budget> budgetList = budgetRepository.findAll();
        if (budgetList.isEmpty())
            throw new NoContentException("Aucun budget trouvé");

        return budgetList;
    }

    public Budget getBudgetById(long id){
        Budget budget = budgetRepository.findByIdBudget(id);
        if (budget == null)
            throw  new EntityNotFoundException("Aucun budget trouvé");

        return budget;
    }

    public String deleteBudget(Budget budget){
        Budget budgetVerif = budgetRepository.findByIdBudget(budget.getIdBudget());
        if (budgetVerif == null)
            throw  new EntityNotFoundException("Aucun budget trouvé");

        budgetRepository.delete(budget);
        return "succes";
    }
}
