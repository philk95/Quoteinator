package de.die.dudes.quoteinator;

import java.util.ArrayList;

/**
 * Created by dirk on 25.06.2016.
 */
public interface IDatabase {

    public ArrayList<Docent> getDocents();
    public Docent getDocent(int id);
    public boolean addDocent(Docent docent);
    public boolean removeDocent(int id);
    public boolean updateDocent(int id, Docent docent);


    public ArrayList<Module> getModules();
    public Module getModule(int id);
    public boolean addModule(Module module);
    public boolean removeModule(int id);
    public boolean updateModule(int id, Module module);

    public ArrayList<Quotation> getQuotes();
    public Quotation getQuote(int id);
    public boolean addQuote(Quotation quote);
    public boolean removeQuote(int id);
    public boolean updateQuote(int id, Quotation quote);

    public ArrayList<Quotation> getQuotesByModule(int id);
    public ArrayList<Quotation> getQuotesByDocent(int id);
    public Docent getDocentByModule(int id);
}
