package com.demo.shoppinglist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.demo.shoppinglist.adapter.RecyclerViewAdapter;
import com.demo.shoppinglist.data.MyDbHandler;
import com.demo.shoppinglist.model.shopping;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<shopping> shoppingArrayList;
    private ArrayAdapter<String> arrayAdapter;
    MyDbHandler db;
//    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new MyDbHandler(MainActivity.this);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        button = findViewById(R.id.drop);
        shoppingArrayList = new ArrayList<>();
        List<shopping> allitems = db.getAllItems();

        for (shopping con: allitems){
            Log.d("nishi", "Id: "+con.getId());
            shoppingArrayList.add(con);
        }

        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, shoppingArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
        findViewById(R.id.drop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setTitle("Attention")
                        .setMessage("are you sure to delete the List")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.delTable();
                                Log.d("nishi", "drop: ");
                                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });


    }
    public void addItem(View view){
        Intent intent = new Intent(this,NewList.class);
        startActivity(intent);
    }

    public void drop(View view){

        db.delTable();
        Log.d("nishi", "drop: ");
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
    }

}