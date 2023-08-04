package com.groupe_8.tp_api.Service;

import com.groupe_8.tp_api.Model.Categorie;
import com.groupe_8.tp_api.Repository.CategorieRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategorieService {
    @Autowired
    private CategorieRepository categorieRepository;

    public Categorie creer(Categorie categorie){
        return categorieRepository.save(categorie);
    }
    public List<Categorie> lire(){
        return categorieRepository.findAll();
    }
    public Categorie modifier(Categorie categorie) {
        Categorie categorieVerif = categorieRepository.findById(categorie.getIdCategorie()).orElse(null);
        if (categorieVerif != null) {
            return categorieRepository.save(categorie);
        }
        return null;
    }
    public void supprimer(Long idCategorie) {
        Optional<Categorie> categorie = categorieRepository.findById(idCategorie);
        if (categorie != null) {
            categorieRepository.deleteById(idCategorie);
        }
    }
}
