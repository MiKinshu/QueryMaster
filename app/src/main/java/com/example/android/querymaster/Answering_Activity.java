package com.example.android.querymaster;

import android.content.Intent;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Answering_Activity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public static final String TAG="Answering_Activity";
    private TextView questionTextView;
    private TextView answerEditText;
    private ArrayList<String> answerArrayList;
    private String questionName;
    private int ObjPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answering_);
        questionTextView=findViewById(R.id.questionTextView);//fill in this using intent from main activity
        answerEditText=findViewById(R.id.answerEditText);
        Button submitButton=findViewById(R.id.submitButton);
        Button uploadimageButton=findViewById(R.id.uploadimageButton);


        getIncomingIntent();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Submitting Answer",Toast.LENGTH_SHORT).show();
                String answer=answerEditText.getText().toString();
                answerEditText.setText("");
                answerArrayList.add(answer);
                //Now this answer has to be added to ArrayList of Answers of that particular query
                Intent myIntent=new Intent(v.getContext(),MainActivity.class);
                myIntent.putExtra("objPostion",ObjPosition);
                myIntent.putStringArrayListExtra("answersList",answerArrayList);
                v.getContext().startActivity(myIntent);
            }
        });

        uploadimageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView answerImageView = findViewById(R.id.answerImageView);
            answerImageView.setVisibility(View.VISIBLE);
            answerImageView.setImageBitmap(imageBitmap);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void getIncomingIntent()
    {
        Log.d(TAG,"Getting Incoming Intent");
        if(getIntent().hasExtra("question")&&getIntent().hasExtra("answersList")&&getIntent().hasExtra("objPosition"));
        {
            questionName=getIntent().getStringExtra("question");
            answerArrayList=getIntent().getStringArrayListExtra("answersList");
            ObjPosition=getIntent().getIntExtra("objPosition",-1);
            setQuestionName();
        }
    }

    private void setQuestionName()
    {
        questionTextView.setText(questionName);
    }

}
