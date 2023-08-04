package com.groupe_8.tp_api.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idCategorie;

    @Column( nullable=false, unique=true)
    @NotNull(message = "Il faut de contenu pour ce champ")
    private String titre;

    @ManyToOne
    private Utilisateur utilisateur;

    @OneToMany(mappedBy="categorie")
    private List<Depenses> depenses;

    @OneToMany(mappedBy = "categorie")
    private List<Budget> budgets;
}
