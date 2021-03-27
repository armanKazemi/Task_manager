package Program;

import Program.models.ItemTask;
import Program.models.ItemsTaskList;
import Program.models.SimpleTask;
import Program.models.TimeTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class TaskManager implements Serializable {
    private ArrayList<SimpleTask> tasks = new ArrayList<>();

    public TaskManager(){
    }


    public SimpleTask newSimpleTask (String title, String description){
        return new SimpleTask(title, description);
    }

    public SimpleTask newSimpleTask (String title, String description, String tag){
        return new SimpleTask(title, description, tag);
    }



    public TimeTask newTimeTask (String title, String description){
        return new TimeTask(title, description);
    }

    public TimeTask newTimeTask (String title, String description, String tag){
        return new TimeTask(title, description, tag);
    }

    public TimeTask newTimeTask (String title, String description, Date date){
        return new TimeTask(title, description, date);
    }

    public TimeTask newTimeTask (String title, String description, Date date, String tag){
        return new TimeTask(title, description, tag, date);
    }



    public ItemsTaskList newItemTaskList (String title, String description){
        return new ItemsTaskList(title, description);
    }

    public ItemsTaskList newItemTaskList (String title, String description, String tag){
        return new ItemsTaskList(title, description, tag);
    }

    public ItemsTaskList newItemTaskList (String title, String description, ArrayList<ItemTask> itemTasks){
        return new ItemsTaskList(title, description, itemTasks);
    }

    public ItemsTaskList newItemTaskList (String title, String description, ArrayList<ItemTask> itemTasks, String tag){
        return new ItemsTaskList(title, description, tag, itemTasks);
    }



    public void newTask (SimpleTask task){
        tasks.add(task);
    }

    public void newTasks (ArrayList<SimpleTask> tasks){
        this.tasks.addAll(tasks);
    }

    public void removeTask (int index){
        tasks.remove(index);
    }

    public void removeTask (SimpleTask task){
        tasks.remove(task);
    }

    public void done (int index){
        tasks.get(index).done();
    }

    public void done (SimpleTask task){
        task.done();
    }

    public <T extends SimpleTask> SimpleTask getTask (int index){
        return (T) tasks.get(index);
    }

    public ArrayList<SimpleTask> getTasks () {
        return tasks;
    }
}
