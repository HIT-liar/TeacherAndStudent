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
        String classesSql = "create table classes(_id integer primary key autoincrement, teacheraccount text, classname text, classnum integer, credit real, location text, maxstunum integer, time text)";

        db.execSQL(personSql);
        db.execSQL(classesSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertClass(Class clas){


        SQLiteDatabase db = this.getWritableDatabase();

        if(db.isOpen()) {

            String taccount = clas.getTeacheraccount();
            String classname = clas.getClass_name();
            String classnum =( (Integer)clas.getClass_num()).toString();
            String credit = String.valueOf(clas.getCourse_credit());
            String location = clas.getLocation_Of_Class();
            String maxstunum = ((Integer)clas.getMax_stu_num()).toString();
            String time = clas.getTime();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("insert into classes(teacheraccount,classname,classnum,credit,location,maxstunum,time) values(")
                         .append("'"+taccount+"',").append("'"+classname+"',")
                         .append(classnum+",").append(credit+",").append("'"+location+"',")
                         .append(maxstunum+",").append("'"+time+"')");
            String sql = stringBuilder.toString();

            db.execSQL(sql);
        }

        db.close();
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
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("insert into users(account,password,name,gender,job) values(")
                    .append("'"+account+"',").append("'"+password+"',")
                    .append("'"+name+"',")
                    .append(gender+",").append(job+")");
            String sql = stringBuilder.toString();

            db.execSQL(sql);
        }

        db.close();
    }


    public Cursor query(String sql, String[] args)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        return cursor;
    }

}
