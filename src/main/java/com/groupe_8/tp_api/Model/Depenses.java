package com.groupe_8.tp_api.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
public class Depenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDepenses;
    @Lob
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private int montant;

    private Date date;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur utilisateur;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idCategorie")
    private  Categorie categorie;

}
