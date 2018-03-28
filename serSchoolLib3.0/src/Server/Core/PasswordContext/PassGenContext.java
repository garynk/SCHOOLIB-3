package Server.Core.PasswordContext;


public class PassGenContext {
    private PassGenStrategy strategy;
    //this can be set at runtime by the application preferences
    public void setPassGenStrategy(PassGenStrategy strategy) {
        this.strategy = strategy;
    }

    //use the strategy
    public char[] createNewPassword() {
        char [] psw;
       psw = strategy.generatePassword();
       return psw;

    }
}
