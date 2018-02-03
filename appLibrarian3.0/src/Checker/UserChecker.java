/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Checker;

/**
 * Si occupa di fare i controlli per i parametri utente nella loro validità di Stringa (non verifica in DB)
 *
 * @author Lorenzo Gavazzeni
 */
public class UserChecker {

    public UserChecker() {
    }

    /**
     * Verifica che la stringa Nome o Cognome rispetti i parametri di lunghezza e correttezza nei caratteri
     *
     * @param nmcg Stringa da verificare
     *
     * @return un intero con codici di errori diversificati:
     * 1) se vuota, 2) se troppo lunga, 3) se i caratteri non sono coerenti, 0) se è corretto
     * */
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

    /**
     * Verifica che la stringa CodiceFiscale rispetti i parametri di lunghezza e correttezza nei caratteri
     *
     * @param cod Stringa da verificare
     *
     * @return un intero con codici di errori diversificati:
     * 1) se vuota, 2) se troppo corta, 3) se troppo lunga, 0) se è corretto
     * */
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

    /**
     * Verifica che la Mail inserita rispetti i parametri di lunghezza e correttezza nei caratteri
     *
     * @param email Stringa email da verificare
     *
     * @return un intero con codici di errori diversificati:
     * 1) se vuota, 2) se troppo lunga, 3) se non contiene la '@', 4) se compare meno di 1 carattere prima di '@'
     * 5) se dopo la '@' non compaiono caratteri, 6) se dopo il '.' non compaiono caratteri, 0) se corretto
     * */
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

    /**
     * Verifica che l'inquadramento inserito non sia vuoto
     *
     * @param inquadramento Stringa inquadramento da verificare
     *
     * @return un intero con codici di errori diversificati:
     * 1) se vuoto, 0) se corretto
     * */
    public int checkInquadramento(String inquadramento) {

        String inquadramento_tmp = inquadramento.toLowerCase();

        if (inquadramento_tmp.isEmpty()) {
            return Checker.EMPTY_CODE;
        }

        return 0;

    }

    /**
     * Verifica che il numero sia corretto
     *
     * @param numero Stringa con il numero da verificare
     *
     * @return un intero con codici di errori diversificati:
     * 1) se vuoto, 2) se la lunghezza eccede il limite(15), 3) se contiene caratteri diversi da numeri
     * 4) se troppo corto, 0) se corretto
     * */
    public int checkNumero(String numero) {
        if (numero.isEmpty()) {
            return Checker.EMPTY_CODE;
        }

        if (numero.length() > 15) {
            return 2;
        }

        char[] checknum = numero.toCharArray();

        for (char c : checknum) {
            if (!Character.isDigit(c)) {
                return 3;
            }
        }

        if (numero.length() < 5) {
            return 4;
        }

        return 0;
    }

    /**
     * Verifica che la password sia corretta
     *
     * @param psw array di caratteri da verificare
     *
     * @return un intero con codici di errori diversificati:
     * 1) se vuoto, 2) se troppo corta, 3) se non contiene numeri, 4) se non contiene maiuscole, 5) se troppo lunga, 0) se corretto
     * */
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

    /**
     * Verifica il codice generato rispetti i requisiti
     *
     * @param codice codice da verificare
     *
     * @return un intero con codici di errori diversificati:
     * 1) se vuoto, 2) se troppo lungo, 3) se contiene caratteri errati, 4) se troppo corto, 0) se corretto
     * */
    public int checkCodice(String codice) {
        if (codice.isEmpty()) {
            return Checker.EMPTY_CODE;
        }

        if (codice.length() > 10) {
            return 2;
        }

        char[] checknum = codice.toCharArray();

        for (char c : checknum) {
            if (!Character.isDigit(c)) {
                return 3;
            }
        }

        if (codice.length() < 5) {
            return 4;
        }

        return 0;
    }

}
