package despairs.smscleaner.app.model;

import java.util.Date;

/**
 * Created by Home on 10.04.2017.
 */

public class Sms {
    private Long id;
    private String from;
    private String text;
    private Date date;

    public Sms() {

    }

    public Sms(Long id, String from, String text, Date date) {
        this.id = id;
        this.from = from;
        this.text = text;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
