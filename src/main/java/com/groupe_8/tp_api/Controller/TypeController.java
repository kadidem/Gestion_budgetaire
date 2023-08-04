package com.groupe_8.tp_api.Controller;

import com.groupe_8.tp_api.Model.Categorie;
import com.groupe_8.tp_api.Model.Type;
import com.groupe_8.tp_api.Service.TypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("Type")
public class TypeController {
    private final TypeService typeService;

    @GetMapping("creer")
    public String creer(Type type){
        typeService.creer(type);
        return "La categorie"+ type +"a été créée";
    }
    @GetMapping("lire")
    public List<Type> lire() {
        typeService.lire();
        return null;
    }
    @PutMapping("modifier")
    public String modifier(@RequestBody Type type){
        typeService.modifier(type);
        return "Modification reussie";
    }
    @DeleteMapping("Supprimer")
    public String supprimer(@RequestParam("id") long idType){
        typeService.supprimer(idType);
        return "Vous avez supprimé la categorie à l'id "+idType;
    }

}
