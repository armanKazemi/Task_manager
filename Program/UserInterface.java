package Program;

import IO.Account.AccountModel;
import IO.FileManager;
import Program.models.ItemTask;
import Program.models.ItemsTaskList;
import Program.models.SimpleTask;
import Program.models.TimeTask;

import java.io.PrintStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Tag implements Serializable {
    private final static String notTagged = "Not tagged[enter 1]";
    private final static String work = "Work";
    private final static String life = "Life";
    private final static String personal = "Personal";
    private final static String newTag = "New Tag";

    @Override
    public String toString() {
        return "Tag {" +
                notTagged + ",\'" +
                work + '\'' +
                ",\'" + life + '\'' +
                ",\'" + personal + '\'' +
                ",\'" + newTag + '\'' +
                '}';
    }
}

public class UserInterface {
    private Scanner scanner;
    private char entryKey;
    private int index = 0;

    private TaskManager taskManager;
    private AccountModel accountModel;

    public UserInterface(TaskManager taskManager, AccountModel accountModel) {
        scanner = new Scanner(System.in);
        this.taskManager = taskManager;
        this.accountModel = accountModel;
    }

    private final static char SAVE_TASKS = 's';
    private final static char EXIT = 'e';
    private final static char BACK = '0';

    private final static char NEW_TASK = '1';
    private final static char DISPLAY_TASKS = '2';

    private final static char SIMPLE_TASK = '1';
    private final static char TIME_TASK = '2';
    private final static char ITEMS_TASK = '3';

    private final static char DISPLAY_DETAILS_TASK = '1';
    private final static char DO_TASK = '2';
    private final static char EDIT_TASK = '3';
    private final static char REMOVE_TASK = '4';

    private final static String TITLE = "title";
    private final static String DESCRIPTION = "description";

    private final static char EDIT_TITLE = '1';
    private final static char EDIT_DESCRIPTION = '2';
    private final static char EDIT_TAG = '3';
    private final static char EDIT_DATE = '4';
    private final static char EDIT_ITEMS = '4';

    private PrintStream printType = System.out;

    public void setPrintType(PrintStream printType) {
        this.printType = printType;
    }


    public void start (){
        print (getStartMenu(), printType);
        entryKey = scanner.nextLine().toLowerCase().trim().charAt(0);

        switch (entryKey){
            case NEW_TASK :
                newTask();
                break;
            case DISPLAY_TASKS :
                displayTasks();
                break;
            case SAVE_TASKS :
                save(taskManager, accountModel);
                start();
                break;
            case EXIT :
                System.out.println("Exited.");
                System.exit(0);
            default :
                System.out.println("Wrong input.");
                start();
        }
    }


    public String getStartMenu(){
        StringBuilder stringBuilderStartMenu = new StringBuilder() ;
        stringBuilderStartMenu.append("\nWelcome to TASK MANAGER app.");
        stringBuilderStartMenu.append("\n");
        stringBuilderStartMenu.append(NEW_TASK + "- New task.");
        stringBuilderStartMenu.append('\n');
        stringBuilderStartMenu.append(DISPLAY_TASKS + "- Display my tasks.");
        stringBuilderStartMenu.append('\n');
        stringBuilderStartMenu.append(SAVE_TASKS + "- Save tasks.");
        stringBuilderStartMenu.append('\n');
        stringBuilderStartMenu.append(EXIT + "- Exit app.");
        stringBuilderStartMenu.append('\n');

        return stringBuilderStartMenu.toString();
    }


    public void newTask(){
        print (getNewTaskMenu(), printType);
        entryKey = scanner.nextLine().toLowerCase().trim().charAt(0);

        switch (entryKey){
            case SIMPLE_TASK :
                newSimpleTask();
                start();
                break;
            case TIME_TASK :
                newTimeTask();
                start();
                break;
            case ITEMS_TASK :
                newItemsTask();
                newTask();
                break;
            case SAVE_TASKS :
                save(taskManager, accountModel);
                start();
                break;
            case BACK :
                start();
                break;
            default :
                System.out.println("Wrong input.");
                newTask();
        }
    }

