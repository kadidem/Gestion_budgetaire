package com.groupe_8.tp_api.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBudget;

    @NotNull(message = "Désole le montant ne doit pas etre null")
    @Min(value = 1000, message = "Désole le montant doit etre superieure ou égale a 1000")
    @Column(nullable = false)
    private int montant;

    @NotNull(message = "Désole le montant ne doit pas etre null")
    @Column(nullable = false)
    private int montantRestant;

    @NotNull(message = "Désole le montant ne doit pas etre null")
    @Column(nullable = false)
    private int montantAlerte;

    @NotNull(message = "Désole la date ne doit pas etre null")
    @Column(nullable = false)
    private LocalDate dateDebut;

    @NotNull(message = "Désole la date ne doit pas etre null")
    @Column(nullable = false)
    private LocalDate dateFin;

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToOne
    private Categorie categorie;

}
