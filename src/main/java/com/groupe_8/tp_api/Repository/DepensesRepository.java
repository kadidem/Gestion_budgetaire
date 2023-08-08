package com.groupe_8.tp_api.Repository;

import com.groupe_8.tp_api.Model.Categorie;
import com.groupe_8.tp_api.Model.Depenses;
import com.groupe_8.tp_api.Model.Type;
import com.groupe_8.tp_api.Model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DepensesRepository extends JpaRepository<Depenses,Long> {
    Depenses findByUtilisateurAndCategorieAndTypeAndDate(Utilisateur utilisateur, Categorie categorie, Type type, LocalDate date);
    Depenses findFirstByUtilisateurAndCategorieAndTypeOrderByDateDesc(Utilisateur utilisateur, Categorie categorie, Type type);
}
