package de.die.dudes.quoteinator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by dirk on 25.06.2016.
 */
public class MockDB implements IDatabase {

    private static ArrayList<Docent> docents = new ArrayList<>();
    private static ArrayList<Module> modules = new ArrayList();
    private static ArrayList<Quotation> quotes = new ArrayList<>();

    MockDB() {
        Docent docent = null;
        for (int i = 0; i < 10; i++) {
            docent = new Docent("Dozent" + i);
            docents.add(docent);
        }
        Module module = null;
        for (int i = 0; i < 10; i++) {
            module = new Module("Modul" + i);
            modules.add(module);
        }

        Quotation quote = null;
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            quote = new Quotation(r.nextInt(9), Calendar.getInstance(), "Zitat" + i);
            quotes.add(quote);
        }
    }

    @Override
    public ArrayList<Docent> getDocents() {
        return docents;
    }

    @Override
    public Docent getDocent(int id) {
        return docents.get(0);
    }

    @Override
    public boolean addDocent(Docent docent) {
        docents.add(docent);
        return true;
    }

    @Override
    public boolean removeDocent(int id) {
        docents.remove(0);
        return true;
    }

    @Override
    public boolean updateDocent(int id, Docent docent) {
        return true;
    }

    @Override
    public ArrayList<Module> getModules() {
        return modules;
    }

    @Override
    public Module getModule(int id) {
        return modules.get(0);
    }

    @Override
    public boolean addModule(Module module) {
        return modules.add(module);
    }

    @Override
    public boolean removeModule(int id) {
        return false;
    }

    @Override
    public boolean updateModule(int id, Module module) {
        return false;
    }

    @Override
    public ArrayList<Quotation> getQuotes() {
        return quotes;
    }

    @Override
    public Quotation getQuote(int id) {
        return quotes.get(0);
    }

    @Override
    public boolean addQuote(Quotation quote) {
        return quotes.add(quote);
    }

    @Override
    public boolean removeQuote(int id) {
        return false;
    }

    @Override
    public boolean updateQuote(int id, Quotation quote) {
        return false;
    }

    @Override
    public ArrayList<Quotation> getQuotesByModule(int id) {
        return quotes;
    }

    @Override
    public ArrayList<Quotation> getQuotesByDocent(int id) {
        return quotes;
    }

    @Override
    public Docent getDocentByModule(int id) {
        return docents.get(0);
    }
}
