package com.groupe_8.tp_api.Service;

import com.groupe_8.tp_api.Model.Utilisateur;
import com.groupe_8.tp_api.Repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UtilisateurService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public UtilisateurService(UtilisateurRepository userRepository) {
        this.utilisateurRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Objects.requireNonNull(username);
        Utilisateur utilisateur = utilisateurRepository.findUserWithName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return utilisateur;
    }

    public Utilisateur createUtilisateur(Utilisateur utilisateur){return utilisateurRepository.save(utilisateur);}
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
}
