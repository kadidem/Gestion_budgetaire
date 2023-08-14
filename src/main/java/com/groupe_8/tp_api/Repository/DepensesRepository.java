package com.groupe_8.tp_api.Repository;

import com.groupe_8.tp_api.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DepensesRepository extends JpaRepository<Depenses,Long> {
    Depenses findByUtilisateurAndBudgetAndTypeAndDescriptionAndDate(Utilisateur utilisateur, Budget budget, Type type, String desc, LocalDate date);
    Depenses findFirstByUtilisateurAndBudgetAndTypeAndDescriptionOrderByDateDesc(Utilisateur utilisateur, Budget budget, Type type,String desc);
    Depenses findByIdDepenses(long id);
    Depenses findFirstByBudget(Budget budget);
}
