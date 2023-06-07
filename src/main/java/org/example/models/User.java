package org.example.models;

public class User {
        String name;

        String role;
        public String number;
        public User(String userName, String password, String role) {
            this.name = userName;
            this.number = password;
            this.role = role;
        }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return number;
    }

    public String getRole() {
        return role;
    }
    }

