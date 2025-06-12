# 📢 NotiCoo – Application de Notification pour Employés

NotiCoo est une application Java console conçue pour gérer l’envoi de notifications à des employés abonnés à un service. Ce projet utilise une architecture propre respectant les principes **SOLID** et s’appuie sur **SQLite** comme base de données locale.

---

## 🔧 Fonctionnalités

- 👤 **Authentification sécurisée** (admin / employé)
- ✅ **3 tentatives maximum** de connexion
- 📬 **Envoi de notifications** aux abonnés
- 📜 **Historique des notifications** par utilisateur
- 📩 **Envoi automatique d’e-mails**
- 👥 **Ajout / listing des employés**
- 🔔 **Abonnement / Désabonnement / Réabonnement**
- 🧱 Structure modulaire (DBgestion, Services, View)

---

## 🧱 Architecture du Projet

notifcool/
├── dbGestion/ # Connexion et requêtes SQLite
│ ├── Connexion.java
│ └── Requets.java
├── model/ # Objets métiers
│ ├── Employer.java
│ ├── Abonnement.java
│ └── Notification.java
├── repository/ # Accès aux données (SRP + OCP)
│ └── ...
├── services/ # Logique métier (SRP)
│ └── ...
├── view/ # Interfaces utilisateurs (console)
│ ├── Login.java
│ └── menus...
└── README.md


---

## 🛠️ Technologies utilisées

- **Java 21+**
- **SQLite** (base embarquée)
- **JDBC** (connexion SQL)
- **JakartaMailler** (envoie de mail)
- **Architecture SOLID**
- **Design Pattern : Repository(DBgestion), Service Layer**

---

## 🚀 Lancer l'application

1. Cloner le projet :

```bash
git clone https://github.com/votre-utilisateur/notifcool.git
cd notifcool
```

---
## Coté Administrateur
Un administrateur par défaut sera créé automatiquement avec comme identifiant de connexion :

- Email : admin@noticoo.com

- Mot de passe : Admin1234

---


## 📂 Base de données

---

- **Nom** : notifDB.db

- **Tables** :

  - employes

  - users

  - abonnements

  - notifications

  - envoieNotification

  - administrateurs

✅ L’application crée automatiquement l’administrateur si aucun n’existe.

💡 Bonnes pratiques appliquées
✅ Principe SRP : chaque classe a une responsabilité unique

✅ Principe OCP : code modulaire et extensible

✅ Pattern Repository + Service Layer

✅ Utilisation de PreparedStatement pour éviter les injections SQL

---
### 🙌 Auteur

---
Projet réalisé par **Diallo Ibrahim Sory**

📧 Contact : ibrahimsorydiallo204@outlook.com

---

### 📄 Licence


Ce projet est open-source. Utilisation libre à des fins pédagogiques ou personnelles.

--------
