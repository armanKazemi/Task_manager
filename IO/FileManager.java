package IO;

import IO.Account.AccountModel;
import Program.TaskManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileManager {
    TaskManager taskManager;
    AccountModel accountModel;

    public FileManager (TaskManager taskManager, AccountModel accountModel){
        this.taskManager = taskManager;
        this.accountModel = accountModel;
    }

    public void save (){
        try {
            ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream(accountModel.getFileAddress()));
            objectOutput.writeObject(taskManager);
            objectOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

