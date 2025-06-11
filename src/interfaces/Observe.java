package interfaces;

import model.Employer;

public interface Observe {
    void envoieNotification(String message, Employer expediteur);

}
