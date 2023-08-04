package com.groupe_8.tp_api.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Depenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDepenses;
    private String description;
    @NotNull(message = "Désole le montant ne doit pas etre null")
    private String montant;
    @NotNull(message = "Choisissez la date")
    private Date date;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur utilisateur;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idCategorie")
    private  Categorie categorie;

}
