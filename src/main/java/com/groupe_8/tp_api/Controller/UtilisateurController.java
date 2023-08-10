package com.groupe_8.tp_api.Controller;

import com.groupe_8.tp_api.Model.Utilisateur;
import com.groupe_8.tp_api.Service.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("utilisateur")
@AllArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    @PostMapping("/create")
    @Operation(summary = "Création  d'un utilisateur")
    public ResponseEntity<Utilisateur> createUtilisateur(@Valid @RequestBody Utilisateur utilisateur){
         return new ResponseEntity<>(utilisateurService.createUtilisateur(utilisateur), HttpStatus.OK);
     }
     @GetMapping("/read")
     @Operation(summary = "Affichage de la  liste des utilisateurs")
    public ResponseEntity<List<Utilisateur>> getUtilisateur(){
        return new ResponseEntity<>(utilisateurService.getAllUtilisateur(),HttpStatus.OK);}
   @GetMapping("/read/{id}")
   @Operation(summary = "Affichage  d'un utilisateur")
    public ResponseEntity<Utilisateur> getUtilisateurById(@Valid @PathVariable long id){
        return new ResponseEntity<>(utilisateurService.getUtilisateurById(id),HttpStatus.OK) ;}
   @PutMapping("/update")
   @Operation(summary = "Modification d'un utilisateur")
    public ResponseEntity<Utilisateur>  editUtilisateur(@Valid @RequestBody Utilisateur utilisateur){
        return new ResponseEntity<>( utilisateurService.editutilisateur(utilisateur),HttpStatus.OK);}
    @DeleteMapping("/delete")
    @Operation(summary = "Suppression d'un utilisateur")
    public ResponseEntity<String> deleteUtilisateurById(@Valid @RequestBody Utilisateur utilisateur){
        return new ResponseEntity<>(utilisateurService.deleteUtilisateurById(utilisateur),HttpStatus.OK);}
    @PostMapping("/login")
    @Operation(summary = "Connexion d'un utilisateur")
    public Object connexion(@RequestParam("email") String email,
                            @RequestParam("motDePasse") String motDePasse) {
        return utilisateurService.connectionUtilisateur(email, motDePasse);
    }
}

