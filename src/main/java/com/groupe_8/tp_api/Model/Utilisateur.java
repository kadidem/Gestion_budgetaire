package com.groupe_8.tp_api.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
<<<<<<< HEAD
    @OneToMany(mappedBy = "utilisateur",cascade = CascadeType.ALL)
    private List<Budget> budgets;
    @OneToMany(mappedBy = "utilisateur",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Depenses> depenses;
=======
    @ManyToOne(optional = false)
    private Categorie categories;

    public Categorie getCategories() {
        return categories;
    }

    public void setCategories(Categorie categories) {
        this.categories = categories;
    }
>>>>>>> 2888d0e5b0ddd8ef6b8f6abaac8a4884919142b0
}
