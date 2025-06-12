package services;

import dbGestion.AbonnementDB;
import dbGestion.EmployeDB;
import interfaces.IAbonnement;
import model.Employer;

public class AbonnementService implements IAbonnement {



    private final AbonnementDB abonnementRequets;
    private final EmployeDB employeRequets;

    public AbonnementService(AbonnementDB abonnementRequets, EmailService emailService, EmployeDB emplyeRequets) {
        this.abonnementRequets = abonnementRequets;
        this.employeRequets = emplyeRequets;
    }

    @Override
    public boolean abonner(Employer employer) {
        Employer emp = employeRequets.getEmployerByEmail(employer.getEmail());
        if (emp == null) {
            System.out.println("❌ Employé introuvable !");
            return false;
        }

        boolean dejaAbonne = abonnementRequets.estAbonne(emp.getId());
        if (dejaAbonne) {
            System.out.println("⚠️ Cet employé est déjà abonné.");
            return false;
        }

        abonnementRequets.abonner(emp.getId());
        return true;
    }

    @Override
    public boolean desabonner(Employer employer) {
        Employer emp = employeRequets.getEmployerByEmail(employer.getEmail());
        if (emp == null) {
            System.out.println("❌ Employé introuvable !");
            return false;
        }

        if (!abonnementRequets.estAbonne(emp.getId())) {
            System.out.println("⚠️ Cet employé n'est pas abonné.");
            return false;
        }

        abonnementRequets.desabonner(emp.getId());
        return true;

    }

    @Override
    public void reabonner(Employer employer) {

    }

    @Override
    public boolean estAbonne(String email) {
        return false;
    }
}
