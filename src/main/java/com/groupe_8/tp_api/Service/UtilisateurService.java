package com.groupe_8.tp_api.Service;

import com.groupe_8.tp_api.Exception.NoContentException;
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
   public List<Utilisateur> getAllUtilisateur(){

        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        if (utilisateurs.isEmpty())
            throw new NoContentException("Aucun utilisateur trouvé");
        return utilisateurs;
    }
    public Utilisateur getUtilisateurById(long idUtilisateur){

        Utilisateur utilisateur= utilisateurRepository.findByIdUtilisateur(idUtilisateur);
        if(utilisateur ==null)
            throw new EntityNotFoundException("cet utilisateur n'existe pas");
        return utilisateur;
    }

    public Utilisateur editutilisateur(Utilisateur utilisateur){
        Utilisateur user= utilisateurRepository.findByIdUtilisateur(utilisateur.getIdUtilisateur());
         if (user == null)
             throw new EntityNotFoundException("cet utilisateur n'existe pas");
         return utilisateurRepository.save(utilisateur);

    }
    public String deleteUtilisateurById(Utilisateur utilisateur){
        Utilisateur user= utilisateurRepository.findByIdUtilisateur(utilisateur.getIdUtilisateur());
        if (user == null)
            throw new EntityNotFoundException("Désolé  l'utilisateur à supprimé n'existe pas");
        utilisateurRepository.delete(utilisateur);
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
