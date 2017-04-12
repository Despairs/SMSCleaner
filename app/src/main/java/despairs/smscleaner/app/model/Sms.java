package despairs.smscleaner.app.model;

import java.util.Date;

/**
 * Created by Home on 10.04.2017.
 */

public class Sms {
    private Long id;
    private String address;
    private String mappedAddress;
    private String text;
    private Date date;

    public Sms() {

    }

    public Sms(Long id, String address, String text, Date date) {
        this.id = id;
        this.address = address;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMappedAddress() {
        return mappedAddress;
    }

    public void setMappedAddress(String mappedAddress) {
        this.mappedAddress = mappedAddress;
    }
}
