package de.die.dudes.quoteinator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import de.die.dudes.quoteinator.database.migration.ConsumeData;
import de.die.dudes.quoteinator.database.migration.ModuleXML;
import de.die.dudes.quoteinator.database.migration.QuotationXML;
import de.die.dudes.quoteinator.database.migration.XMLParsing;
import de.die.dudes.quoteinator.model.Docent;
import de.die.dudes.quoteinator.model.Module;
import de.die.dudes.quoteinator.model.Quotation;

/**
 * Created by dirk on 25.06.2016.
 */
public class SqlDatabase extends SQLiteOpenHelper implements IDatabase {

    private Context context;
    private SQLiteDatabase db;
    private static final String DB_NAME = "quotesDB";
    private static final int DB_VERSION = 1;

    // Table names
    private static final String TABLE_DOCENT = "docent";
    private static final String TABLE_MODULE = "module";
    private static final String TABLE_QUOTATION = "quotation";

    // Docent Column
    public static final String DOCENT_DOCENT_ID = "_id";
    // private static final String DOCENT_FIRSTNAME = "firstname";
    public static final String DOCENT_LASTNMAE = "lastname";

    // Module Column
    public static final String MODULE_MODULE_ID = "_id";
    public static final String MODULE_NAME = "name";
    public static final String MODULE_DOCENT_ID = "docent_id";

    // Qoute Colum
    public static final String QUOTATION_QUOTATION_ID = "_id";
    public static final String QUOTATION_DATE = "date";
    public static final String QUOTATION_TEXT = "text";
    public static final String QUOTATION_MODULE_ID = "module_id";

