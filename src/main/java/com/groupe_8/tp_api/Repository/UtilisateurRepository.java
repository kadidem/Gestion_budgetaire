package com.groupe_8.tp_api.Repository;

import com.groupe_8.tp_api.Model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
}
