package com.groupe_8.tp_api.Controller;

import com.groupe_8.tp_api.Model.Categorie;
import com.groupe_8.tp_api.Service.CategorieService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/Categorie")
public class CategorieController {
    private final CategorieService categorieService;
    @GetMapping("/creer")
      public String creer(@RequestBody Categorie categorie){
         categorieService.creer(categorie);
         return "La categorie "+ categorie.getTitre() +" a été créée";
    }
    @GetMapping("/lire")
    public List<Categorie> lire(){
        return categorieService.lire();
    }

    @PutMapping("/modifier")
    public ResponseEntity<Object> modifier(@RequestBody Categorie categorie)

    {
        Categorie categorieModifiee = categorieService.modifier(categorie);
        if (categorieModifiee != null) {
            return ResponseEntity.ok(categorieModifiee);
        } else {
            return ResponseEntity.notFound().build();
        }
       // return categorieModifiee+" a été remplacée par l'ancienne";
    }
    @DeleteMapping("/Supprimer")
      public String supprimer(@RequestParam("id") long idCategorie){
       categorieService.supprimer(idCategorie);
       return "Vous avez supprimé la categorie à l'id "+idCategorie;
      }
}
