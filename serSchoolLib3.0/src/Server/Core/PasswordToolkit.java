package Server.Core;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ThreadLocalRandom;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ThreadLocalRandom;


/**
 *
 * @author User
 */
public class PasswordToolkit {


    /**
     * data una password in ingresso rendere un numero random in posizione random di caratteri minuscoli
     *
     * @param password la password da modifiare
     *
     * @return la password modificata
     * */
    public String randomLowerCase(String password){
        //random letters to lowercase
        String newPassword;
        int nOfIterations = ThreadLocalRandom.current().nextInt(1,password.length());
        StringBuilder sb = new StringBuilder();
        int index;
        char [] passArray = password.toCharArray();
        while(nOfIterations>0){
            index = ThreadLocalRandom.current().nextInt(1,passArray.length);
            if (Character.isUpperCase(passArray[index])){
                passArray[index] = Character.toLowerCase(passArray[index]);
                nOfIterations--;
            }
        }
        for (int i=0;i<passArray.length;i++){
            sb.append(passArray[i]);
        }
        newPassword = sb.toString();

        return newPassword;



    }

    /**
     * aggiunge alla password in ingresso dei numeri randomici
     *
     * @param password la password su cui aggiungere
     *
     * @return la password modificata
     * */
    public String addRandomNumbers(String password){

        int bound = ThreadLocalRandom.current().nextInt(3,7);
        while (bound>0){
            password = password + ThreadLocalRandom.current().nextInt(0,9);
            bound--;
        }
        return password;

    }


    /**
     * esegue un Hash della password in ingresso
     *
     * @param password la password su cui effettuare Hash
     *
     * @return la password modificata
     * */
    public String hashPassword(String password, String salt){
        String generatedPassword = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e){
        }
        return generatedPassword;
    }



}