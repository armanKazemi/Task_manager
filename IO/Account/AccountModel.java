package IO.Account;

import java.io.Serializable;

public class AccountModel implements Serializable {
    private String username ;
    private String password ;
    private String fileAddress ;
    final int encryptionKey = 3;

    AccountModel(String username, String password){
        this.username = username;
        this.password = password;
        setFileAddress();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private void setFileAddress (){
        fileAddress = decryptionString(getUsername()) + ".dat" ;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void encryption() {
        encryptionString(username);
        encryptionString(password);
    }

    public String encryptionString(String string){
        char [] chars = string.toCharArray();
        char [] encryptionChars = new char[chars.length];

        for (int counter = 0 ; counter < chars.length ; counter++){
            encryptionChars[counter] = (char) (chars[counter] + encryptionKey) ;
        }
        return encryptionChars.toString();
    }

    public void decryption() {
        decryptionString(username);
        decryptionString(password);
    }

    public String decryptionString(String string){
        char [] chars = string.toCharArray();
        char [] decryptionChars = new char[chars.length];

        for (int counter = 0 ; counter < chars.length ; counter++){
            decryptionChars[counter] = (char) (chars[counter] - encryptionKey) ;
        }
        return decryptionChars.toString();
    }
}