    // Docent Table create
    private static final String CREATE_TABLE_DOCENT = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT)",
            TABLE_DOCENT, DOCENT_DOCENT_ID, DOCENT_LASTNMAE);

    // Module Table create
    private static final String CREATE_TABLE_MODULE = String
            .format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEST,%s INTEGER,FOREIGN KEY(%s) REFERENCES %s(%s))",
                    TABLE_MODULE, MODULE_MODULE_ID, MODULE_NAME,
                    MODULE_DOCENT_ID, MODULE_DOCENT_ID, TABLE_DOCENT,
                    DOCENT_DOCENT_ID);

    // Quote Table creates
    private static final String CREATE_TABLE_QUOTATION = String
            .format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s INTEGER, FOREIGN KEY(%s) REFERENCES %s(%s))",
                    TABLE_QUOTATION, QUOTATION_QUOTATION_ID, QUOTATION_DATE,
                    QUOTATION_TEXT, QUOTATION_MODULE_ID, QUOTATION_MODULE_ID,
                    TABLE_MODULE, MODULE_MODULE_ID);


    public SqlDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        // super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        this.db = getWritableDatabase();
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        updateMyDatabase(db, 0, DB_VERSION);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion == 0) {
            db.execSQL(CREATE_TABLE_DOCENT);
            db.execSQL(CREATE_TABLE_MODULE);
            db.execSQL(CREATE_TABLE_QUOTATION);

            XMLParsing xmlParsing = new XMLParsing(context);
            try {
                xmlParsing.run(new ConsumeData() {
                    @Override
                    public void consumeDocents(ArrayList<String> docents) {
                        for (String docent : docents) {
                            addDocent(new Docent(docent));
                        }
                    }

                    @Override
                    public void consumeModules(ArrayList<ModuleXML> modules) {
                        for (ModuleXML module : modules) {
                            String name = module.name;
                            Docent docent = getDocent(module.docent);
                            addModule(new Module(name, docent));
                        }
                    }

                    @Override
                    public void consumeQuotations(ArrayList<QuotationXML> quoations) {
                        for (QuotationXML quote : quoations) {
                            String text = quote.text;
                            String date = quote.date + ":09:00:00";
                            Module module = getModule(quote.module);

                            addQuotation(new Quotation(module, Util.parse(date), text));
                        }
                    }
                });
            } catch (XmlPullParserException | IOException e) {
                Log.e("+++++", e.getMessage());
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

   /* @Override
    public ArrayList<Docent> getDocents() {
        ArrayList<Docent> docents = new ArrayList<>();

        Cursor cursor = getDocentsCursor();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                docents.add(new Docent(id, name));
            } while (cursor.moveToNext());
        }

        return docents;
    }*/

    @Override
    public Cursor getDocentsCursor() {
        return db.query(TABLE_DOCENT, new String[]{DOCENT_DOCENT_ID, DOCENT_LASTNMAE}, null, null, null, null, null);
    }

    @Override
    public Docent getDocent(int id) {
        Docent docent = null;
        Cursor cursor = db.query(TABLE_DOCENT, new String[]{DOCENT_DOCENT_ID, DOCENT_LASTNMAE}, DOCENT_DOCENT_ID + " =  ?", new String[]{Integer.toString(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
            String name = cursor.getString(1);
            docent = new Docent(id, name);
        }

        return docent;
    }

    private Docent getDocent(String name) {
        Docent docent = null;
        Cursor cursor = db.query(TABLE_DOCENT, new String[]{DOCENT_DOCENT_ID, DOCENT_LASTNMAE}, DOCENT_LASTNMAE + " =  ?", new String[]{name}, null, null, null);

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String lastname = cursor.getString(1);
            docent = new Docent(id, lastname);
        }

        return docent;
    }

    @Override
    public boolean addDocent(Docent docent) {
        ContentValues docentValues = new ContentValues();
        docentValues.put(DOCENT_LASTNMAE, docent.getName());

        long newID = db.insert(TABLE_DOCENT, null, docentValues);

        return newID != -1;
    }

    @Override
    public boolean removeDocent(int id) {
        int affectedRows = 0;
        try {
            affectedRows = db.delete(TABLE_DOCENT, DOCENT_DOCENT_ID + " = ?", new String[]{Integer.toString(id)});
        } catch (SQLiteConstraintException e) {
            throw e;
        }
        return affectedRows > 0;
    }

    @Override
    public boolean updateDocent(int id, Docent docent) {
        ContentValues docentValues = new ContentValues();
        docentValues.put(DOCENT_LASTNMAE, docent.getName());

        int affectedRows = db.update(TABLE_DOCENT, docentValues, DOCENT_DOCENT_ID + " = ?", new String[]{Integer.toString(id)});
        return affectedRows > 0;
    }

    /*  @Override
      public ArrayList<Module> getModules() {
          ArrayList<Module> modules = new ArrayList<>();

          Cursor cursor = getModulesCursor();

          if (cursor.moveToFirst()) {
              do {
                  int id = cursor.getInt(0);
                  String name = cursor.getString(1);
                  Docent docent = getDocent(cursor.getInt(2));
                  modules.add(new Module(id, name, docent));
              } while (cursor.moveToNext());
          }

          return modules;
      }
  */
    @Override
    public Cursor getModulesCursor() {
        String sql = String.format("SELECT m.%s, m.%s, d.%s FROM %s m INNER JOIN %s d on m.%s = d.%s", MODULE_MODULE_ID, MODULE_NAME, DOCENT_LASTNMAE, TABLE_MODULE, TABLE_DOCENT, MODULE_DOCENT_ID, DOCENT_DOCENT_ID);
        return db.rawQuery(sql, new String[]{});
        //return db.query(TABLE_MODULE, new String[]{MODULE_MODULE_ID, MODULE_NAME, MODULE_DOCENT_ID}, null, null, null, null, null);
    }

    @Override
    public Module getModule(int id) {
        Module module = null;
        Cursor cursor = db.query(TABLE_MODULE, new String[]{MODULE_MODULE_ID, MODULE_NAME, MODULE_DOCENT_ID}, MODULE_MODULE_ID + " =  ?", new String[]{Integer.toString(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
            String name = cursor.getString(1);
            Docent docent = getDocent(cursor.getInt(2));
            module = new Module(id, name, docent);
        }

        return module;
    }

    public Module getModule(String moduleName) {
        Module module = null;
        Cursor cursor = db.query(TABLE_MODULE, new String[]{MODULE_MODULE_ID, MODULE_NAME, MODULE_DOCENT_ID}, MODULE_NAME + " =  ?", new String[]{moduleName}, null, null, null);

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            Docent docent = getDocent(cursor.getInt(2));
            module = new Module(id, name, docent);
        }

        return module;
    }

    @Override
    public boolean addModule(Module module) {
        ContentValues moduleValues = new ContentValues();
        Docent docent = getDocent(module.getDocent().getName());
        if (docent == null) {
            Log.e(getClass().getName(), "Docent not find with name: " + module.getDocent().getName());
        }
        moduleValues.put(MODULE_DOCENT_ID, docent.getId());
        moduleValues.put(MODULE_NAME, module.getName());

        long newID = db.insert(TABLE_MODULE, null, moduleValues);

        return newID != -1;
    }

    @Override
    public boolean removeModule(int id) {
        int affectedRows = 0;

        try {
            affectedRows = db.delete(TABLE_MODULE, MODULE_MODULE_ID + " = ?", new String[]{Integer.toString(id)});
        } catch (SQLiteConstraintException e) {
            throw e;
        }
        return affectedRows > 0;
    }

    @Override
    public boolean updateModule(int id, Module module) {
        ContentValues moduleValues = new ContentValues();
        moduleValues.put(MODULE_DOCENT_ID, module.getDocent().getId());
        moduleValues.put(MODULE_NAME, module.getName());

        int affectedRows = db.update(TABLE_MODULE, moduleValues, MODULE_MODULE_ID + " = ?", new String[]{Integer.toString(id)});
        return affectedRows > 0;
    }

   /* @Override
    public ArrayList<Quotation> getQuotations() {
        ArrayList<Quotation> quotations = new ArrayList<>();

        Cursor cursor = getQuotationsCursor();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String text = cursor.getString(1);
                Calendar calendar = Util.parse(cursor.getString(2));
                Module module = getModule(cursor.getInt(3));
                quotations.add(new Quotation(id, module, calendar, text));
            } while (cursor.moveToNext());
        }

        return quotations;
    }*/

    @Override
    public Cursor getQuotationsCursor() {
        String sql = String.format("SELECT q.%s, q.%s, q.%s, m.%s FROM %s q INNER JOIN %s m on q.%s = m.%s", QUOTATION_QUOTATION_ID, QUOTATION_TEXT, QUOTATION_DATE, MODULE_NAME, TABLE_QUOTATION, TABLE_MODULE, QUOTATION_MODULE_ID, MODULE_MODULE_ID);
        return db.rawQuery(sql, new String[]{});
        //return db.query(TABLE_QUOTATION, new String[]{QUOTATION_QUOTATION_ID, QUOTATION_TEXT, QUOTATION_DATE, QUOTATION_MODULE_ID}, null, null, null, null, null);
    }

    @Override
    public Quotation getQuotation(int id) {
        Quotation quotation = null;
        Cursor cursor = db.query(TABLE_QUOTATION, new String[]{QUOTATION_QUOTATION_ID, QUOTATION_TEXT, QUOTATION_DATE, QUOTATION_MODULE_ID}, QUOTATION_QUOTATION_ID + " =  ?", new String[]{Integer.toString(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
            String text = cursor.getString(1);
            Calendar calendar = Util.parse(cursor.getString(2));
            Module module = getModule(cursor.getInt(3));
            quotation = new Quotation(id, module, calendar, text);
        }

        return quotation;
    }

    @Override
    public boolean addQuotation(Quotation quote) {
        ContentValues quotationValues = new ContentValues();
        quotationValues.put(QUOTATION_MODULE_ID, quote.getModul().getID());
        quotationValues.put(QUOTATION_TEXT, quote.getText());
        quotationValues.put(QUOTATION_DATE, Util.format(quote.getDate()));

        long newID = db.insert(TABLE_QUOTATION, null, quotationValues);

        return newID != -1;
    }

    @Override
    public boolean removeQuotation(int id) {
        int affectedRows = 0;
        try {
            affectedRows = db.delete(TABLE_QUOTATION, QUOTATION_QUOTATION_ID + " = ?", new String[]{Integer.toString(id)});
        } catch (SQLiteConstraintException e) {
            throw e;
        }
        return affectedRows > 0;
    }

    @Override
    public boolean updateQuotation(int id, Quotation quote) {

        ContentValues quotationValues = new ContentValues();
        quotationValues.put(QUOTATION_MODULE_ID, quote.getModul().getID());
        quotationValues.put(QUOTATION_TEXT, quote.getText());
        quotationValues.put(QUOTATION_DATE, Util.format(quote.getDate()));

        int affectedRows = db.update(TABLE_QUOTATION, quotationValues, QUOTATION_QUOTATION_ID + " = ?", new String[]{Integer.toString(id)});
        return affectedRows > 0;
    }

  /*  @Override
    public ArrayList<Quotation> getQuotationsByModule(int id) {
        ArrayList<Quotation> quotations = new ArrayList<>();

        Cursor cursor = getQuotationsCursorByModule(id);

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
                String text = cursor.getString(1);
                Calendar calendar = Util.parse(cursor.getString(2));
                Module module = getModule(cursor.getInt(3));
                quotations.add(new Quotation(id, module, calendar, text));
            } while (cursor.moveToNext());
        }

        return quotations;
    }*/

    @Override
    public Cursor getQuotationsCursorByModule(int id) {
        String sql = String.format("SELECT q.%s, q.%s, q.%s, m.%s FROM %s q INNER JOIN %s m on q.%s = m.%s WHERE q.%s = ?", QUOTATION_QUOTATION_ID, QUOTATION_TEXT, QUOTATION_DATE, MODULE_NAME, TABLE_QUOTATION, TABLE_MODULE, QUOTATION_MODULE_ID, MODULE_MODULE_ID, QUOTATION_MODULE_ID);
        return db.rawQuery(sql, new String[]{Integer.toString(id)});
        // return db.query(TABLE_QUOTATION, new String[]{QUOTATION_QUOTATION_ID, QUOTATION_TEXT, QUOTATION_DATE, QUOTATION_MODULE_ID}, QUOTATION_MODULE_ID + " = ? ", new String[]{Integer.toString(id)}, null, null, null);
    }

    /*   @Override
       public ArrayList<Quotation> getQuotationsByDocent(int id) {
           ArrayList<Quotation> quotations = new ArrayList<>();

           Cursor cursor = getQuotationsCursorByDocent(id);

           if (cursor.moveToFirst()) {
               do {
                   id = cursor.getInt(0);
                   String text = cursor.getString(1);
                   Calendar calendar = Util.parse(cursor.getString(2));
                   Module module = getModule(cursor.getInt(3));
                   quotations.add(new Quotation(id, module, calendar, text));
               } while (cursor.moveToNext());
           }

           return quotations;
       }
   */
    @Override
    public Cursor getQuotationsCursorByDocent(int id) {
        String sql = "SELECT q._id, q.text, q.date, module.name, q.module_id FROM quotation as q INNER JOIN module ON q.module_id = module._id WHERE module.docent_id = ?";
        return db.rawQuery(sql, new String[]{Integer.toString(id)});
    }


}
