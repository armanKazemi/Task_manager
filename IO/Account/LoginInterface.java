package IO.Account;

import Program.TaskManager;
import Program.UserInterface;

import java.io.*;
import java.util.Scanner;

public class LoginInterface {
    private Scanner scanner;
    private char entryKey;
    private int index = 0;

    public LoginInterface() {
        scanner = new Scanner(System.in);
    }

    private final static String accountFileName = "AccountFiles.dat";
    private final static String FUNCTION_TYPE_SING_IN = "sing_in";
    private final static String FUNCTION_TYPE_EDIT_PASSWORD = "edit_password";

    private final static char NEW_ACCOUNT = '1';
    private final static char SING_IN = '2';

    private final static char TASKS = '1';
    private final static char EDIT_PASSWORD = '2';
    private final static char DELETE_ACCOUNT = '3';
    private final static char LOG_OUT = '4';

    private final static char EXIT = 'e';

    private PrintStream printType = System.out;
    public void setPrintType(PrintStream printType) {
        this.printType = printType;
    }


    public void logging (){
        print (getLoggingMenu(), printType);
        entryKey = scanner.nextLine().toLowerCase().trim().charAt(0);

        switch (entryKey){
            case NEW_ACCOUNT :
                createNewAccount();
                break;
            case SING_IN:
                singIn();
                break;
            case EXIT :
                System.out.println("Exited.");
                System.exit(0);
            default :
                System.out.println("Wrong input.");
                logging();
        }
    }


    String getLoggingMenu(){
        StringBuilder stringBuilderLoginMenu = new StringBuilder() ;
        stringBuilderLoginMenu.append("\nWelcome to TASK MANAGER app.");
        stringBuilderLoginMenu.append("\n");
        stringBuilderLoginMenu.append(NEW_ACCOUNT + "- Register now.");
        stringBuilderLoginMenu.append('\n');
        stringBuilderLoginMenu.append(SING_IN + "- Sing In.");
        stringBuilderLoginMenu.append('\n');
        stringBuilderLoginMenu.append(EXIT + "- Exit app.");

        return stringBuilderLoginMenu.toString();
    }


    void createNewAccount(){
        AccountList accountList;
        AccountModel accountModel;
        ObjectInputStream objectInput;
        TaskManager taskManager = new TaskManager();

        try {
            ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream(accountFileName));
            objectOutput.close();

            objectInput = new ObjectInputStream(new FileInputStream(accountFileName));
            accountList = (AccountList) objectInput.readObject();
            accountList.decryption();
            objectInput.close();
        } catch (IOException | ClassNotFoundException e){
            accountList = null;
        }

        accountModel = getNewAccount(accountList);

