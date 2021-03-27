package Program.models;

import java.io.Serializable;

public class SimpleTask implements Serializable {
    private String title;
    private String description;
    private boolean doneOrNot = false;
    private String tag;

    public SimpleTask (String title, String description){
        this.title = title;
        this.description = description;
    }

    public SimpleTask (String title, String description, String tag){
        this.title = title;
        this.description = description;
        assert false;
        this.tag = tag;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void done() {
        this.doneOrNot = true;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDoneOrNot() {
        return doneOrNot;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "Simple task {" +
                "Title = '" + title + '\'' +
                ", Tag = " + tag +
                ", Description = '" + description + '\'' +
                ", Is done = " + doneOrNot +
                '}';
    }
}
