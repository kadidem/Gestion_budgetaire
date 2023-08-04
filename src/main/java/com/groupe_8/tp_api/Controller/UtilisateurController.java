package com.groupe_8.tp_api.Controller;

import com.groupe_8.tp_api.Model.Utilisateur;
import com.groupe_8.tp_api.Service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("utilisateur")
@AllArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
     public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur){
         return utilisateurService.createUtilisateur(utilisateur);
     }

}
