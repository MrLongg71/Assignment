package vn.mrlongg71.assignment.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class Database extends SQLiteOpenHelper {


    public Database(  Context context, String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void INSERT_USER(String user, String pass,String name, String place, String phone){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO User VALUES(null, ?, ?,?,?,?)";
        SQLiteStatement statement =  database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, user);
        statement.bindString(2, pass);
        statement.bindString(3,name);
        statement.bindString(4, place);
        statement.bindString(5, phone);
        statement.executeInsert();

    }
    //insert class
    public void INSERT_CLASS(String malop,String tenlop, int iduser){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Class VALUES(null, ?, ? ,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, malop);
        statement.bindString(2,tenlop);
        statement.bindString(3, iduser + "");
        statement.executeInsert();
    }
    //insert SV
    public void INSERT_SV(String tenSV,String date,int idclass ,int iduser, String tenlop, String sdt, String email,String place, byte[] images ){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Students VALUES(null, ?, ? ,?,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, tenSV);
        statement.bindString(2,date);
        statement.bindString(3, idclass + "");
        statement.bindString(4, iduser + "");
        statement.bindString(5, tenlop);
        statement.bindString(6, sdt);
        statement.bindString(7, email);
        statement.bindString(8, place);
        statement.bindBlob(9, images);

        statement.executeInsert();
    }
    //Truy vấn database
    public void QueryData(String sql){
       SQLiteDatabase database = getWritableDatabase();
       database.execSQL(sql);
    }
    //Lấy database
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }


}
