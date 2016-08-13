package de.die.dudes.quoteinator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

import de.die.dudes.quoteinator.model.Docent;
import de.die.dudes.quoteinator.model.Module;
import de.die.dudes.quoteinator.model.Quotation;

/**
 * Created by dirk on 25.06.2016.
 */
public class SqlDatabase extends SQLiteOpenHelper implements IDatabase {

    private static final String DB_NAME = "quotesDB";
    private static final int DB_VERSION = 1;


    // Table names
    private static final String TABLE_DOCENT = "docent";
    private static final String TABLE_MODULE = "module";
    private static final String TABLE_QUOTATION = "quotation";

    // Docent Column
    private static final String DOCENT_DOCENT_ID = "_id";
    // private static final String DOCENT_FIRSTNAME = "firstname";
    private static final String DOCENT_LASTNMAE = "lastname";

    // Module Column
    private static final String MODULE_MODULE_ID = "_id";
    private static final String MODULE_NAME = "name";
    private static final String MODULE_DOCENT_ID = "docent_id";

    // Qoute Colum
    private static final String QUOTATION_QUOTATION_ID = "_id";
    private static final String QUOTATION_DATE = "date";
    private static final String QUOTATION_TEXT = "text";
    private static final String QUOTATION_MODULE_ID = "module_id";

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
        super(context, null, null, DB_VERSION);
        //// TODO: 05.08.2016 DB ist momentan temporär. Namen hinzufügen um es zu ändern.
        Log.e("+++++++++++++", "SqlDatabase cstr");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("++++++++++++", "onCreate");
        updateMyDatabase(db, 0, DB_VERSION);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CREATE_TABLE_DOCENT);
        db.execSQL(CREATE_TABLE_MODULE);
        db.execSQL(CREATE_TABLE_QUOTATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    @Override
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
    }

    @Override
    public Cursor getDocentsCursor() {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(TABLE_DOCENT, new String[]{DOCENT_DOCENT_ID, DOCENT_LASTNMAE}, null, null, null, null, null);
    }

    @Override
    public Docent getDocent(int id) {
        Docent docent = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DOCENT, new String[]{DOCENT_DOCENT_ID, DOCENT_LASTNMAE}, DOCENT_DOCENT_ID + " =  ?", new String[]{Integer.toString(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
            String name = cursor.getString(1);
            docent = new Docent(id, name);
        }

        return docent;
    }

    @Override
    public boolean addDocent(Docent docent) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues docentValues = new ContentValues();
        docentValues.put(DOCENT_LASTNMAE, docent.getName());

        long newID = db.insert(TABLE_DOCENT, null, docentValues);

        return newID != -1;
    }

    @Override
    public boolean removeDocent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int affectedRows = db.delete(TABLE_DOCENT, DOCENT_DOCENT_ID + " = ?", new String[]{Integer.toString(id)});

        return affectedRows > 0;
    }

    @Override
    public boolean updateDocent(int id, Docent docent) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues docentValues = new ContentValues();
        docentValues.put(DOCENT_LASTNMAE, docent.getName());

        int affectedRows = db.update(TABLE_DOCENT, docentValues, DOCENT_DOCENT_ID + " = ?", new String[]{Integer.toString(id)});
        return affectedRows > 0;
    }

    @Override
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

    @Override
    public Cursor getModulesCursor() {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(TABLE_MODULE, new String[]{MODULE_MODULE_ID, MODULE_NAME, MODULE_DOCENT_ID}, null, null, null, null, null);
    }

    @Override
    public Module getModule(int id) {
        Module module = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MODULE, new String[]{MODULE_MODULE_ID, MODULE_NAME, MODULE_DOCENT_ID}, MODULE_MODULE_ID + " =  ?", new String[]{Integer.toString(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
            String name = cursor.getString(1);
            Docent docent = getDocent(cursor.getInt(2));
            module = new Module(id, name, docent);
        }

        return module;
    }

    @Override
    public boolean addModule(Module module) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues moduleValues = new ContentValues();
        moduleValues.put(MODULE_DOCENT_ID, module.getDocent().getId());
        moduleValues.put(MODULE_NAME, module.getName());

        long newID = db.insert(TABLE_MODULE, null, moduleValues);

        return newID != -1;
    }

    @Override
    public boolean removeModule(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int affectedRows = db.delete(TABLE_MODULE, MODULE_MODULE_ID + " = ?", new String[]{Integer.toString(id)});

        return affectedRows > 0;
    }

    @Override
    public boolean updateModule(int id, Module module) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues moduleValues = new ContentValues();
        moduleValues.put(MODULE_DOCENT_ID, module.getDocent().getId());
        moduleValues.put(MODULE_NAME, module.getName());

        int affectedRows = db.update(TABLE_MODULE, moduleValues, MODULE_MODULE_ID + " = ?", new String[]{Integer.toString(id)});
        return affectedRows > 0;
    }

    @Override
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
    }

    @Override
    public Cursor getQuotationsCursor() {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(TABLE_QUOTATION, new String[]{QUOTATION_QUOTATION_ID, QUOTATION_TEXT, QUOTATION_DATE, QUOTATION_MODULE_ID}, null, null, null, null, null);
    }

    @Override
    public Quotation getQuotation(int id) {
        Quotation quotation = null;
        SQLiteDatabase db = this.getReadableDatabase();

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
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues quotationValues = new ContentValues();
        quotationValues.put(QUOTATION_MODULE_ID, quote.getModul().getID());
        quotationValues.put(QUOTATION_TEXT, quote.getText());
        quotationValues.put(QUOTATION_DATE, Util.format(quote.getDate()));

        long newID = db.insert(TABLE_QUOTATION, null, quotationValues);

        return newID != -1;
    }

    @Override
    public boolean removeQuotation(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int affectedRows = db.delete(TABLE_QUOTATION, QUOTATION_QUOTATION_ID + " = ?", new String[]{Integer.toString(id)});

        return affectedRows > 0;
    }

    @Override
    public boolean updateQuotation(int id, Quotation quote) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues quotationValues = new ContentValues();
        quotationValues.put(QUOTATION_MODULE_ID, quote.getModul().getID());
        quotationValues.put(QUOTATION_TEXT, quote.getText());
        quotationValues.put(QUOTATION_DATE, Util.format(quote.getDate()));

        int affectedRows = db.update(TABLE_QUOTATION, quotationValues, QUOTATION_QUOTATION_ID + " = ?", new String[]{Integer.toString(id)});
        return affectedRows > 0;
    }

    @Override
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
    }

    @Override
    public Cursor getQuotationsCursorByModule(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(TABLE_QUOTATION, new String[]{QUOTATION_QUOTATION_ID, QUOTATION_TEXT, QUOTATION_DATE, QUOTATION_MODULE_ID}, QUOTATION_MODULE_ID + " = ? ", new String[]{Integer.toString(id)}, null, null, null);
    }

    @Override
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

    @Override
    public Cursor getQuotationsCursorByDocent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT q._id, q.module_id, q.date, q.text FROM quotation as q INNER JOIN module ON q.module_id = module._id WHERE module.docent_id = ?";
        return db.rawQuery(sql, new String[]{Integer.toString(id)});
    }


}
