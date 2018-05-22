package manik.com.mysql.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by manikkam on 1/2/18.
 */

public class DBHandler extends SQLiteOpenHelper {

    private final static String TAG="ConnectDB";
    // All Static variables

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "emp";

    // Contacts table name
    private static final String TABLE_NAME= "stuinfo";
    private static final String TABLE_NAME1= "classinfo";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CLASS_TABLE="create table classinfo (id INTEGER PRIMARY KEY AUTOINCREMENT,classname TEXT)";
        String STU_TABLE="create table stuinfo (id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT," +
                "fathername TEXT,dob TEXT,address TEXT,classname TEXT,classstatus TEXT)";
        db.execSQL(CLASS_TABLE);
        db.execSQL(STU_TABLE);
        Log.d(TAG,"Table Create Successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);

    }

    public void insertClass(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("classname", name);
        db.insert(TABLE_NAME1, null, values);
    }

    // Adding new contact
    public void insertStudent(String name, String fatherName, String dob,String address,String className,String status) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", name);
        values.put("fathername", fatherName);
        values.put("dob", dob);
        values.put("address", address);
        values.put("classname", className);
        values.put("classstatus", status);

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
        Log.d(TAG,"Insert Success," + "Name:"+name);
    }


    public void removeStudent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " where id=" + id +"");
        db.close();
        Log.d(TAG,"Saved Form Deleted");
    }

    public void updateStudent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("update stuinfo set classstatus='Active' where id=" + id +"");
        db.close();
        Log.d(TAG,"Saved Form Deleted");
    }

    public String getStatus(String classname) {
        String selectQuery = "SELECT id FROM stuinfo"+ " where classname='" + classname + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                if (cursor.getCount()<2) {
                    return "Active";
                } else {
                    return "Waiting";
                }
            }
        }
        return "Active";
    }

    public Cursor getClassDetails() {
        String selectQuery = "SELECT id,classname FROM classinfo order by id";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public int getClassDetail() {
        String selectQuery = "SELECT id,classname FROM classinfo order by id";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor.getCount();
    }

    public Cursor getStudentDetails(String classname) {
        String selectQuery = "SELECT id,username,fathername,dob,address,classname,classstatus FROM stuinfo where classname='" + classname + "' order by id";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor getUpdateStudentDetails(String classname) {
        String selectQuery = "SELECT id,username,fathername,dob,address,classname,classstatus FROM stuinfo where classstatus='Waiting' and classname='" + classname + "' order by id";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

}
