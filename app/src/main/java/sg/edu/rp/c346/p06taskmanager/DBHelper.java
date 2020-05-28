package sg.edu.rp.c346.p06taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "P06PS.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_DATA = "data";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE Note
        // (id INTEGER PRIMARY KEY AUTOINCREMENT, note_content TEXT, rating
        // INTEGER );
        String createNoteTableSql = "CREATE TABLE " + TABLE_DATA + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT )";

        db.execSQL(createNoteTableSql);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(db);
    }

    public Long insertData(String name, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);

        long result = db.insert(TABLE_DATA, null, values);
        db.close();
        Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1

        return result;
    }



    public ArrayList<Task> getAllData() {
        ArrayList<Task> tasks = new ArrayList<Task>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME + ","
                + COLUMN_DESCRIPTION + "  FROM " + TABLE_DATA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);

                Task song = new Task(id, name,description);
                tasks.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

//    public ArrayList<Song> getSong() {
//        ArrayList<Song> songs = new ArrayList<Song>();
//
//        String selectQuery = "SELECT " + COLUMN_ID + ","
//                + COLUMN_TITLE + ","
//                + COLUMN_SINGERS + ","
//                + COLUMN_YEAR + ","
//                + COLUMN_STARS + "  FROM " + TABLE_SONG + " WHERE " + COLUMN_STARS + " == 5";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        if (cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(0);
//                String title = cursor.getString(1);
//                String singers = cursor.getString(2);
//                int year = cursor.getInt(3);
//                int star = cursor.getInt(4);
//
//                Song song = new Song(id, title,singers,year,star);
//                songs.add(song);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return songs;
//    }
//
//    public int updateSong(Song data){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_TITLE, data.getTitle());
//        values.put(COLUMN_SINGERS, data.getSingers());
//        values.put(COLUMN_YEAR, data.getYear());
//        values.put(COLUMN_STARS, data.getStars());
//
//        String condition = COLUMN_ID + "= ?";
//        String[] args = {String.valueOf(data.getId())};
//        int result = db.update(TABLE_SONG, values, condition, args);
//        db.close();
//        return result;
//    }
//    public int deleteSong(int id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String condition = COLUMN_ID + "= ?";
//        String[] args = {String.valueOf(id)};
//        int result = db.delete(TABLE_SONG, condition, args);
//        db.close();
//        return result;
//    }
//
//    public ArrayList<String> getAllYears(){
//        ArrayList<String> years = new ArrayList<String>();
//        String selectQuery = "SELECT " + COLUMN_YEAR + " FROM " + TABLE_SONG;
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        // moveToFirst() moves to first row
//        if (cursor.moveToFirst()) {
//            // Loop while moveToNext() points to next row and returns true;
//            // moveToNext() returns false when no more next row to move to
//            do {
//                years.add(cursor.getString(0));
//
//            } while (cursor.moveToNext());
//        }
//        // Close connection
//        cursor.close();
//        db.close();
//        return years;
//    }
//
//    public ArrayList<Song> getSongBasedYears(int year){
//        ArrayList<Song> notes = new ArrayList<Song>();
//        String selectQuery = "SELECT " + COLUMN_ID + ","
//                + COLUMN_TITLE + ","
//                + COLUMN_SINGERS + ","
//                + COLUMN_YEAR + ","
//                + COLUMN_STARS + "  FROM " + TABLE_SONG + " WHERE " + COLUMN_YEAR + " == " + year;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        // moveToFirst() moves to first row
//        if (cursor.moveToFirst()) {
//            // Loop while moveToNext() points to next row and returns true;
//            // moveToNext() returns false when no more next row to move to
//            do {
//                int id = cursor.getInt(0);
//                String title = cursor.getString(1);
//                String singers = cursor.getString(2);
//                int star = cursor.getInt(4);
//
//                Song song = new Song(id, title,singers,year,star);
//                notes.add(song);
//
//            } while (cursor.moveToNext());
//        }
//        // Close connection
//        cursor.close();
//        db.close();
//
//        return notes;
//    }
//
//    public ArrayList<Song> getAllSong(String keyword) {
//        ArrayList<Song> notes = new ArrayList<Song>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS, COLUMN_YEAR, COLUMN_STARS};
//        String condition = COLUMN_STARS + " = ?";
//        String[] args = { "%" +  keyword + "%"};
//        Cursor cursor = db.query(TABLE_SONG, columns, condition, args,
//                null, null, null, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(0);
//                String title = cursor.getString(1);
//                String singers = cursor.getString(2);
//                int year = cursor.getInt(3);
//                int star = cursor.getInt(4);
//
//                Song note = new Song(id, title,singers,year,star);
//                notes.add(note);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return notes;
//    }
}
