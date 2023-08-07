package com.groupe_8.tp_api.Controller;

import com.groupe_8.tp_api.Model.Type;
import com.groupe_8.tp_api.Service.TypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Type")
public class TypeController {
    private final TypeService typeService;

    @PostMapping("/creer")
    public ResponseEntity<String> creer(@RequestBody Type type, BindingResult bindingResult) {
        // Valider l'objet Type en utilisant les annotations de validation
        if (bindingResult.hasErrors()) {
            // Des erreurs de validation ont été trouvées, renvoyer un code 400 avec les erreurs
            return ResponseEntity.badRequest().body("Erreur de validation : " + bindingResult.getAllErrors());
        }

        // Si tout est valide, tenter de créer l'objet Type
        try {
            typeService.creer(type);
            // L'objet Type a été créé avec succès, renvoyer un code 201 avec un message de succès
            return ResponseEntity.status(HttpStatus.CREATED).body("Le type " + type.getTitre() + " a été créée");
        } catch (Exception e) {
            // Une exception s'est produite lors de la création de l'objet Type, renvoyer un code 500 avec le message d'erreur
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la création de la catégorie.");
        }
    }
    @GetMapping("/lire")
    public List<Type> lire() {
        typeService.lire();
        return typeService.lire();
    }
    @PutMapping("/modifier/{idType}")
    public ResponseEntity<String> modifier(@PathVariable Long idType, @RequestBody Type type) {
        Type typeAModifier = typeService.modifier(idType, type);
        if (typeAModifier != null) {
            return ResponseEntity.ok("Le Type avec l'ID " + idType + " a été modifié.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/Supprimer")
    public String supprimer(@RequestParam("id") long idType){
        typeService.supprimer(idType);
        return "Vous avez supprimé le type à l'id "+idType;
    }

}