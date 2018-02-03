/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Checker;

import Common.Libro;

/**
 * Verifica la correttezza in termini di lunghezza, caratteri, etc.. relativi ai parametri di un Libro
 *
 * @author Lorenzo Gavazzeni
 */
public class BookChecker {

    public BookChecker() {

    }

    /**
     * Verifica l'ISBN abbia un formato corretto
     *
     * @param isbn isbn da verificare
     *
     * @return un intero con codici di errori diversificati:
     * 1) se vuoto, 2) se troppo lungo o troppo corto, 3) se contiene caratteri errati, 0) se corretto
     * */
    public int checkISBN(String isbn) {
        if (isbn.isEmpty()) {
            return Checker.EMPTY_CODE;
        }

        if (isbn.length() < 3 || isbn.length() > 3) {
            return 2;
        }

        for (int i = 0; i < isbn.length(); i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                return 3;
            }
        }

        return 0;
    }

    /**
     * Verifica il titolo rispetti i parametri di correttezza
     *
     * @param title titolo da verificare
     *
     * @return un intero con codici di errori diversificati:
     * 1) se vuoto, 2) se troppo lungo, 0) se corretto
     * */
    public int checkTitle(String title) {
        if (title.isEmpty()) {
            return Checker.EMPTY_CODE;
        }

        if (title.length() > 40) {
            return 2;
        }

        return 0;
    }

    /**
     * Verifica l'autore rispetti i parametri di correttezza
     *
     * @param autor autore da verificare
     *
     * @return un intero con codici di errori diversificati:
     * 1) se vuoto, 2) se troppo lungo, 3) se contiene numeri, 0) se corretto
     * */
    public int checkAutor(String autor) {
        if (autor.isEmpty()) {
            return Checker.EMPTY_CODE;
        }

        if (autor.length() > 30) {
            return 2;
        }

        for (int i = 0; i < autor.length(); i++) {
            if (Character.isDigit(autor.charAt(i))) {
                return 3;
            }
        }

        return 0;
    }

    /**
     * Verifica la casa editrice rispetti i parametri di correttezza
     *
     * @param editrice nome della casa editrice da verificare
     *
     * @return un intero con codici di errori diversificati:
     * 1) se vuoto, 2) se troppo lungo, 0) se corretto
     * */
    public int checkEditrice(String editrice) {
        if (editrice.isEmpty()) {
            return Checker.EMPTY_CODE;
        }

        if (editrice.length() > 30) {
            return 2;
        }

        return 0;
    }

    /**
     * Verifica la Stringa dell'anno di pubblicazione rispetti i requisiti
     *
     * @param anno_p anno pubblicazione da verificare
     *
     * @return un intero con codici di errori diversificati:
     * 1) se vuoto, 2) non è di 4 caratteri, 3) se contiene caratteri diversi da numeri, 0) se corretto
     * */
    public int checkAnno(String anno_p) {
        if (anno_p.isEmpty()) {
            return Checker.EMPTY_CODE;
        }

        if (anno_p.length() != 4) {
            return 2;
        }

        for (int i = 0; i < anno_p.length(); i++) {
            if (!Character.isDigit(anno_p.charAt(i))) {
                return 3;
            }
        }

        return 0;
    }

    /**
     * Verifica la Stringa dell'anno di ristampa rispetti i requisiti
     *
     * @param anno_r anno ristampa da verificare
     *
     * @return un intero con codici di errori diversificati:
     * 2) non è di 4 caratteri, 3) se contiene caratteri diversi da numeri, 0) se corretto
     * */
    public int checkAnnoRistampa(String anno_r) {

        if (anno_r.length() != 4) {
            return 2;
        }

        for (int i = 0; i < anno_r.length(); i++) {
            if (!Character.isDigit(anno_r.charAt(i))) {
                return 3;
            }
        }

        return 0;
    }

    /**
     * Verifica la Stringa della lingua rispetti i requisiti
     *
     * @param lingua Stringa della lingua da verificare
     *
     * @return un intero con codici di errori diversificati:
     * 1) se è vuota, 2) se contiene caratteri diversi da lettere, 0) se corretto
     * */
    public int checkLingua(String lingua) {
        if (lingua.isEmpty()) {
            return Checker.EMPTY_CODE;
        }

        for (int i = 0; i < lingua.length(); i++) {
            if (Character.isDigit(lingua.charAt(i))) {
                return 2;
            }
        }

        return 0;
    }

    /**
     * Verifica la Stringa dello scaffale rispetti i requisiti
     *
     * @param scaffale Stringa dello scaffale
     *
     * @return un intero con codici di errori diversificati:
     * 2) se la lung è maggiore di 2, 3) se contiene lettere, 0) se corretto
     * */
    public int checkScaffale(String scaffale) {
        if (scaffale.length() > 2) {
            return 2;
        }

        for (int i = 0; i < scaffale.length(); i++) {
            if (!Character.isDigit(scaffale.charAt(i))) {
                return 3;
            }
        }

        return 0;
    }

}
