/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Checker;

/**
 *
 * @author Lorenzo Gavazzeni
 */
public class UserChecker {

    public UserChecker() {
    }

    public int checkAvaliableNomeCognome(String nmcg) {
        if (nmcg.isEmpty()) {
            return Checker.EMPTY_CODE;
        }
        if (nmcg.length() > 20) {
            return 2;
        }

        char[] scomp = nmcg.toCharArray();

        for (char c : scomp) {
            if (!Character.isLetter(c)) {
                if (!Character.isWhitespace(c)) {
                    return 3;
                }
            }
        }

        return 0;

    }

    public int checkCodiceFiscale(String cod) {
        if (cod.isEmpty()) {
            return Checker.EMPTY_CODE;
        }

        if (cod.length() < 4) {
            return 2;
        }
        if (cod.length() > 5) {
            return 3;
        }

        return 0;
    }

    public int checkEmail(String email) {

        if (email.isEmpty()) {
            return Checker.EMPTY_CODE;
        }
        if (email.length() > 30) {
            return 2;
        }

        int at_posix = 0;

        if (!email.contains("@")) {
            return 3;
        } else {
            int counter_at = 0;

            for (int i = 0; i < email.length(); i++) {
                if (email.charAt(i) == '@') {

                    counter_at++;
                    at_posix = i;
                }
                if (counter_at > 1) {
                    return 4;
                }
            }

        }

        int point_occ = 0;

        if (at_posix == 0) {
            return 5;
        }

        for (int i = at_posix; i < email.length(); i++) {

            if (email.charAt(i) == '.') {
                point_occ++;
            }

        }

        if (point_occ < 1) {
            return 6;
        }

        return 0;

    }

    public int checkInquadramentoClasse(String classe_inq) {

        String inquadramento_tmp = classe_inq.toLowerCase();

        if (inquadramento_tmp.isEmpty()) {
            return Checker.EMPTY_CODE;
        }

        if (classe_inq.length() == 2) {
            return 0;
        } else {
            return 2;
        }

    }

    public int checkNumero(String s) {
        if (s.isEmpty()) {
            return Checker.EMPTY_CODE;
        }

        if (s.length() > 15) {
            return 2;
        }

        char[] checknum = s.toCharArray();

        for (char c : checknum) {
            if (!Character.isDigit(c)) {
                return 3;
            }
        }

        if (s.length() < 5) {
            return 4;
        }

        return 0;
    }

    public int checkPassword(char[] psw) {
        int counter_number = 0;
        int counter_maiusc = 0;

        if (psw.length == 0) {
            return Checker.EMPTY_CODE;
        }

        if (psw.length < 6) {
            return 2;
        }

        for (char c : psw) {
            if (c >= '0' && c <= '9') {
                counter_number++;
            } else if (c >= 'A' && c <= 'Z') {
                counter_maiusc++;
            }
        }

        if (counter_number < 1) {
            return 3;
        }
        if (counter_maiusc < 1) {
            return 4;
        }

        if (psw.length > 15) {
            return 5;
        }

        return 0;
    }

    public int checkCodice(String s) {
        if (s.isEmpty()) {
            return Checker.EMPTY_CODE;
        }

        if (s.length() > 10) {
            return 2;
        }

        char[] checknum = s.toCharArray();

        for (char c : checknum) {
            if (!Character.isDigit(c)) {
                return 3;
            }
        }

        if (s.length() < 5) {
            return 4;
        }

        return 0;
    }

}
