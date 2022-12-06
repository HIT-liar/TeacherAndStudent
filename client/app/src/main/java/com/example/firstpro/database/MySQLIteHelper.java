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

    public void update(Me user){
        SQLiteDatabase db = this.getReadableDatabase();
        String account = user.getAccount();
        if(db.isOpen()) {
            String sql = "select * from users where account='" + account + "'";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor == null || cursor.getCount() < 1) {
                db.close();
                cursor.close();
                insertUser(user);
                return;
            } else {
                //更新用户名
                String nsql = "update users set name =? where account =?";
                db.execSQL(nsql,new Object[]{user.getName(),account});
                //更新性别
                    String gsql = "update users set gender =? where account =?";
                    if(user.getGender()){
                        db.execSQL(gsql,new Object[]{1,account});
                    }else {
                        db.execSQL(gsql,new Object[]{0,account});
                    }
                    db.close();
                    cursor.close();
            }
        }

    }

}
