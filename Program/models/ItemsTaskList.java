package Program.models;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemsTaskList extends SimpleTask implements Serializable {
    private ArrayList<ItemTask> items = new ArrayList<>();

    public ItemsTaskList (String title, String description){
        super(title, description);
    }

    public ItemsTaskList (String title, String description, String tag){
        super(title, description, tag);
    }

    public ItemsTaskList (String title, String description, ArrayList<ItemTask> itemTasks){
        super(title, description);
        items = itemTasks;
    }

    public ItemsTaskList (String title, String description, String tag, ArrayList<ItemTask> itemTasks){
        super(title, description, tag);
        items = itemTasks;
    }

    public void addItemToTaskList (String nameItem){
        ItemTask itemTask = new ItemTask(nameItem, false);
        items.add(itemTask);
    }

    public void addItems (ArrayList<ItemTask> items) {
        this.items = items;
    }

    public void doneItem (String nameItem){
        for (ItemTask itemTasks : items){
            if (itemTasks.getNameItem().equals(nameItem)){
                itemTasks.done();
            }
        }
    }

    public void setItem(int index, String name){
        items.get(index).setNameItem(name);
    }

    public void removeItem (String nameItem){
        items.removeIf(itemTasks -> itemTasks.getNameItem().equals(nameItem));
    }

    public ArrayList<ItemTask> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return  "Title = '" + getTitle() + '\'' +
                ", Tag = " + getTag() +
                ", Description = '" + getDescription() + '\'' +
                ", Is done = " + isDoneOrNot() +
                ", \n\tItems = \n\t" + items;
    }
}
