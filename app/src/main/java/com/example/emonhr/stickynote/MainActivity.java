package com.example.emonhr.stickynote;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.emonhr.stickynote.Adapter.CustomGridViewAdapter;
import com.example.emonhr.stickynote.DataBase.MyDBHelper;
import com.example.emonhr.stickynote.MyDataType.MyGridViewData;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    Dialog optionDialog;
    Context context;

    private ArrayList<MyGridViewData> contents;
    private MyDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;



        myDBHelper=new MyDBHelper(this);
        SQLiteDatabase db=myDBHelper.getWritableDatabase();

        contents=new ArrayList<>();




        //loading data from database in array list;
        contents=myDBHelper.loadData();
        gridView = findViewById(R.id.grid_view);
        gridView.setVerticalScrollBarEnabled(false);
        gridView.setAdapter(new CustomGridViewAdapter(this,contents));

        FloatingActionButton button=(FloatingActionButton) findViewById(R.id.fab);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int currentId=contents.get(position).getId();
                String currentNote=contents.get(position).getDescription();
                //updateNoteDialogBox(currentId,currentNote,view);
                Toast.makeText(context,position+" postion " + currentId,Toast.LENGTH_LONG).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDialogBox(v);
            }
        });

    }
    private void newDialogBox(View v)
    {
        //craeting a dialog
        optionDialog=new Dialog(context);

        //set view for the dialog box
        optionDialog.setContentView(R.layout.dialog_input);
        final LinearLayout dialogBox=(LinearLayout) optionDialog.findViewById(R.id.
                dialog_option_LinearLayout);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                dialogBox.setBackground(ContextCompat.getDrawable(v.getContext(),
                        R.drawable.dialog_background));
            }
        }catch (Exception e){

        }

        optionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final Button colorCodeView1=(Button) optionDialog.findViewById(R.id.color_code_view1);
        final Button colorCodeView2=(Button) optionDialog.findViewById(R.id.color_code_view2);
        final Button colorCodeView3=(Button) optionDialog.findViewById(R.id.color_code_view3);
        ImageButton save=(ImageButton) optionDialog.findViewById(R.id.save_note);
        ImageButton cancel=(ImageButton) optionDialog.findViewById(R.id.cancel_note);
        final EditText noteDescription=(EditText) optionDialog.findViewById(R.id.full_note_description);




        //show the dialog
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.dialog_animation);
        anim.setInterpolator(new BounceInterpolator());
        optionDialog.getWindow().getAttributes().windowAnimations=R.style.DialogSlide;

        optionDialog.show();
        colorCodeView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorCodeView1.setText("1");
                colorCodeView2.setText("1");
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        dialogBox.setBackground(ContextCompat.getDrawable(v.getContext(),
                                R.drawable.dialog_background));
                        //optionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    }
                }catch (Exception e){

                }

            }
        });
        colorCodeView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorCodeView1.setText("2");
                colorCodeView2.setText("2");
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        dialogBox.setBackground(ContextCompat.getDrawable(v.getContext(),
                                R.drawable.dialog_background2));
                        //optionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    }
                }catch (Exception e){

                }

            }
        });

        colorCodeView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorCodeView1.setText("3");
                colorCodeView2.setText("3");
                colorCodeView3.setText("3");
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        dialogBox.setBackground(ContextCompat.getDrawable(v.getContext(),
                                R.drawable.dialog_background3));
                        //optionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    }
                }catch (Exception e){

                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int colorCode= Integer.parseInt(colorCodeView1.getText().toString());
                String note=noteDescription.getText().toString();

                if(!note.isEmpty()){

                    myDBHelper=new MyDBHelper(context);
                    SQLiteDatabase db=myDBHelper.getWritableDatabase();
                    myDBHelper.insertData(colorCode,note);
                    finish();
                    startActivity(getIntent());

                    finish();
                    startActivity(getIntent());
                }
                else{
                    Toast.makeText(context,"please add your note first",Toast.LENGTH_SHORT).show();

                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionDialog.cancel();
            }
        });
    }

}
