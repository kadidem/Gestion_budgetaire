package com.groupe_8.tp_api.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private long idType;

    @Column( nullable=false, unique=true)
    @NotNull(message = "Il faut de contenu pour ce champ")
    private String titre;

    @ManyToOne
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "typeDepenses")
    private List<Depenses> depenses;
}
