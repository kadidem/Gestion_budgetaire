package com.groupe_8.tp_api.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Categorie")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idCategorie;

    @Column( nullable=false, unique=true)
    @NotNull(message = "Il faut de contenu pour ce champ")
    private String titre;
}
