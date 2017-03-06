package de.thelp1987.noteapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by thelp1987 on 04.03.2017.
 * DB Helper for managing stored notes data with SQLite (as internal source)
 */
public class DBHelper extends SQLiteOpenHelper {
    /* .: PREDEFINITIONS --- START :. */
    public final static String DATABASE_NAME = "FavendoNoteApp";
    public final static String NOTES_TABLE_NAME = "notes";
    public final static String NOTES_COLUMN_ID = "id";
    public final static String NOTES_COLUMN_TITLE = "title";
    public final static String NOTES_COLUMN_CONTENT = "content";
    public final static String NOTES_GMAPS_LINK = "gmapsLink";
    /* .: PREDEFINTIONS --- END :. */


    /**
     * Constructor<br>
     * Create DB, managed by System<br>
     * Location: /data/data/de.thelp1987.favendonoteapp/databasesnotes<br>
     * @param context {@link Context}
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * Startup initializer<br>
     * Create DB if not exist (1st start of the app).<br>
     *     <quote>onCreate() is only run when the database file did not exist and was just created. If onCreate() returns successfully (doesn't throw an exception), the database is assumed to be created with the requested version number. As an implication, you should not catch SQLExceptions in onCreate() yourself.<br>
     *         Source: http://stackoverflow.com/questions/21881992/when-is-sqliteopenhelper-oncreate-onupgrade-run</quote>
     * <br>
     * @param db {@link SQLiteDatabase}
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + NOTES_TABLE_NAME + " (" +
                NOTES_COLUMN_ID + " integer primary key, " +    // NOTE: this order is like an auto_increment, same like ROW_ID
                                                                // NO Use of explicit auto_increment function
                NOTES_COLUMN_TITLE + " text, " +
                NOTES_COLUMN_CONTENT + " text, " +
                NOTES_GMAPS_LINK + " text" + " )"
        );
    }

    /**
     * Delete predicated table.
     * <quote> is only called when the database file exists but the stored version number is lower than requested in constructor. The onUpgrade() should update the table schema to the requested version.<br>
     *     Source: http://stackoverflow.com/questions/21881992/when-is-sqliteopenhelper-oncreate-onupgrade-run</quote>
     * <br>
     *     NOTE: This case doesn't proc anytime at the moment, because the class constructor don't have the parameter for this. (static param)
     * @param db {@link SQLiteDatabase}
     * @param oldVersion as primitive class int
     * @param newVersion as primitive class int
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE_NAME);
    }

    // Managing DB Data

    /*
    SQLite Autoincrement:
        On an INSERT, if the ROWID or INTEGER PRIMARY KEY column is not explicitly given a value, then it will
        be filled automatically with an unused integer, usually one more than the largest ROWID currently in use.
        This is true regardless of whether or not the AUTOINCREMENT keyword is used.
     Source: http://sqlite.org/autoinc.html
     */

    /**
     * Method to insert a new note into the DB.<br>
     * @param title {@link String}
     * @param content {@link String}
     * @param gmaps {@link String}
     * @return true or false (boolean)
     */
    public boolean insertNote(String title, String content, String gmaps){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = setContentValues(title, content, gmaps);

        db.insert(NOTES_TABLE_NAME, null, contentValues);

        return true;
    }

    /**
     * Search and return one data from the DB (if the data exist)<br>
     * Search record with <i>ROWID</i>.<br>
     * @param i as primitive class int for ROW_ID matching
     * @return {@link Cursor} or if data not exist: null
     */
    public Cursor getData(int i){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+NOTES_TABLE_NAME+" where "+NOTES_COLUMN_ID+" = "+i, null);

        return cursor;
    }

    /**
     * Overloaded method. Same describe like the int variant<br>
     * Search record with note title.<br>
     * Attention!: No unique check on column title
     * @param i as {@link String}
     * @return
     */
    public Cursor getData(String noteTitle){
        //TODO UNIQUE CHECK MISSING
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+NOTES_TABLE_NAME+" where "+NOTES_COLUMN_TITLE+" = '"+noteTitle+"'", null);
        return cursor;
    }

    /**
     * Return the max records from the table in the db<br>
     * @return the number as primitive int class
     */
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, NOTES_TABLE_NAME);

        return numRows;
    }

    /**
     * Edit an existing record (note) in the db.<br>
     * @param id {@link Integer}
     * @param title {@link String}
     * @param content {@link String}
     * @param gmaps {@link String}
     * @return true or false
     */
    public boolean updateNote(Integer id, String title, String content, String gmaps){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = setContentValues(title, content, gmaps);

        db.update(
                NOTES_TABLE_NAME,
                contentValues,
                NOTES_COLUMN_ID+" = ?",
                new String[] {Integer.toString(id)}
        );

        return true;
    }

    /**
     * Delete one record from the db.<br>
     * @param id {@link Integer}
     * @return {@link Integer}
     */
    public Integer deleteContact (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(
                NOTES_TABLE_NAME,
                NOTES_COLUMN_ID+" = ?",
                new String[] {Integer.toString(id)}
        );
    }

    /**
     * Read the whole db and give all records title as {@link java.util.ArrayList}<{@link String}> back.<br>
     * @return {@link java.util.ArrayList}<{@link String}>
     */
    public ArrayList<String> getAllNotesTitle(){
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from "+NOTES_TABLE_NAME, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            arrayList.add(cursor.getString(
                                cursor.getColumnIndex(NOTES_COLUMN_TITLE)
            ));

            cursor.moveToNext();
        }
        return arrayList;
    }

    /**
     * Subroutine to setting content values for raw sql statements (SQLite).<br>
     * @param title {@link String}
     * @param content {@link String}
     * @param gmaps {@link String}
     * @return {@link ContentValues}
     */
    private ContentValues setContentValues(String title, String content, String gmaps){
        ContentValues contentValues = new ContentValues();

        contentValues.put(NOTES_COLUMN_TITLE, title);
        contentValues.put(NOTES_COLUMN_CONTENT, content);
        contentValues.put(NOTES_GMAPS_LINK, gmaps);

        return contentValues;
    }
}