    public void newSimpleTask(){
        String title = (String) getOptions (TITLE);
        String description = (String) getOptions(DESCRIPTION);
        String tag = (String) getOptions(new Tag().toString());
        if (tag.trim().charAt(0) == '1'){
            SimpleTask simpleTask = new SimpleTask(title, description);
            taskManager.newTask(simpleTask);
        } else {
            SimpleTask simpleTask = new SimpleTask(title, description, tag);
            taskManager.newTask(simpleTask);
        }
        displayDetailsTasks(taskManager.getTasks().size()-1);
    }

    public void newTimeTask(){
        String title = (String) getOptions (TITLE);
        String description = (String) getOptions(DESCRIPTION);
        String tag = (String) getOptions(new Tag().toString());
        Date date = getDate();

        if (tag.trim().charAt(0) == '1'){
            if (date == null){
                print("You didn't enter date, can you edit task after creating. ", printType);
                TimeTask timeTask = new TimeTask(title, description);
                taskManager.newTask(timeTask);
            } else {
                TimeTask timeTask = new TimeTask(title, description ,date);
                taskManager.newTask(timeTask);
            }

        } else {
            if (date == null){
                print("You didn't enter date, can you edit task after creating. ", printType);
                TimeTask timeTask = new TimeTask(title, description, tag);
                taskManager.newTask(timeTask);
            } else {
                TimeTask timeTask = new TimeTask(title, description, tag, date);
                taskManager.newTask(timeTask);
            }
        }
        displayDetailsTasks(taskManager.getTasks().size()-1);
    }

    public void newItemsTask() {
        String title = (String) getOptions(TITLE);
        String description = (String) getOptions(DESCRIPTION);
        String tag = (String) getOptions(new Tag().toString());
        ArrayList<ItemTask> items = getItems();

        if (tag.trim().charAt(0) == '1') {
            if (items == null){
                print("You didn't enter any item, can you edit task after creating. ", printType);
                ItemsTaskList itemsTaskList = new ItemsTaskList(title, description);
                taskManager.newTask(itemsTaskList);
            } else {
                ItemsTaskList itemsTaskList = new ItemsTaskList(title, description, items);
                taskManager.newTask(itemsTaskList);
            }
        } else {
            if (items == null){
                print("You didn't enter any item, can you edit task after creating. ", printType);
                ItemsTaskList itemsTaskList = new ItemsTaskList(title, description, tag);
                taskManager.newTask(itemsTaskList);
            } else {
                ItemsTaskList itemsTaskList = new ItemsTaskList(title, description, tag, items);
                taskManager.newTask(itemsTaskList);
            }
        }
        displayDetailsTasks(taskManager.getTasks().size() - 1);
    }

    public ArrayList<ItemTask> getItems (){
        ArrayList<ItemTask> arrayList = new ArrayList<>();
        ItemTask itemTask = new ItemTask();
        while (true){
            print("Enter new item task : ", printType);
            print("Enter new item name[quit : 'q'] : ", printType);
            String firstInput = scanner.nextLine().trim();
            if (firstInput.toLowerCase().equals("q")){
                break;
            } else {
                print("Enter new item name : ", printType);
                itemTask.setNameItem(firstInput);
                arrayList.add(itemTask);
            }
        }
        return arrayList;
    }

    public Object getOptions(String string){
        print("New " + string, printType);
        return scanner.nextLine().trim();
    }

