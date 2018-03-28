package Server.Core.PasswordContext;


import Server.Core.PasswordContext.PassGenStrategy;

import java.util.concurrent.ThreadLocalRandom;

public class StrongPassGenAlgorithm implements PassGenStrategy {
    private static final String letters = "qwertyuiopasdfghjklzxcvbnm";
    private static final String uppercaseLetters = "QWERTYUIOPASDFGHJKLZXCVBNM";
    private static final String numbers = "0123456789";
    private static final String specials = "éè*+°à§ù_-:§ù";


    @Override
    public char[] generatePassword() {
        char[] psw_generated;
        final int MIN_SIZE = 8;
        final int MAX_SIZE = 15;
        String totalChars = letters + uppercaseLetters + numbers + specials;

        int passSize;
        passSize = ThreadLocalRandom.current().nextInt(MIN_SIZE, MAX_SIZE+1);
        psw_generated = new char[passSize];
        for (int i = 0; i < psw_generated.length; i++) {
            psw_generated[i] = totalChars.charAt(ThreadLocalRandom.current().nextInt(0, totalChars.length() - 1));
        }
        System.out.println(psw_generated.toString());
        return psw_generated;
    }
}
