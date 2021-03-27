package IO.Account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class AccountList implements Serializable {
    private ArrayList<AccountModel> accountsList = new ArrayList<>();

    AccountList() {
    }

    AccountList(ArrayList<AccountModel> usersPassList){
        this.accountsList = usersPassList ;
    }


    public void setAccountsList(ArrayList<AccountModel> usersPassList) {
        this.accountsList = usersPassList;
    }

    public ArrayList<AccountModel> getAccountsList() {
        return accountsList;
    }


    public void addAccount (AccountModel accountModel){
        accountsList.add(accountModel);
    }

    public AccountModel getAccount (String username){
        try {
            for (AccountModel element : accountsList){
                if (element.getUsername().equals(username)){
                    return element;
                }
            }
        } catch (NoSuchElementException e){
            e.printStackTrace();
        }
        return null;
    }


    public void deleteElement (String username){
        int numberElement = getNumberElement (username);
        accountsList.remove(numberElement);
    }

    private int getNumberElement (String username){
        int number = 0;
        for (AccountModel element : accountsList){
            if (element.getUsername().equals(username)){
                break;
            }
            number++ ;
        }
        return number;
    }


    public void encryption (){
        for (AccountModel accountModel : accountsList){
            accountModel.encryption();
        }
    }

    public void decryption (){
        for (AccountModel accountModel : accountsList){
            accountModel.decryption();
        }
    }
}
