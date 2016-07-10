package de.die.dudes.quoteinator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by dirk on 25.06.2016.
 */
public class SqlDatabase extends SQLiteOpenHelper implements IDatabase {

    private static final String DB_NAME = "quotesDB";
    private static final int DB_VERSION = 1;


    //Table names
    private static final String TABLE_DOCENT = "docent";
    private static final String TABLE_MODULE = "module";
    private static final String TABLE_QUOTE = "quote";

    // Docent Column
    private static final String KEY_DOCENT_ID = "docent_id";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNMAE = "lastname";

    //Module Column
    private static final String KEY_MODULE_ID = "module_id";
    private static final String KEY_NAME = "name";

    //Qoute Colum
    private static final String KEY_QUOTE_ID = "quote_id";
    private static final String KEY_DATE = "date";
    private static final String KEY_TEXT = "text";


    //Docent Table create
    private static final String CREATE_TABLE_DOCENT = "CREATE TABLE " + TABLE_DOCENT +
            " (" + KEY_DOCENT_ID + " INTEGER PRIMARY KEY,"
            + KEY_FIRSTNAME + " TEXT," + KEY_LASTNMAE + " TEXT)";

    //Module Table create
    private static final String CREATE_TABLE_MODULE = "CREATE TABLE " + TABLE_MODULE +
            " (" + KEY_MODULE_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEST," + KEY_DOCENT_ID + " INTEGER)";

    //Quote Table creates
    private static final String CREATE_TABLE_QUOTE = "CREATE TABLE " + TABLE_QUOTE + " (" + KEY_QUOTE_ID + " INTEGER PRIMARY KEY,"
            + KEY_DATE + " DATETIME, " + KEY_TEXT + " TEXT, " + KEY_MODULE_ID + " INTEGER)";

    SqlDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.e("+++++++++++++", "SqlDatabase cstr");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("++++++++++++", "onCreate");
        db.execSQL(CREATE_TABLE_DOCENT);
        db.execSQL(CREATE_TABLE_MODULE);
        db.execSQL(CREATE_TABLE_QUOTE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    @Override
    public ArrayList<Docent> getDocents() {
        ArrayList<Docent> docents = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_DOCENT;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
        }

        while (!c.isLast()) {

        }
        return null;
    }

    @Override
    public Docent getDocent(int id) {
        return null;
    }

    @Override
    public boolean addDocent(Docent docent) {
        return false;
    }

    @Override
    public boolean removeDocent(int id) {
        return false;
    }

    @Override
    public boolean updateDocent(int id, Docent docent) {
        return false;
    }

    @Override
    public ArrayList<Module> getModules() {
        ArrayList<Module> modules = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String select = "SELECT * FROM " + TABLE_MODULE;

        Cursor c = db.rawQuery(select, null);

        c.moveToFirst();
        do{
            int module_id = c.getInt(c.getColumnIndex(KEY_MODULE_ID));
            String name = c.getString(c.getColumnIndex(KEY_NAME));
            int docent_id = c.getInt(c.getColumnIndex(KEY_DOCENT_ID));
            Module m = new Module(name,module_id,docent_id);
            modules.add(m);
        }while(c.moveToNext());

        return modules;
    }

    @Override
    public Module getModule(int id) {
        return null;
    }

    @Override
    public boolean addModule(Module module) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, module.getName());
        values.put(KEY_DOCENT_ID, module.getDocent_id());

        db.insert(TABLE_MODULE, null, values);

        return true;
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
        return null;
    }

    @Override
    public Quotation getQuote(int id) {
        return null;
    }

    @Override
    public boolean addQuote(Quotation quote) {
        return false;
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
        return null;
    }

    @Override
    public ArrayList<Quotation> getQuotesByDocent(int id) {
        return null;
    }

    @Override
    public Docent getDocentByModule(int id) {
        return null;
    }
}
