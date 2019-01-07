package com.example.emonhr.stickynote.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emonhr.stickynote.DataBase.MyDBHelper;
import com.example.emonhr.stickynote.MainActivity;
import com.example.emonhr.stickynote.MyDataType.MyGridViewData;
import com.example.emonhr.stickynote.R;

import java.util.ArrayList;

public class CustomGridViewAdapter extends  BaseAdapter {


    private Context context;
    private ArrayList<MyGridViewData> contents;
    LayoutInflater inflater;
    private MyDBHelper myDBHelper;


    public CustomGridViewAdapter(Context context,ArrayList<MyGridViewData> contents) {
        this.context=context;
        this.contents=contents;

    }

    @Override
    public int getCount() {
        return contents.size();
    }

    @Override
    public Object getItem(int position) {
        return contents.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        final MyGridViewData content=contents.get(position);

        View gridView=convertView;
        if(convertView == null){
            inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView=inflater.inflate(R.layout.activity_custom_grid_view,null);
        }
        LinearLayout linearLayoutView =gridView.findViewById(R.id.note_linear_layout);
        if(content.getColorCode()==2){

            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                linearLayoutView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.note_background2) );
            } else {
                linearLayoutView.setBackground(ContextCompat.getDrawable(context, R.drawable.note_background2));
            }
        }
        else if(content.getColorCode()==3)
        {
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                linearLayoutView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.note_background3) );
            } else {
                linearLayoutView.setBackground(ContextCompat.getDrawable(context, R.drawable.note_background3));
            }
        }

        final TextView descreptionView=gridView.findViewById(R.id.descreption);
        ImageButton button=gridView.findViewById(R.id.delete_button);
        ImageButton shareButton=gridView.findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/*");

                intent.putExtra(Intent.EXTRA_TEXT,descreptionView.getText().toString());

                context.startActivity(Intent.createChooser(intent,"Share with"));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDBHelper=new MyDBHelper(context);
                SQLiteDatabase db=myDBHelper.getWritableDatabase();
                myDBHelper.deleteData(contents.get(position).getId());
                Toast.makeText(context,"deleted the note",Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(context, MainActivity.class);

                context.startActivity(myIntent);
            }
        });

        descreptionView.setText(content.getDescription());
        gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"note editable now",Toast.LENGTH_LONG).show();
                updateNoteDialogBox(contents.get(position).getId(),content.getColorCode(),
                        contents.get(position).getDescription(),convertView);
            }
        });

        return gridView;
    }
    private void updateNoteDialogBox(final int position, int colorCode,String note,View v){
        final Dialog optionDialog;
        //craeting a dialog
        optionDialog=new Dialog(context);

        //set view for the dialog box
        optionDialog.setContentView(R.layout.dialog_input);
        final LinearLayout dialogBox=(LinearLayout) optionDialog.findViewById(R.id.
                dialog_option_LinearLayout);
        if(colorCode==1){
            dialogBox.setBackgroundColor(Color.parseColor("#D8F692"));

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    dialogBox.setBackground(ContextCompat.getDrawable(v.getContext(),
                            R.drawable.dialog_background));
                }
            }catch (Exception e){

            }
        }
        else if(colorCode==2){

            dialogBox.setBackgroundColor(Color.parseColor("#FAD182"));
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    dialogBox.setBackground(ContextCompat.getDrawable(v.getContext(),
                            R.drawable.dialog_background2));
                }
            }catch (Exception e){

            }
        }
        else {
            dialogBox.setBackgroundColor(Color.parseColor("#8FD8FB"));
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    dialogBox.setBackground(ContextCompat.getDrawable(v.getContext(),
                            R.drawable.dialog_background3));
                }
            }catch (Exception e){

            }
        }

        optionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final Button colorCodeView1=(Button) optionDialog.findViewById(R.id.color_code_view1);
        final Button colorCodeView2=(Button) optionDialog.findViewById(R.id.color_code_view2);
        final Button colorCodeView3=(Button) optionDialog.findViewById(R.id.color_code_view3);
        ImageButton save=(ImageButton) optionDialog.findViewById(R.id.save_note);
        ImageButton cancel=(ImageButton) optionDialog.findViewById(R.id.cancel_note);
        final EditText noteDescription=(EditText) optionDialog.findViewById(R.id.full_note_description);
        noteDescription.setText(note);




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
                colorCodeView3.setText("1");
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
                colorCodeView3.setText("2");
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
                String note=noteDescription.getText().toString().trim();
                if(!note.isEmpty()){

                    myDBHelper=new MyDBHelper(context);
                    SQLiteDatabase db=myDBHelper.getWritableDatabase();
                    myDBHelper.updateData(position,colorCode,note);
                    Intent myIntent = new Intent(context, MainActivity.class);

                    context.startActivity(myIntent);
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
