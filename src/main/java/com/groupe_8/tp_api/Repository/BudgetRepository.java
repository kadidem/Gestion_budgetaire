package com.groupe_8.tp_api.Repository;

import com.groupe_8.tp_api.Model.Budget;
import com.groupe_8.tp_api.Model.Categorie;
import com.groupe_8.tp_api.Model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BudgetRepository extends JpaRepository<Budget,Long> {
    Budget findByCategorie(Categorie categorie);
    Budget findByIdBudget(long id);
    Budget findByUtilisateurAndCategorieAndDateFinIsAfter(Utilisateur utilisateur, Categorie categorie, LocalDate date);
    Budget findFirstByUtilisateurAndCategorieAndDateFinIsBeforeOrderByDateFinDesc(Utilisateur utilisateur,Categorie categorie,LocalDate localDate);
}
