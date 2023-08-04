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

    @Autowired
    private BudgetRepository budgetRepository;

    public Budget createBudget(Budget budget){

        LocalDate toDay = LocalDate.now();
        LocalDate dateDebut = budget.getDateDebut();
        LocalDate dateFin ;
        int jourMaxDuMois = toDay.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();

        if ((dateDebut.getMonthValue() != toDay.getMonthValue()) || (dateDebut.getYear() != toDay.getYear()))
            throw new BadRequestException("Désolé veuillez choisir une date correct ");

        if (dateDebut.getDayOfMonth() <=0 || dateDebut.getDayOfMonth() > jourMaxDuMois)
            throw new BadRequestException("Désolé veuillez choisir une date dans le mois actuel ");

        Budget budgetVerif = budgetRepository.findByCategorie(budget.getCategorie());
        if (budgetVerif == null) {
            dateFin = dateDebut.plusDays(30);
            budget.setDateFin(dateFin);
            return budgetRepository.save(budget);
        }

        LocalDate dateFinVerif = budgetVerif.getDateFin();
        if(toDay.isAfter(dateFinVerif))
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
