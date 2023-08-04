package com.groupe_8.tp_api.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Depenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDepenses;
    private String description;
    private String montant;
    private Date date;


    @OneToMany(mappedBy = "depensesCategorie")
    private Collection<Categorie> categorie;

    public Collection<Categorie> getCategorie() {
        return categorie;
    }

    public void setCategorie(Collection<Categorie> categorie) {
        this.categorie = categorie;
    }
}
