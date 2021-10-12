package com.demo.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.shoppinglist.data.MyDbHandler;
import com.demo.shoppinglist.model.shopping;

public class NewList extends AppCompatActivity {

    EditText title,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

    }
    public void addList(View view){
        title=findViewById(R.id.title);
        description=findViewById(R.id.description);

        Toast.makeText(this,"Item Added Successfully",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}