package com.codespurt.basicappstructureboilerplate.utils;

public class Validations {

    private String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String NUMBER_PATTERN = "[0-9]+";
    private int PASSWORD_LENGTH = 8;

    public boolean checkEmail(String email) {
        if (checkEmpty(email)) {
            if (email.matches(EMAIL_PATTERN)) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkTextPassword(String password) {
        if (checkEmpty(password)) {
            if (password.length() > PASSWORD_LENGTH) {
                char[] chars = password.toCharArray();
                for (char c : chars) {
                    if (!Character.isLetter(c)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean checkNumberPassword(String password) {
        if (checkEmpty(password)) {
            if (password.matches(NUMBER_PATTERN) && password.length() > PASSWORD_LENGTH) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPasswordMatching(String password, String confirmPassword) {
        if (checkEmpty(password)) {
            if (checkEmpty(confirmPassword)) {
                return password.trim().equals(confirmPassword.trim());
            }
        }
        return false;
    }

    private boolean checkEmpty(String value) {
        return !value.trim().equals("");
    }
}