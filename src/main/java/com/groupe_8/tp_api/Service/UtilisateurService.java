package com.groupe_8.tp_api.Service;

import com.groupe_8.tp_api.Model.Utilisateur;
import com.groupe_8.tp_api.Repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;



    public Utilisateur createUtilisateur(Utilisateur utilisateur){return utilisateurRepository.save(utilisateur);}
   public List<Utilisateur> getAllUtilisateur(){return utilisateurRepository.findAll();}
    public Optional<Utilisateur> getUtilisateurById(Long id_utilisateur){return utilisateurRepository.findById(id_utilisateur);}
}
