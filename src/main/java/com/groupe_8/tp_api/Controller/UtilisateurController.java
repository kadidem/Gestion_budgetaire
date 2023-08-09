package com.groupe_8.tp_api.Controller;

import com.groupe_8.tp_api.Model.Utilisateur;
import com.groupe_8.tp_api.Service.UtilisateurService;
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
    public ResponseEntity<Utilisateur> createUtilisateur(@Valid @RequestBody Utilisateur utilisateur){
         return new ResponseEntity<>(utilisateurService.createUtilisateur(utilisateur), HttpStatus.OK);
     }
     @GetMapping("/read")
    public ResponseEntity<List<Utilisateur>> getUtilisateur(){
        return new ResponseEntity<>(utilisateurService.getAllUtilisateur(),HttpStatus.OK);}
   @GetMapping("/read/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@Valid @PathVariable long id){
        return new ResponseEntity<>(utilisateurService.getUtilisateurById(id),HttpStatus.OK) ;}
   @PutMapping("/update")
    public ResponseEntity<Utilisateur>  editUtilisateur(@Valid @RequestBody Utilisateur utilisateur){
        return new ResponseEntity<>( utilisateurService.editutilisateur(utilisateur),HttpStatus.OK);}
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUtilisateurById(@Valid @RequestBody Utilisateur utilisateur){
        return new ResponseEntity<>(utilisateurService.deleteUtilisateurById(utilisateur),HttpStatus.OK);}
    @PostMapping("/login")
    public Object connexion(@RequestParam("email") String email,
                            @RequestParam("motDePasse") String motDePasse) {
        return utilisateurService.connectionUtilisateur(email, motDePasse);
    }
}

