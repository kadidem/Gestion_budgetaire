package com.groupe_8.tp_api.Controller;

import com.groupe_8.tp_api.Model.Utilisateur;
import com.groupe_8.tp_api.Service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("utilisateur")
@AllArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    @PostMapping("/create")
    public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur){
         return utilisateurService.createUtilisateur(utilisateur);
     }
     @GetMapping("/read")
    public List<Utilisateur> getUtilisateur(){return utilisateurService.getAllUtilisateur();}
   @GetMapping("/read/{id}")
    public Optional<Utilisateur> getUtilisateurById(@PathVariable Long id){return utilisateurService.getUtilisateurById(id);}
   @PutMapping("/update/{id}")
    public Utilisateur editUtilisateur(@PathVariable Long id,@RequestBody Utilisateur utilisateur){return
            utilisateurService.editutilisateur(id,utilisateur);}
    @DeleteMapping("/delete/{id}")
    public String deleteUtilisateurById(@PathVariable Long id){return utilisateurService.deleteUtilisateurById(id);}
}
