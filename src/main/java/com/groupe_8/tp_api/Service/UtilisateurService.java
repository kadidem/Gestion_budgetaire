package com.groupe_8.tp_api.Service;

import com.groupe_8.tp_api.Model.Utilisateur;
import com.groupe_8.tp_api.Repository.UtilisateurRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService  {

    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public UtilisateurService(UtilisateurRepository userRepository) {
        this.utilisateurRepository = userRepository;
    }
  /*  @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Objects.requireNonNull(username);
        Utilisateur utilisateur = utilisateurRepository.findUserWithName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return utilisateur;
        Utilisateur utilisateur =utilisateurRepository.findUserWithName(username);
        if(utilisateur ==null) {
            throw new UsernameNotFoundException("L'utilisateur ne correspond pas au username" + utilisateur);
        }
        return new Utilisateur();
        }
*/
    public Utilisateur createUtilisateur(Utilisateur utilisateur){
        if (utilisateurRepository.findByEmail(utilisateur.getEmail()) == null) {
            return utilisateurRepository.save(utilisateur);
        } else {
            throw new EntityExistsException("Cet email existe déjà");}}
   public List<Utilisateur> getAllUtilisateur(){return utilisateurRepository.findAll();}
    public Optional<Utilisateur> getUtilisateurById(Long idUtilisateur){return utilisateurRepository.findById(idUtilisateur);}
    public Utilisateur editutilisateur(Long idUtilisateur,Utilisateur utilisateur){
        Utilisateur user= utilisateurRepository.findById(idUtilisateur)
                .map(u ->{
                    u.setNom(utilisateur.getNom());
                    u.setPrenom((utilisateur.getPrenom()));
                    u.setEmail((utilisateur.getEmail()));
                    u.setMotDePasse((utilisateur.getMotDePasse()));
                    return utilisateurRepository.save(u);
                }).orElseThrow(() -> new EntityNotFoundException("Désolé l'utilisateur à modifier n'existe pas"));

        return user;
    }
    public String deleteUtilisateurById(Long idUtilisateur){
        if (idUtilisateur == null)
            throw new EntityNotFoundException("Désolé  l'utilisateur à supprimé n'existe pas");
        utilisateurRepository.deleteById(idUtilisateur);
        return "Utilisateur Supprimé";
    }
    public Utilisateur connectionUtilisateur(String email, String motDePasse) {
        Utilisateur user = utilisateurRepository.findByEmailAndMotDePasse(email, motDePasse);
        if (user == null) {
            throw new EntityNotFoundException("Cet utilisateur n'existe pas");
        }

        return user;
    }
}
