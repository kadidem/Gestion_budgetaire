package com.groupe_8.tp_api.Repository;

import com.groupe_8.tp_api.Model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    @Query(" select u from Utilisateur u " +
            " where u.username = ?1")
    Optional<Utilisateur> findUserWithName(String username);

}
