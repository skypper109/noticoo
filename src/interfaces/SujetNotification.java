package interfaces;

import model.Employer;

import java.time.LocalDate;
import java.util.Date;

public interface SujetNotification {
    void abonner(String email);
    void desabonner(String email);
    void notifier(String message, LocalDate date);
    boolean estAbonner(String email);
}
