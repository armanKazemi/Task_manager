package Program.models;

import java.io.Serializable;
import java.util.Date;

public class TimeTask extends SimpleTask implements Serializable {
    private Date date;

    public TimeTask (String title, String description){
        super(title, description);
    }

    public TimeTask (String title, String description, String tag){
        super(title, description, tag);
    }

    public TimeTask (String title, String description, Date date){
        super(title, description);
        this.date = date;
    }

    public TimeTask (String title, String description, String tag, Date date){
        super(title, description, tag);
        this.date = date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Time Task {" +
                "Title = '" + getTitle() + '\'' +
                ", Tag = " + getTag() +
                ", Description = '" + getDescription() + '\'' +
                ", Is done = " + isDoneOrNot() +
                ", Date = " + date +
                '}';
    }
}
