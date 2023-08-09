package com.groupe_8.tp_api.Repository;

import com.groupe_8.tp_api.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DepensesRepository extends JpaRepository<Depenses,Long> {
    Depenses findByUtilisateurAndBudgetAndTypeAndDate(Utilisateur utilisateur, Budget budget, Type type, LocalDate date);
    Depenses findFirstByUtilisateurAndBudgetAndTypeOrderByDateDesc(Utilisateur utilisateur, Budget budget, Type type);
    Depenses findByIdDepenses(long id);
}