        try {
            ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream(accountModel.getFileAddress()));
            objectOutput.writeObject(taskManager);
            objectOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        firstStartMenu(taskManager, accountModel, accountList);
    }


    AccountModel getNewAccount(AccountList accountList) {
        String username, password;
        final String usernameHint = "Enter a new username [It can include letters and number] : ";
        final String unavailableUsernameHint = "The username is unavailable, try another username [It can include letters and number] : ";
        final String passwordHint = "Enter your password [It can include letters and number, 8 Elements] : ";
        ObjectOutputStream objectOutput;

        username = getUsername(accountList, usernameHint, unavailableUsernameHint, false);

        print(passwordHint, printType);
        password = scanner.nextLine().replaceAll("\\s","");

        AccountModel accountModel = new AccountModel(username, password);

        if (accountList == null) {
            accountList = new AccountList();
        }

        accountList.addAccount(accountModel);

        try {
            accountList.encryption();
            objectOutput = new ObjectOutputStream(new FileOutputStream(accountFileName));
            objectOutput.writeObject(accountList);
            objectOutput.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return accountModel;
    }


    String getUsername(AccountList accountList, String usernameHint, String beAvailableHint, boolean newUserOrNot){
        String username;
        boolean repetitiousUsername;

        print(usernameHint, printType);

        while (true) {
            username = scanner.nextLine().replaceAll("\\s", "");
            repetitiousUsername = checkSameUsernames(username, newUserOrNot, accountList);
            if (!repetitiousUsername) {
                break;
            }
            print(beAvailableHint, printType);
        }
        return username;
    }


    boolean checkSameUsernames(String username, boolean newUserOrNot, AccountList accountList){
        if (accountList != null) {
            for (AccountModel accountModel : accountList.getAccountsList()) {
                if (accountModel.getUsername().equals(username)) {
                    return !newUserOrNot;
                }
            }
            return newUserOrNot;
        }
        return newUserOrNot;
    }


    void singIn(){
        String username, password;
        boolean loginOrNot = false;
        final String usernameHint = "Enter your username : ";
        final String unavailableUsernameHint = "This username doesn't exist, try again : ";
        final String passwordHint = "Enter your password : ";
        final String passwordWrongHint = "Password is wrong. Try again. [for change username enter 'b'] ";
        AccountList accountList;
        TaskManager taskManager = new TaskManager();
        ObjectInputStream objectInput;

        try {
            objectInput = new ObjectInputStream(new FileInputStream(accountFileName));
            accountList = (AccountList) objectInput.readObject();
            accountList.decryption();
            objectInput.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            accountList = null;
        }

        username = getUsername(accountList, usernameHint, unavailableUsernameHint, true);
        AccountModel accountModel = accountList.getAccount(username);
        loginOrNot = getPassword(taskManager, accountList, accountModel, passwordHint, passwordWrongHint, FUNCTION_TYPE_SING_IN);

        if (loginOrNot){
            try {
                objectInput = new ObjectInputStream(new FileInputStream(accountModel.getFileAddress()));
                taskManager = (TaskManager) objectInput.readObject();
                objectInput.close();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }

        firstStartMenu(taskManager, accountModel, accountList);
    }


    boolean getPassword(TaskManager taskManager, AccountList accountList, AccountModel accountModel, String passwordHint, String passwordWrongHint, String functionType){
        String password;
        boolean repetitiousPass;

        print('[' + accountModel.getUsername() + ']' + '\n' + passwordHint, printType);

        while (true) {
            password = scanner.nextLine().replaceAll("\\s", "");

            if (accountModel.getPassword().equals(password)) {
                break;
            }

            if (functionType == FUNCTION_TYPE_SING_IN && password.toLowerCase().charAt(0) == 'b') {
                singIn();
            }

            if (functionType == FUNCTION_TYPE_EDIT_PASSWORD && password.toLowerCase().charAt(0) == 'b') {
                firstStartMenu(taskManager, accountModel, accountList);
            }

            print(passwordHint, printType);
        }
        return true;
    }


    public void firstStartMenu (TaskManager taskManager, AccountModel accountModel, AccountList accountList){

        print (getFirstStartMenu(), printType);
        entryKey = scanner.nextLine().toLowerCase().trim().charAt(0);

        switch (entryKey){
            case TASKS :
                UserInterface userInterface = new UserInterface(taskManager, accountModel);
                userInterface.start();
                break;
            case EDIT_PASSWORD :
                editPassword(taskManager, accountList, accountModel);
                break;
            case DELETE_ACCOUNT :
                accountList.deleteElement(accountModel.getUsername());
                logging();
                break;
            case LOG_OUT :
                logging();
                break;
            case EXIT :
                System.out.println("Exited.");
                System.exit(0);
            default :
                System.out.println("Wrong input.");
                firstStartMenu(taskManager, accountModel, accountList);
        }
    }


    String getFirstStartMenu(){
        StringBuilder stringBuilderFirstStartMenu = new StringBuilder() ;
        stringBuilderFirstStartMenu.append('\n');
        stringBuilderFirstStartMenu.append("***TASK MANAGER***");
        stringBuilderFirstStartMenu.append("\n");
        stringBuilderFirstStartMenu.append(TASKS + "- Tasks");
        stringBuilderFirstStartMenu.append('\n');
        stringBuilderFirstStartMenu.append(EDIT_PASSWORD + "- Edit password.");
        stringBuilderFirstStartMenu.append('\n');
        stringBuilderFirstStartMenu.append(DELETE_ACCOUNT + "- Delete account.");
        stringBuilderFirstStartMenu.append('\n');
        stringBuilderFirstStartMenu.append(LOG_OUT + "- Log out.");
        stringBuilderFirstStartMenu.append('\n');
        stringBuilderFirstStartMenu.append(EXIT + "- Exit app.");
        stringBuilderFirstStartMenu.append('\n');

        return stringBuilderFirstStartMenu.toString();
    }


    void editPassword (TaskManager taskManager, AccountList accountList, AccountModel accountModel){
        final String confirmPassword = "Enter your password : ";
        final String passwordWrongHint = "Password is wrong. Try again. [Back 'b'] ";
        final String newPasswordHint = "Enter a new password : ";
        String newPassword;

        boolean loginOrNot = getPassword(taskManager, accountList, accountModel, confirmPassword, passwordWrongHint, FUNCTION_TYPE_SING_IN);

        if (loginOrNot){
            print(newPasswordHint, printType);
            newPassword = scanner.nextLine().replaceAll("\\s", "");

            accountList.getAccount(accountModel.getUsername()).setPassword(newPassword);

            try {
                accountList.encryption();
                ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream(accountFileName));
                objectOutput.writeObject(accountList);
                objectOutput.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        firstStartMenu(taskManager, accountModel, accountList);
    }


    void print (Object object, PrintStream printStream){
        printStream.println(object);

    }
}
