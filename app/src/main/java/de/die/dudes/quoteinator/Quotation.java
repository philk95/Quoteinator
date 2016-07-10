package de.die.dudes.quoteinator;

import java.io.Serializable;
import java.util.Calendar;

public class Quotation implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quotation quotation = (Quotation) o;

        if (id != quotation.id) return false;
        if (modul != quotation.modul) return false;
        if (date != null ? !date.equals(quotation.date) : quotation.date != null) return false;
        return text != null ? text.equals(quotation.text) : quotation.text == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + modul;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    /**
     *
     */

    private static final long serialVersionUID = -5253895370673051649L;
    private int id;
    private int modul;
    private Calendar date;
    private String text;

    public Quotation(int modul, Calendar date, String text) {
        setModul(modul);
        setDate(date);
        setText(text);
    }

    public int getModul() {
        return modul;
    }

    public void setModul(int modul) {
        this.modul = modul;
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
        sb.append(Util.getStringOfDate(date)).append(":").append(getModul())
                .append("\n").append(getText());
        return sb.toString();
    }
}
