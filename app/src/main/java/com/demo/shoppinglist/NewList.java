package com.demo.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.shoppinglist.Entity.Item;
import com.demo.shoppinglist.data.MyDbHandler;
import com.demo.shoppinglist.model.shopping;

import java.util.Date;

public class NewList extends AppCompatActivity {
    public static final String EXTRA_TITLE = "com.demo.shoppinglist.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.demo.shoppinglist.EXTRA_DESCRIPTION";
    public static final String EXTRA_TIME = "com.demo.shoppinglist.EXTRA_TIME";

    EditText titleEditText,descriptionEditText;
    Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        setTitle("Add Item");

        titleEditText=findViewById(R.id.title);
        descriptionEditText=findViewById(R.id.description);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=titleEditText.getText().toString();
                String description=descriptionEditText.getText().toString();

                if (title.trim().equals("") || description.trim().equals("")){
                    Toast.makeText(getApplicationContext(),"Invalid Data",Toast.LENGTH_SHORT).show();
                }
                else{
                    long time = new Date().getTime();

                    Intent home = new Intent();
                    home.putExtra(EXTRA_TITLE, title);
                    home.putExtra(EXTRA_DESCRIPTION, description);
                    home.putExtra(EXTRA_TIME, String.valueOf(time));

                    setResult(RESULT_OK,home);
                    finish();
                }

            }
        });
    }

}