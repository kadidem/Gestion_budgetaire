package com.groupe_8.tp_api.Service;

import com.groupe_8.tp_api.Exception.BadRequestException;
import com.groupe_8.tp_api.Exception.NoContentException;
import com.groupe_8.tp_api.Model.Budget;
import com.groupe_8.tp_api.Model.Categorie;
import com.groupe_8.tp_api.Model.Notification;
import com.groupe_8.tp_api.Model.Utilisateur;
import com.groupe_8.tp_api.Repository.NotificationRepository;
import com.groupe_8.tp_api.Repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String sender;

    /*public String sendNotification(Notification notification){

        Utilisateur utilisateur = notification.getUtilisateur();
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        try {
            mailMessage.setFrom(sender);
            mailMessage.setTo(utilisateur.getEmail());
            mailMessage.setText(notification.getTexte());
            mailMessage.setSubject("Alerte Budgetaire");

            javaMailSender.send(mailMessage);

            notification.setDate(LocalDate.now());

            return "succes";
        }catch (Exception e){
            return e.getMessage();
        }
    }*/

    public void sendNotification(Budget budget){
        Notification notification = new Notification();
        Utilisateur utilisateur = budget.getUtilisateur();
        Categorie categorie = budget.getCategorie();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String msg;

        if (budget.getMontantAlerte() == 0){
            msg = "Bonjour, Mr/Mme "+utilisateur.getNom()+"\nvotre Budget de "+categorie.getTitre()+" du " +
                    budget.getDateDebut()+" au "+budget.getDateFin()+" à atteint son seuil qui est "+budget.getMontantAlerte();
        }else {
            msg = "Bonjour, Mr/Mme "+utilisateur.getNom()+"\nvotre Budget de "+categorie.getTitre()+" du " +
                    budget.getDateDebut()+" au "+budget.getDateFin()+" à atteint/depasseé son montant d'alerte qui est "+
                    budget.getMontantAlerte()+", votre montant restant du budget est "+budget.getMontantRestant();
        }

        notification.setTexte(msg);

        try {
            mailMessage.setFrom(sender);
            mailMessage.setTo(utilisateur.getEmail());
            mailMessage.setText(notification.getTexte());
            mailMessage.setSubject("Alerte Budgetaire");

            javaMailSender.send(mailMessage);

            notification.setUtilisateur(utilisateur);
            notification.setBudget(budget);
            notification.setDate(LocalDate.now());

            notificationRepository.save(notification);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    /*public List<Notification> allNotificationByUtilisateur(long idUser){
        List<Notification> notifications = notificationRepository.findByUtilisateur_Id_utilisateur(idUser);

        if (notifications.isEmpty())
            throw new NoContentException("Aucune notification trouvée");

        return notifications;
    }*/

    public String deleteNotification(Notification notification){
        Notification notificationVerif = notificationRepository.findByIdNotification(notification.getIdNotification());

        if(notificationVerif == null)
            throw new EntityNotFoundException("Notification non trouvée");

        notificationRepository.delete(notification);

        return "succes";
    }
}
