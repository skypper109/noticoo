package services;

import dbGestion.Requets;

public class LoginService {
    private Requets requets = new Requets();
    public LoginService() {
    }

    public boolean verifierIdentifiants(String email, String motDePasse) {
        return requets.verifierLog(email,motDePasse);
    }
    public boolean estEmploye(String email){
        return requets.verifierEmp(email);
    }
}
