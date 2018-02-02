/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Checker;

import Common.Libro;

/**
 *
 * @author Lorenzo Gavazzeni
 */
public class BookChecker {

    public BookChecker() {

    }

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

    public int checkTitle(String title) {
        if (title.isEmpty()) {
            return Checker.EMPTY_CODE;
        }

        if (title.length() > 40) {
            return 2;
        }

        return 0;
    }

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

    public int checkEditrice(String editrice) {
        if (editrice.isEmpty()) {
            return Checker.EMPTY_CODE;
        }

        if (editrice.length() > 30) {
            return 2;
        }

        return 0;
    }

    public int checkAnno(String rist) {
        if (rist.isEmpty()) {
            return Checker.EMPTY_CODE;
        }

        if (rist.length() != 4) {
            return 2;
        }

        for (int i = 0; i < rist.length(); i++) {
            if (!Character.isDigit(rist.charAt(i))) {
                return 3;
            }
        }

        return 0;
    }

    public int checkAnnoRistampa(String rist) {

        if (rist.length() != 4) {
            return 2;
        }

        for (int i = 0; i < rist.length(); i++) {
            if (!Character.isDigit(rist.charAt(i))) {
                return 3;
            }
        }

        return 0;
    }

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
