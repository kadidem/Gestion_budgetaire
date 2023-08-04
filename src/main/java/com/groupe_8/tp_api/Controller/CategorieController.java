package com.groupe_8.tp_api.Controller;

import com.groupe_8.tp_api.Model.Categorie;
import com.groupe_8.tp_api.Service.CategorieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("Categorie")
public class CategorieController {
    private final CategorieService categorieService;
    @GetMapping("creer")
      public String creer(Categorie categorie){
         categorieService.creer(categorie);
         return "La categorie"+ categorie +"a été créée";
    }
    @GetMapping("lire")
      public List<Categorie> lire() {
        categorieService.lire();
        return null;
}
    @PutMapping("modifier")
      public String modifier(@RequestBody Categorie categorie){
        categorieService.modifier(categorie);
        return "Modification reussie";
      }
    @DeleteMapping("Supprimer")
      public String supprimer(@RequestParam("id") long idCategorie){
       categorieService.supprimer(idCategorie);
       return "Vous avez supprimé la categorie à l'id "+idCategorie;
      }
}
