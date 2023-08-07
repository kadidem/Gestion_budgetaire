package com.groupe_8.tp_api.Service;

import com.groupe_8.tp_api.Exception.BadRequestException;
import com.groupe_8.tp_api.Exception.NoContentException;
import com.groupe_8.tp_api.Model.Budget;
import com.groupe_8.tp_api.Model.Categorie;
import com.groupe_8.tp_api.Model.Depenses;
import com.groupe_8.tp_api.Model.Utilisateur;
import com.groupe_8.tp_api.Repository.BudgetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class BudgetService {

    @Autowired // Injection de dependance
    private BudgetRepository budgetRepository; // Interface BudgetRepository

    @Autowired
    NotificationService notificationService;

    //Fonction pour la création d'un budget, elle prend un entrée un objet budget
    public Budget createBudget(Budget budget){

        Utilisateur utilisateur = budget.getUtilisateur(); // Capture de l'utilisateur du budget
        Categorie categorie = budget.getCategorie(); // Capture de catégorie du budget
        LocalDate toDay = LocalDate.now(); // Obtention de la date du jour en type LocalDate
        LocalDate dateDebut = budget.getDateDebut(); // Capture de la date de début du buget à inserer
        LocalDate dateFin ; // Déclaration du variable de type LocalDate qui vas nous servir de personnaliser la date de fin du budget à inserer
        int jourMaxDuMois = toDay.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth(); // Obtention du dernier jour du mois actuel

        /* Vérification si la date le mois et l'année du date de debut du budget à inserer est différent du mois et de l'année de la date actuelle,
         alors le système lèvera une exception */
        if ((dateDebut.getMonthValue() != toDay.getMonthValue()) || (dateDebut.getYear() != toDay.getYear()))
            throw new BadRequestException("Désolé veuillez choisir une date correct ");

        /* Vérification si le jour de la date du budget à inserer est eest inferieur ou égale à 0 ou si c'est suppérieur au jour de la date actuelle,
         alors le système lèvera une exception */
        if (dateDebut.getDayOfMonth() <=0 || dateDebut.getDayOfMonth() > jourMaxDuMois)
            throw new BadRequestException("Désolé veuillez choisir une date dans le mois actuel ");

        // Vérification s'il existe dans la base de donnée un budget en cours de même catégorie que le budget à inserer
        Budget budgetVerif = budgetRepository.findByUtilisateurAndCategorieAndDateFinIsAfter(utilisateur,categorie, LocalDate.now());

        /* Si le budgetVerif est différent de null, ce qui veut dire qu'il existe un budget en coursde même catégorie que le budget à inserer
        * , alors le système lèvera une exception */
        if (budgetVerif != null)
            throw new BadRequestException("Désolé vous avez déjà un budget en cours pour cette catégorie");

        /* Si budget budgetVerif est égale à null, alors cela veut dire qu'il n'existe pas de budget dans la base de donnée en cours avec la même catégorie
         que le budget à inserer, alors on insere notre budget */
        dateFin = dateDebut.plusDays(30); // On ajoute 30 jours à la date de debut du budget à inserer, et on l'affecte à la variable dateFin
        budget.setDateFin(dateFin); // On donne cette date à la date de fin du budget à inserer
        return budgetRepository.save(budget); // On sauvegarde cet budget dans notre base de donnée

        /*  Si le budgetVerif est différent de null, alors on capture la date fin du budgetVerif dans une variable dateFinVerif de type LocalDate
        LocalDate dateFinVerif = budgetVerif.getDateFin();

         Vérification si la date actuelle est superieure à dateFinVerif, ce qui veut dire que le budgetVerif n'est pas expirer pour le moment,
         le système lèvera une exception
        if(toDay.isBefore(dateFinVerif))
            throw new BadRequestException("Désolé vous avez déjà un budget en cours pour cette catégorie");

        /* Si budgetVerif est expirer, alors on ajoute 30 jours à la date de debbut du budget à inserer et on l'affecte à la variable dateFin,
         puis on donne cette variable à la date de fin du budget à inserer, et à la fin on sauvegarde notre budget dans notre base de données
        dateFin = dateDebut.plusDays(30);
        budget.setDateFin(dateFin);
        return budgetRepository.save(budget); */

    }

    // Fonction qui permet de modifier un budget, elle prend un entrée un objet budget
    public Budget updateBudget(Budget budget){

        // Vérification de l'existance du budget à modifier dans la base de donnée à travers son id
        Budget budgetVerif = budgetRepository.findByIdBudget(budget.getIdBudget());

        // Si budgetVerif est null, alors le budget n'a pas étét trouvé et le système lèvera une exception
        if (budgetVerif == null)
            throw new EntityNotFoundException("Désolé cet budget n'exis");

        /* Verification si la date de debut du budgetVerif est different de la date du budget à modifier, alors le système lèvera une exception */
        if(!budgetVerif.getDateDebut().equals(budget.getDateDebut()))
            throw new BadRequestException("Vous ne pouvez pas modifier la date de debut du budget");

        /* Dans le cas contraire on sauvegarde la modification du budget dans la base de donnée
        et retourne le budget modifié
         */
        return budgetRepository.save(budget);
    }

    //Fonction qui permet retourner la liste de tous les budget, elle ne prend rien en paramètre
    public List<Budget> allBudget(){

        // Obtention de tous les budget dans la base de données
        List<Budget> budgetList = budgetRepository.findAll();

        // Si la liste est vide, le système lèvera une exception
        if (budgetList.isEmpty())
            throw new NoContentException("Aucun budget trouvé");

        // Dans le cas contraire le système retourne la liste
        return budgetList;
    }

    // Obtention d'un budget à travers son id
    public Budget getBudgetById(long id){

        // Obtention d'un budget dans la base de donnée à travers son id
        Budget budget = budgetRepository.findByIdBudget(id);

        // Si budget est null, alors le budget n'a pas étét trouvé et le système lèvera une exception
        if (budget == null)
            throw  new EntityNotFoundException("Aucun budget trouvé");

        // Dans le cas contraire le système enregistre et retourne le budget enregistré
        return budget;
    }

    // Fonction qui permet de supprimer un budget
    public String deleteBudget(Budget budget){

        // Vérification de l'existance du budget à modifier dans la base de donnée à travers son id
        Budget budgetVerif = budgetRepository.findByIdBudget(budget.getIdBudget());

        // Si budgetVerif est null, alors le budget n'a pas étét trouvé et le système lèvera une exception
        if (budgetVerif == null)
            throw  new EntityNotFoundException("Aucun budget trouvé");

        // Dans le cas contraire le système supprime le budget puis retourne un message succes
        budgetRepository.delete(budget);
        return "succes";
    }

    public void updateMontantRestant(Depenses depenses){
        Utilisateur utilisateur = depenses.getUtilisateur(); // Recuperer l'utilisateur de la dépense
        Categorie categorie = depenses.getCategorie(); // Recuperer categorie de la depense
        int montantDepense = depenses.getMontant(); // Recupere montant de la depense

        // Recuperer le budget en cours de la même catégorie de depense
        Budget budget = budgetRepository.findByUtilisateurAndCategorieAndDateFinIsAfter(utilisateur,categorie,LocalDate.now());

        int montantRestantBudget = budget.getMontantRestant();

        if (montantDepense > montantRestantBudget)
            throw new BadRequestException("Desolé le montant de votre depense depasse le montant restant de votre budget");

        int montantRestantBudgetActuel = montantRestantBudget - montantDepense;
        budget.setMontantRestant(montantRestantBudgetActuel);
        if (montantRestantBudgetActuel <= budget.getMontantAlerte())
            notificationService.sendNotification(budget);

        budgetRepository.save(budget);


    }
}
