package com.groupe_8.tp_api.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDate;
import java.util.Collection;
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

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToOne
    @JsonIgnoreProperties("depenses")
    private  Budget budget;

    @ManyToOne
    private  Type type;
}
