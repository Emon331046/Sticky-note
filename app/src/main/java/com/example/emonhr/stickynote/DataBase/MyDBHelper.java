package com.example.emonhr.stickynote.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.emonhr.stickynote.MyDataType.MyGridViewData;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME      = "stickyNote.db";

    private static final String CURRENT_TABLE_NAME = "StickyNoteTable";

    private static final int    DATABASE_VERSION   = 1;

    private static final String SQL_CREATE_STUDENT_TABLE =  "CREATE TABLE " + CURRENT_TABLE_NAME + " ("
            + "_ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "color_code" + " INTEGER DEFAULT 1, "
            + "descreption" + " TEXT NOT NULL);";

    private static final String Drop_Table="DROP TABLE IF EXISTS "+CURRENT_TABLE_NAME;

    private Context context;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        try {

            db.execSQL(SQL_CREATE_STUDENT_TABLE);
            Toast.makeText(context,"database created ",Toast.LENGTH_SHORT).show();

        }catch (Exception e){

            Toast.makeText(context,"Error on creating table",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            sqLiteDatabase.execSQL(Drop_Table);
            Toast.makeText(context,"on upgrade ",Toast.LENGTH_SHORT).show();
            onCreate(sqLiteDatabase);

        }catch (Exception e){

            Toast.makeText(context,"failed to catch",Toast.LENGTH_SHORT).show();
        }
    }
    public ArrayList<MyGridViewData> loadData()
    {
        Cursor cursor=showStickyNotes();
         ArrayList<MyGridViewData> contents=new ArrayList<>();
        if(cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                MyGridViewData studentHelper=new MyGridViewData(cursor.getInt(0),
                        cursor.getInt(1),cursor.getString(2));

                contents.add(studentHelper);
            }
        }
        return contents;
    }
    public Cursor showStickyNotes() {

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor;
        String SelectDb="SELECT * FROM "+CURRENT_TABLE_NAME;

        cursor=db.rawQuery(SelectDb,null);

        return cursor;
    }

    public void insertData(int colorCode ,String description) {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor;
        ContentValues values=new ContentValues();
        values.put("descreption",description);
        values.put("color_code",colorCode);
        long rowId=db.insert(CURRENT_TABLE_NAME,null, values);

    }
    public void updateData(int id,int colorCode,String description){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor;
        String strFilter = "_id=" + id;
        ContentValues values=new ContentValues();
        values.put("descreption",description);
        values.put("color_code",colorCode);

        long rowId=db.update(CURRENT_TABLE_NAME,values,strFilter,null);
    }
    public void deleteData(int id){

            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM " + CURRENT_TABLE_NAME+ " WHERE "+"_ID"+"='"+id+"'");
            db.close();

    }
}