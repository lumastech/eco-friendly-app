package com.lumastech.ecoapp.Models;

import com.google.gson.annotations.SerializedName;

    public class LoginRequest {
        @SerializedName("email")
        private String email;
        private String name;

        @SerializedName("password")
        private String password;

        @SerializedName("password_confirm")
        private String passwordConfirm;

        @SerializedName("device_name")
        private String deviceName; // For Laravel Sanctum device identification



        // Constructors
        public LoginRequest() {
        }

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public LoginRequest(String email, String password, String deviceName) {
            this.email = email;
            this.password = password;
            this.deviceName = deviceName;
        }

        // Getters and Setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        // Validation method
        public boolean isValid() {
            return email != null && !email.isEmpty() &&
                    password != null && !password.isEmpty();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPasswordConfirm() {
            return passwordConfirm;
        }

        public void setPasswordConfirm(String passwordConfirm) {
            this.passwordConfirm = passwordConfirm;
        }
    }
