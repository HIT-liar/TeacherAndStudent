package com.example.firstpro.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.firstpro.data.Class;
import com.example.firstpro.data.Me;

public class MySQLIteHelper extends SQLiteOpenHelper {

    private static MySQLIteHelper mInstance;
    public static synchronized MySQLIteHelper getInstance(Context context){
        if(mInstance == null){
            mInstance = new MySQLIteHelper(context,"userMessageDB",null,1);
        }
        return mInstance;
    }

    public MySQLIteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String personSql = "create table users(_id integer primary key autoincrement, account text, password text, name text, gender integer, job integer)";


        db.execSQL(personSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void insertUser(Me user){

        SQLiteDatabase db = this.getWritableDatabase();
        if(db.isOpen()) {

            String account = user.getAccount();
            String password = user.getPassword();
            String name =user.getName();
            String gender = "0";
            String job = "0";
           if(user.getGender()){
               gender = "1";
           }
           if(user.Isteacher()){
               job = "1";
           }
            String sql = "insert into users(account,password,name,gender,job) values(" +
                    "'" + account + "'," + "'" + password + "'," +
                    "'" + name + "'," +
                    gender + "," + job + ")";

            db.execSQL(sql);
        }

        db.close();
    }


    //没关db，失败后记得找这里修改一下
    public Cursor query(String sql, String[] args)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, args);
    }

}
