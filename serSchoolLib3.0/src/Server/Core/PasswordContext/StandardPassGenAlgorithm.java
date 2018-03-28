package Server.Core.PasswordContext;



import Server.Core.PasswordContext.PassGenStrategy;

import java.util.concurrent.ThreadLocalRandom;

public class StandardPassGenAlgorithm implements PassGenStrategy {
    private static final String[] PASSWORD_WORD_ARRAY = {"ARANCIA", "DRESANO", "BANANA", "MELONE", "BARCHETTA", "SGABELLO", "NATURALE"};

    @Override
        public char[] generatePassword() {
            char[] psw_generated;

            int random_psw_posix = ThreadLocalRandom.current().nextInt(0, PASSWORD_WORD_ARRAY.length - 1);
            int last_index = 0;

            psw_generated = new char[PASSWORD_WORD_ARRAY[random_psw_posix].length() + 2];
            boolean putToLowerCase;
            for (int i = 0; i < PASSWORD_WORD_ARRAY[random_psw_posix].length(); i++) {
                putToLowerCase = ThreadLocalRandom.current().nextBoolean();
                if(!putToLowerCase) {
                    psw_generated[i] = PASSWORD_WORD_ARRAY[random_psw_posix].charAt(i);
                }
                else{
                    psw_generated[i] = Character.toLowerCase(PASSWORD_WORD_ARRAY[random_psw_posix].charAt(i));
                }
                last_index = i;
            }

            for (int i = last_index + 1; i < psw_generated.length; i++) {
                psw_generated[i] = Integer.toString(ThreadLocalRandom.current().nextInt(0, 9 + 1)).charAt(0);
            }
            // String password = new String(psw_generated);
            //String finalPassword;
            // password = passToolkit.randomLowerCase(password);
            //finalPassword = passToolkit.addRandomNumbers(password);
            //Quella sotto è la funzione che permette di effettuare l'hashing della password
            // password = passToolkit.hashPassword(password,"$(d*:>3W#"); bisogna assicurare che l'user non riceva la password hashata, bensì quella finale, che per comodità ho chiamato finalPassword
            // psw_generated = password.toCharArray();
System.out.println(psw_generated);
            return psw_generated;



    }


    }