    public Date getDate()  {
        print("Enter data in this format : YYYY-MM-DD, \"enter\" HH-MM, \"enter\". ", printType);
        String dateFormat = scanner.nextLine().trim();
        String clockFormat = scanner.nextLine().trim();
        Date date = null;
        try {
            date = new SimpleDateFormat("YYYY-MM-DD HH-mm").parse(dateFormat + " " + clockFormat);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return date;
    }


    public String getNewTaskMenu(){
        StringBuilder stringBuilderNewTaskMenu = new StringBuilder();
        stringBuilderNewTaskMenu.append("\nCreate a new task.");
        stringBuilderNewTaskMenu.append("\n");
        stringBuilderNewTaskMenu.append(SIMPLE_TASK + "- Simple task.");
        stringBuilderNewTaskMenu.append('\n');
        stringBuilderNewTaskMenu.append(TIME_TASK + "- Time task.");
        stringBuilderNewTaskMenu.append('\n');
        stringBuilderNewTaskMenu.append(ITEMS_TASK + "- Item task.");
        stringBuilderNewTaskMenu.append('\n');
        stringBuilderNewTaskMenu.append(SAVE_TASKS + "- Save tasks.");
        stringBuilderNewTaskMenu.append('\n');
        stringBuilderNewTaskMenu.append(BACK + "- Back.");
        stringBuilderNewTaskMenu.append('\n');

        return stringBuilderNewTaskMenu.toString();
    }


    public void displayTasks(){
        displayAllTasks();
        print (getDisplayMenu(), printType);

        StringBuilder stringBuilder = new StringBuilder(scanner.nextLine().toLowerCase().trim());
        entryKey = stringBuilder.charAt(0);
        if (stringBuilder.length() > 1) {
            stringBuilder.delete(0, 1);
            index = Integer.parseInt(stringBuilder.toString().trim());
            index--;
        }

        switch (entryKey){
            case DISPLAY_DETAILS_TASK :
                displayDetailsTasks(index);
                break;
            case DO_TASK :
                doTask(index);
                break;
            case EDIT_TASK :
                editTask(index);
                break;
            case REMOVE_TASK :
                removeTask(index);
                break;
            case SAVE_TASKS :
                save(taskManager, accountModel);
                displayTasks();
                break;
            case BACK :
                start();
                break;
            default :
                System.out.println("Wrong input.");
                newTask();
        }
    }

    public void displayAllTasks (){
        print("All tasks : ", printType);
        int index = 1;
        for (Object task : taskManager.getTasks()){
            print(index, task, printType);
            index++;
        }
    }

    public void displayDetailsTasks (int index){
        print(taskManager.getTask(index), printType);
        back();
        displayTasks();
    }

    public void doTask(int index){
        taskManager.getTask(index).done();
        displayDetailsTasks(index);
        back();
        displayTasks();
    }

    public void editTask(int index){
        if (taskManager.getTask(index) instanceof TimeTask){
            editTimeTask((TimeTask)taskManager.getTask(index));
        } else if (taskManager.getTask(index) instanceof ItemsTaskList){
            editItemsTask((ItemsTaskList)taskManager.getTask(index));
        } else {
            editSimpleTask(taskManager.getTask(index));
        }
    }

    public void removeTask(int index){
        taskManager.removeTask(index);
        back();
        displayTasks();
    }

    public void back (){
        do {
            print("Back : " + BACK, printType);
            entryKey = scanner.nextLine().toLowerCase().trim().charAt(0);
        } while (entryKey != BACK);
    }

    public void editSimpleTask(SimpleTask simpleTask){
        print(simpleTask, printType);
        print(getDisplayEditMenuForSimpleTask(), printType);
        entryKey = scanner.nextLine().toLowerCase().trim().charAt(0);

        switch (entryKey){
            case EDIT_TITLE :
                editTitle(simpleTask);
                break;
            case EDIT_DESCRIPTION :
                editDescription(simpleTask);
                break;
            case EDIT_TAG :
                editTag(simpleTask);
                break;
            case SAVE_TASKS :
                save(taskManager, accountModel);
                editSimpleTask(simpleTask);
                break;
            case BACK :
                start();
                break;
            default :
                System.out.println("Wrong input.");
                newTask();
        }
    }

    public void editTimeTask(TimeTask timeTask){
        print(timeTask, printType);
        print(getDisplayEditMenuForTimedTask(), printType);
        entryKey = scanner.nextLine().toLowerCase().trim().charAt(0);

        switch (entryKey){
            case EDIT_TITLE :
                editTitle(timeTask);
                break;
            case EDIT_DESCRIPTION :
                editDescription(timeTask);
                break;
            case EDIT_TAG :
                editTag(timeTask);
                break;
            case EDIT_DATE :
                editDate(timeTask);
                break;
            case SAVE_TASKS :
                save(taskManager, accountModel);
                editTimeTask(timeTask);
                break;
            case BACK :
                start();
                break;
            default :
                System.out.println("Wrong input.");
                newTask();
        }
    }

    public void editItemsTask(ItemsTaskList itemsTaskList){
        print(itemsTaskList, printType);
        print(getDisplayEditMenuForItemsTask(), printType);
        entryKey = scanner.nextLine().toLowerCase().trim().charAt(0);

        switch (entryKey){
            case EDIT_TITLE :
                editTitle(itemsTaskList);
                break;
            case EDIT_DESCRIPTION :
                editDescription(itemsTaskList);
                break;
            case EDIT_TAG :
                editTag(itemsTaskList);
                break;
            case EDIT_ITEMS :
                editItems(itemsTaskList);
                break;
            case SAVE_TASKS :
                save(taskManager, accountModel);
                editItemsTask(itemsTaskList);
                break;
            case BACK :
                start();
                break;
            default :
                System.out.println("Wrong input.");
                newTask();
        }
    }

    public void editTitle(SimpleTask task){
        print("Enter new title : ", printType);
        String title = scanner.nextLine().trim();
        task.setTitle(title);
    }

    public void editDescription(SimpleTask task){
        print("Enter new description : ", printType);
        String description = scanner.nextLine().trim();
        task.setDescription(description);
    }

    public void editTag(SimpleTask task){
        print("Enter new tag : ", printType);
        String tag = scanner.nextLine().trim();
        task.setTag(tag);
    }

    public void editDate(TimeTask task){
        print("Enter new date : ", printType);
        Date date = getDate();
        task.setDate(date);
    }

    public void editItems(ItemsTaskList task){
        print(getItem(task) ,printType);
        print("Enter item number for edit item[enter 'n' to add new item] : ", printType);
        String input = scanner.nextLine().trim();

        if (input.toLowerCase().charAt(0) == 'n'){
            print("Enter new item name : ", printType);
            task.addItemToTaskList(scanner.nextLine());
        } else {
            index = Integer.parseInt(input);
            print("Enter new item name : ", printType);
            task.setItem(index-1, scanner.nextLine());
        }
    }

    public String getItem(ItemsTaskList task){
        StringBuilder stringBuilderItem = new StringBuilder() ;
        int index = 1;
        for (ItemTask itemTask : task.getItems()){
            stringBuilderItem.append(index + "_ ");
            stringBuilderItem.append(itemTask);
        }
        return stringBuilderItem.toString();
    }

    public String getDisplayEditMenuForSimpleTask(){
        StringBuilder stringDisplayEditMenuSimpleTask = new StringBuilder() ;
        stringDisplayEditMenuSimpleTask.append("\nChoose option : ");
        stringDisplayEditMenuSimpleTask.append("\n");
        stringDisplayEditMenuSimpleTask.append(EDIT_TITLE + "- Edit title.");
        stringDisplayEditMenuSimpleTask.append('\n');
        stringDisplayEditMenuSimpleTask.append(EDIT_DESCRIPTION + "- Edit description.");
        stringDisplayEditMenuSimpleTask.append('\n');
        stringDisplayEditMenuSimpleTask.append(EDIT_TAG + "- Edit tag.");
        stringDisplayEditMenuSimpleTask.append('\n');
        stringDisplayEditMenuSimpleTask.append(SAVE_TASKS + "- Save tasks.");
        stringDisplayEditMenuSimpleTask.append('\n');
        stringDisplayEditMenuSimpleTask.append(BACK + "- Back.");
        stringDisplayEditMenuSimpleTask.append('\n');

        return stringDisplayEditMenuSimpleTask.toString();
    }

    public String getDisplayEditMenuForTimedTask(){
        StringBuilder stringDisplayEditMenuTimeTask = new StringBuilder() ;
        stringDisplayEditMenuTimeTask.append("\nChoose option : ");
        stringDisplayEditMenuTimeTask.append("\n");
        stringDisplayEditMenuTimeTask.append(EDIT_TITLE + "- Edit title.");
        stringDisplayEditMenuTimeTask.append('\n');
        stringDisplayEditMenuTimeTask.append(EDIT_DESCRIPTION + "- Edit description.");
        stringDisplayEditMenuTimeTask.append('\n');
        stringDisplayEditMenuTimeTask.append(EDIT_TAG + "- Edit tag.");
        stringDisplayEditMenuTimeTask.append('\n');
        stringDisplayEditMenuTimeTask.append(EDIT_DATE + "- Edit date.");
        stringDisplayEditMenuTimeTask.append('\n');
        stringDisplayEditMenuTimeTask.append(SAVE_TASKS + "- Save tasks.");
        stringDisplayEditMenuTimeTask.append('\n');
        stringDisplayEditMenuTimeTask.append(BACK + "- Back.");
        stringDisplayEditMenuTimeTask.append('\n');

        return stringDisplayEditMenuTimeTask.toString();
    }

    public String getDisplayEditMenuForItemsTask(){
        StringBuilder stringDisplayEditMenuItemsTask = new StringBuilder();
        stringDisplayEditMenuItemsTask.append("\nChoose option : ");
        stringDisplayEditMenuItemsTask.append("\n");
        stringDisplayEditMenuItemsTask.append(EDIT_TITLE + "- Edit title.");
        stringDisplayEditMenuItemsTask.append('\n');
        stringDisplayEditMenuItemsTask.append(EDIT_DESCRIPTION + "- Edit description.");
        stringDisplayEditMenuItemsTask.append('\n');
        stringDisplayEditMenuItemsTask.append(EDIT_TAG + "- Edit tag.");
        stringDisplayEditMenuItemsTask.append('\n');
        stringDisplayEditMenuItemsTask.append(EDIT_ITEMS + "- Edit items.");
        stringDisplayEditMenuItemsTask.append('\n');
        stringDisplayEditMenuItemsTask.append(SAVE_TASKS + "- Save tasks.");
        stringDisplayEditMenuItemsTask.append('\n');
        stringDisplayEditMenuItemsTask.append(BACK + "- Back.");
        stringDisplayEditMenuItemsTask.append('\n');

        return stringDisplayEditMenuItemsTask.toString();
    }

    public String getDisplayMenu(){
        StringBuilder stringBuilderDisplayMenu = new StringBuilder() ;

        stringBuilderDisplayMenu.append("\nEnter option number + SPACE + task number :");
        stringBuilderDisplayMenu.append("\n");
        stringBuilderDisplayMenu.append(DISPLAY_DETAILS_TASK + "- Display details task.");
        stringBuilderDisplayMenu.append('\n');
        stringBuilderDisplayMenu.append(DO_TASK + "- Do task.");
        stringBuilderDisplayMenu.append('\n');
        stringBuilderDisplayMenu.append(EDIT_TASK + "- Edit task.");
        stringBuilderDisplayMenu.append('\n');
        stringBuilderDisplayMenu.append(REMOVE_TASK + "- Remove task.");
        stringBuilderDisplayMenu.append('\n');
        stringBuilderDisplayMenu.append(SAVE_TASKS + "- Save tasks.");
        stringBuilderDisplayMenu.append('\n');
        stringBuilderDisplayMenu.append(BACK + "- Back.");
        stringBuilderDisplayMenu.append('\n');

        return stringBuilderDisplayMenu.toString();
    }


    void save(TaskManager taskManager, AccountModel accountModel){
        FileManager fileManager = new FileManager(taskManager, accountModel);
        fileManager.save();
    }

    void print (Object object, PrintStream printStream){
        printStream.println(object);
    }

    void print (int num, Object object, PrintStream printStream){
        printStream.print(num + "- ");
        printStream.println(object);
    }
}
