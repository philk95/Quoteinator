package de.die.dudes.quoteinator.model;

import java.io.Serializable;
import java.util.Calendar;

public class Quotation implements Serializable {

    /**
     *
     */

    private static final long serialVersionUID = -5253895370673051649L;
    private int id;
    private Module module;
    private Calendar date;
    private String text;

    public Quotation(int id, Module module, Calendar date, String text) {
        setId(id);
        setModul(module);
        setDate(date);
        setText(text);
    }

    private void setId(int id) {
        this.id = id;
    }

    public Quotation(Module module, Calendar date, String text) {
        setModul(module);
        setDate(date);
        setText(text);
    }

    public Module getModul() {
        return module;
    }

    public void setModul(Module module) {
        this.module = module;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(date).append(":").append(getModul()).append("\n")
                .append(getText());
        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + id;
        result = prime * result + ((module == null) ? 0 : module.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Quotation other = (Quotation) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (id != other.id)
            return false;
        if (module == null) {
            if (other.module != null)
                return false;
        } else if (!module.equals(other.module))
            return false;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        return true;
    }

    public int getID() {
        return this.id;
    }
}