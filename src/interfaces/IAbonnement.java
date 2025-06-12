package interfaces;

import model.Employer;

public interface IAbonnement {
    boolean abonner(Employer employer);
    boolean desabonner(Employer employer);
    void reabonner(Employer employer);
    boolean estAbonne(String email);
}
