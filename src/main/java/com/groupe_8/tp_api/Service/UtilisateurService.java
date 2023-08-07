package com.groupe_8.tp_api.Service;

import com.groupe_8.tp_api.Model.Utilisateur;
import com.groupe_8.tp_api.Repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService  {

    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }
    public Utilisateur createUtilisateur(Utilisateur utilisateur){
        if (utilisateurRepository.findByEmail(utilisateur.getEmail()) == null) {
            return utilisateurRepository.save(utilisateur);
        } else {
            throw new DuplicateException("Cet email existe déjà");}}
   public List<Utilisateur> getAllUtilisateur(){return utilisateurRepository.findAll();}
    public Optional<Utilisateur> getUtilisateurById(Long id_utilisateur){return utilisateurRepository.findById(id_utilisateur);}
    public Utilisateur editutilisateur(Long id_utilisateur,Utilisateur utilisateur){
        Utilisateur user= utilisateurRepository.findById(id_utilisateur)
                .map(u ->{
                    u.setNom(utilisateur.getNom());
                    u.setPrenom((utilisateur.getPrenom()));
                    u.setEmail((utilisateur.getEmail()));
                    u.setMotDePasse((utilisateur.getMotDePasse()));
                    return utilisateurRepository.save(u);
                }).orElseThrow(() -> new RuntimeException("l'utilisateur n'a pas été trouvé"));
        return user;
    }
    public String deleteUtilisateurById(Long id){
        utilisateurRepository.deleteById(id);
        return "Utilisateur Supprimé";
    }
    public Utilisateur connectionUtilisateur(String email, String motDePasse) {
        Utilisateur user = utilisateurRepository.findByEmailAndMotDePasse(email, motDePasse);
        if (user == null) {
          throw  new EntityNotFoundException("Cet utilisateur n'existe pas");
        }

        return user;

    }

}
