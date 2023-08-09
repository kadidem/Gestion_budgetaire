package com.groupe_8.tp_api.Repository;

import com.groupe_8.tp_api.Model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie,Long> {
    Categorie findByIdCategorie(long id);
}
