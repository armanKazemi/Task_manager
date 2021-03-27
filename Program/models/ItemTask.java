package Program.models;

import java.io.Serializable;

public class ItemTask implements Serializable {
    private String nameItem;
    private boolean situation = false;

    public ItemTask (){
        this.situation = false;
    }

    public ItemTask (String nameTaskItem, boolean situation){
        this.nameItem = nameTaskItem;
        this.situation = situation;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public void done() {
        situation = true;
    }

    public String getNameItem() {
        return nameItem;
    }

    public boolean isSituation() {
        return situation;
    }

    @Override
    public String toString() {
        return "{" +
                "Name item = '" + nameItem + '\'' +
                ", Situation = " + situation +
                "}\n\t";
    }
}
