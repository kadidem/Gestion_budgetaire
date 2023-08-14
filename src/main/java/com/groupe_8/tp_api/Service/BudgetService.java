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
    @Autowired
    private TransfertService transfertService;
    //Fonction pour la création d'un budget, elle prend un entrée un objet budget
    public Budget createBudget(Budget budget){

        Utilisateur utilisateur = budget.getUtilisateur(); // Capture de l'utilisateur du budget
        Categorie categorie = budget.getCategorie(); // Capture de catégorie du budget
        LocalDate toDay = LocalDate.now(); // Obtention de la date du jour en type LocalDate
        LocalDate dateDebut = budget.getDateDebut(); // Capture de la date de début du buget à inserer
        //LocalDate dateFin ; // Déclaration du variable de type LocalDate qui vas nous servir de personnaliser la date de fin du budget à inserer
        LocalDate jourMaxDuMois = dateDebut.with(TemporalAdjusters.lastDayOfMonth()); // Obtention du dernier date du mois actuel
        budget.setDateFin(jourMaxDuMois);
        //=============================================================================================================

        if (dateDebut.getMonthValue() < toDay.getMonthValue() || (dateDebut.getYear() != toDay.getYear()))
            throw new BadRequestException("Veuillez choisie une date dans "+toDay.getMonth()+" "+toDay.getYear());

        Budget verifBudget = budgetRepository.findByUtilisateurAndCategorieAndDateFin(utilisateur,categorie,budget.getDateFin());
        if (verifBudget != null )
            throw new BadRequestException("Désolé vous avez déjà un budget de catégorie "+categorie.getTitre()+" pour ce mois");

        if(budget.getMontantAlerte() >= budget.getMontant())
            throw  new BadRequestException(("Desolé votre montant d'alerte ne peut pas depassé le montant de votre budget"));

        Budget lastBudget = budgetRepository.findFirstByUtilisateurAndCategorieOrderByDateFinDesc(utilisateur,categorie);
        if (lastBudget != null){
            if (budget.getDateFin().isBefore(lastBudget.getDateDebut()))
                throw new BadRequestException("Désole vous avez déjà programmé un budget supérieur à ce mois");
            budget.setParent(lastBudget);
            if (lastBudget.getDateFin().isBefore(toDay)) {
                if (lastBudget.getMontantRestant() > 0) {
                    budget.setMontant(budget.getMontant() + lastBudget.getMontantRestant());
                    budget.setMontantRestant(budget.getMontant());
                    transfertService.creer(budget,lastBudget);
                }
            }
        }

        //=============================================================================================================
        /* Vérification si la date le mois et l'année du date de debut du budget à inserer est différent du mois et de l'année de la date actuelle,
         alors le système lèvera une exception */
        /*if ((dateDebut.getMonthValue() != toDay.getMonthValue()) || (dateDebut.getYear() != toDay.getYear()))
            throw new BadRequestException("Désolé veuillez choisir une date correct ");*/

        //if(budget.getMontantAlerte() >= budget.getMontant())
            //throw  new BadRequestException(("Desolé votre montant d'alerte ne peut pas depassé le montant de votre budget"));

        /* Vérification si le jour de la date du budget à inserer est eest inferieur ou égale à 0 ou si c'est suppérieur au jour de la date actuelle,
         alors le système lèvera une exception
        if (dateDebut.getDayOfMonth() <=0 || dateDebut.getDayOfMonth() > jourMaxDuMois)
            throw new BadRequestException("Désolé veuillez choisir une date dans le mois actuel "); */

        // Vérification s'il existe dans la base de donnée un budget en cours de même catégorie que le budget à inserer
        //Budget budgetVerif = budgetRepository.findByUtilisateurAndCategorieAndDateFinIsAfter(utilisateur,categorie, LocalDate.now());

        /* Si le budgetVerif est différent de null, ce qui veut dire qu'il existe un budget en cours de même catégorie que le budget à inserer
        * , alors le système lèvera une exception */
        //if (budgetVerif != null)
            //throw new BadRequestException("Désolé vous avez déjà un budget en cours pour cette catégorie");

        /* Si budget budgetVerif est égale à null, alors cela veut dire qu'il n'existe pas de budget dans la base de donnée en cours avec la même catégorie
         que le budget à inserer, alors on insere notre budget */
        //dateFin = dateDebut.plusDays(30); // On ajoute 30 jours à la date de debut du budget à inserer, et on l'affecte à la variable dateFin
        //budget.setDateFin(dateFin); // On donne cette date à la date de fin du budget à inserer
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
            throw new EntityNotFoundException("Désolé cet budget n'existe pas");

        LocalDate toDay = LocalDate.now(); // Obtention de la date du jour en type LocalDate
        LocalDate dateDebut = budget.getDateDebut(); // Capture de la date de début du buget à inserer
        LocalDate dateFin = dateDebut.with(TemporalAdjusters.lastDayOfMonth()) ; // Déclaration du variable de type LocalDate qui vas nous servir de personnaliser la date de fin du budget à inserer
        budget.setDateFin(dateFin);

        /* Verification si la date de debut du budgetVerif est different de la date du budget à modifier, alors le système lèvera une exception */
        if(!budgetVerif.getDateDebut().equals(budget.getDateDebut())){
            /* Verification si le budget à modifier possède déjà une depanse */
            if (!budgetVerif.getDepenses().isEmpty())
                throw new BadRequestException("Désolé vous ne pouvez plus modifier la date de cet budget car possède déjà au moins une depense");

            if (budgetVerif.getDateFin().isBefore(toDay))
                throw new BadRequestException("Désolé vous ne pouvez pas modifier un budget déjà expirer");

            if (dateDebut.getMonthValue() < budgetVerif.getDateDebut().getMonthValue() || (dateDebut.getYear() != toDay.getYear()))
                throw new BadRequestException("Veuillez choisie une date dans "+toDay.getMonth()+" "+toDay.getYear());

            //Budget budgetPrecedant = budgetRepository.findFirstByUtilisateurAndCategorieAndDateFinIsBeforeOrderByDateFinDesc(budgetVerif.getUtilisateur(),
                    //budgetVerif.getCategorie(), budgetVerif.getDateDebut());
            Budget budgetPrecedant = budgetVerif.getParent();

            if (budgetPrecedant != null){
                if(budget.getDateDebut().isBefore(budgetPrecedant.getDateFin()) || budget.getDateDebut().equals(budgetPrecedant.getDateFin()))
                    throw new BadRequestException("Désolé votre ne doit pas commencé à une date inféieure à la da te de fin du budget précédant qui est "+budgetPrecedant.getDateFin());
            }

            //Budget budgetSuivant = budgetRepository.findFirstByUtilisateurAndCategorieAndDateDebutIsAfterOrderByDateFinDesc(budgetVerif.getUtilisateur(),
                    //budgetVerif.getCategorie(),budgetVerif.getDateFin());
            Budget budgetSuivant = budgetVerif.getEnfant();

            if (budgetSuivant != null){
                if(budget.getDateDebut().isAfter(budgetSuivant.getDateDebut()) || budget.getDateDebut().equals(budgetSuivant.getDateDebut()))
                    throw new BadRequestException("Désolé votre ne doit pas commencé à une date superieure à la da te de debut du budget suivant qui est "+budgetSuivant.getDateDebut());
            }

            //dateFin = dateDebut.plusDays(30); // On ajoute 30 jours à la date de debut du budget à inserer, et on l'affecte à la variable dateFin
            //budget.setDateFin(dateFin); // On donne cette date à la date de fin du budget à inserer
        }

        if (budget.getMontant() < (budgetVerif.getMontant() - budgetVerif.getMontantRestant()))
            throw new BadRequestException("Desolé vous ne pouvez pas modifier le montant de votre budget ent deçue de "+(budgetVerif.getMontant() - budgetVerif.getMontantRestant()));

        if (budget.getMontant() > (budgetVerif.getMontant() - budgetVerif.getMontantRestant())){
            budget.setMontantRestant(budget.getMontant() - (budgetVerif.getMontant() - budgetVerif.getMontantRestant()));
        }

        if(budget.getMontantAlerte() >= budget.getMontant())
            throw  new BadRequestException(("Desolé votre montant d'alerte ne peut pas depassé le montant de votre budget"));

        //dateFin = dateDebut.plusDays(30); // On ajoute 30 jours à la date de debut du budget à inserer, et on l'affecte à la variable dateFin
        //budget.setDateFin(dateFin);

        if (budget.getMontantRestant() <= budget.getMontantAlerte())
            notificationService.sendNotification(budget);

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
    public String deleteBudget(long idBudget){

        // Vérification de l'existance du budget à modifier dans la base de donnée à travers son id
        Budget budgetVerif = budgetRepository.findByIdBudget(idBudget);

        // Si budgetVerif est null, alors le budget n'a pas étét trouvé et le système lèvera une exception
        if (budgetVerif == null)
            throw  new EntityNotFoundException("Aucun budget trouvé");

        if (budgetVerif.getDateFin().isBefore(LocalDate.now()))
            throw new BadRequestException("Désolé vous ne pouvez pas modifier un budget déjà expirer");

        if (!budgetVerif.getDepenses().isEmpty())
            throw new BadRequestException("Désolé vous ne pouvez plus supprimer cet budget car possède déjà au moins une depense");

        if(budgetVerif.getEnfant() != null)
            throw new BadRequestException("Désolé vous ne pouvez pas supprimer ce budget car il y'a un budget après luis");

        // Dans le cas contraire le système supprime le budget puis retourne un message succes
        budgetRepository.deleteById(idBudget);
        return "succes";
    }

    public void updateMontantRestant(Depenses depenses, Object... depensesMotif){
        //int defaultMontant = depenses.getMontant();
        int montantDepense = 0;

        // Recuperer le budget en cours de la même catégorie de depense
        Budget budget = budgetRepository.findByIdBudget(depenses.getBudget().getIdBudget());
        if (budget == null)
            throw  new EntityNotFoundException("Vous n'avez aucun budget pour ce categorie de depenses");
        if (budget.getDateFin().isBefore(LocalDate.now()))
            throw new BadRequestException("ce budjet n'est plus valide");
        if (budget.getDateDebut().isAfter(LocalDate.now()))
            throw new BadRequestException("ce budjet n'est pas encore commencé");

        if(depensesMotif.length != 0 ){
            if (depensesMotif[0] instanceof Depenses){
                Depenses depensesVerif = (Depenses) depensesMotif[0];
                if (depenses.getMontant() != depensesVerif.getMontant()){
                    montantDepense = depenses.getMontant()-depensesVerif.getMontant();
                }
            } else {
                String motif = (String) depensesMotif[0];
                if (!motif.equals("sup"))
                    throw  new BadRequestException("Motif incorrect");
                montantDepense = -depenses.getMontant();
            }
        } else
            montantDepense = depenses.getMontant(); // Recupere montant de la depense

        int montantRestantBudget = budget.getMontantRestant();

        if (montantDepense > montantRestantBudget)
            throw new BadRequestException("Desolé le montant de votre depense depasse le montant restant de votre budget");

        int montantRestantBudgetActuel = montantRestantBudget - montantDepense;
        budget.setMontantRestant(montantRestantBudgetActuel);
        if (montantRestantBudgetActuel <= budget.getMontantAlerte())
            notificationService.sendNotification(budget);

        budgetRepository.save(budget);


    }

    public void transfertBudget(Budget budget, Budget lastBudget){
        budget.setMontant(budget.getMontant() + lastBudget.getMontantRestant());
        budget.setMontantRestant(budget.getMontantRestant() + lastBudget.getMontantRestant());
        transfertService.creer(budget,lastBudget);
        budgetRepository.save(budget);
    }

}
