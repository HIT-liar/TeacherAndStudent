package com.example.firstpro.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.firstpro.data.Class;

import java.util.ArrayList;
import java.util.List;

public class ClassesSQLIteHelper extends SQLiteOpenHelper {

    private static ClassesSQLIteHelper mInstance;
    public static synchronized ClassesSQLIteHelper getInstance(Context context){
        if(mInstance == null){
            mInstance = new ClassesSQLIteHelper(context,"classMessageDB",null,1);
        }
        return mInstance;
    }

    public ClassesSQLIteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sql = "create table classes(_id integer primary key autoincrement, classid text, teacheraccount text, classname text, classnum integer, credit real, location text, maxstunum integer, time text, true_stunum integer)";

        db.execSQL(sql);

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
            String class_id = clas.getClass_id();
            String true_stu_num = ((Integer)clas.getTrue_stu_num()).toString();

            String sql = "insert into classes(classid,teacheraccount,classname,classnum,credit,location,maxstunum,time,true_stunum) values(" +
                    "'" + class_id + "'," +"'" + taccount + "'," + "'" + classname + "'," +
                    classnum + "," + credit + "," + "'" + location + "'," +
                    maxstunum + "," + "'" + time + "',"+true_stu_num+')';

            db.execSQL(sql);
        }

        db.close();
    }

    public Cursor query(String sql, String[] args)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, args);
    }

    public void deleteOne(String class_id ){

        SQLiteDatabase db = this.getWritableDatabase();
        if(db.isOpen()) {


            String sql = "delete from classes where classid=?";

            db.execSQL(sql,new Object[]{class_id});
        }

        db.close();

    }

    public void update(List<Class> classList,Context context){
        List<Class> classes = new ArrayList<>(classList);
        List<String> classidList = new ArrayList<>();
        for(Class cla : classes){//从远端数据库中获得的清单
            classidList.add(cla.getClass_id());
        }

        SQLiteDatabase db = this.getWritableDatabase();
        if(db.isOpen()) {
            String cursor_query = "select classid from classes";
            Cursor cursor = db.rawQuery(cursor_query, null);
            int size = cursor.getCount();
            int index = cursor.getColumnIndex("classid");
           //若本地数据库有，则不管，若本地数据库有而远端没有，则删除
            for (int i=0;i<size;i++){
                cursor.moveToNext();
                String class_id = cursor.getString(index);
                if(classidList.contains(class_id)){
                    classidList.remove(class_id);
                }else{
                    String deleSQL = "delete from classes where classid=?";
                    db.execSQL(deleSQL,new Object[]{class_id});
                }
            }
            cursor.close();
            db.close();
            //把剩下的加进本地数据库
            if(classidList.size()>=1) {
                List<Class> classList2 = new ArrayList<>();
                for(Class cla : classes){
                    if(classidList.contains(cla.getClass_id())){
                        classList2.add(cla);
                    }
                }
                for(Class cla : classList2){
                    ClassesSQLIteHelper.getInstance(context).insertClass(cla);
                }
            }

        }
    }

}
