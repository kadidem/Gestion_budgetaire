package com.groupe_8.tp_api.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_utilisateur;
   @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String motDePasse;
    @OneToMany(mappedBy = "utilisateur",cascade = CascadeType.ALL)
    private List<Budget> budgets;
    @OneToMany(mappedBy = "utilisateur",cascade = CascadeType.ALL)
    private List<Depenses> depenses;
}
