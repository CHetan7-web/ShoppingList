package com.demo.shoppinglist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.demo.shoppinglist.Entity.Item;
import com.demo.shoppinglist.ViewModel.ItemViewModel;
import com.demo.shoppinglist.adapter.RecyclerViewAdapter;
import com.demo.shoppinglist.data.MyDbHandler;
import com.demo.shoppinglist.model.shopping;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    public ItemViewModel itemViewModel;
    FloatingActionButton addItem ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addItem = findViewById(R.id.addItem);

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);

        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        itemViewModel.getAllItems().
                observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                Toast.makeText(MainActivity.this,"Onchanged",Toast.LENGTH_SHORT).show();
                for (Item item:items)
                    Log.d("ITEM",item.toString());
                recyclerViewAdapter.setItems(items);
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewList.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

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
//                                db.delTable();
//                                Log.d("nishi", "drop: ");
//                                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
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

    public void drop(View view){

//        db.delTable();
        Log.d("nishi", "drop: ");
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.search_menu);
        MenuItem hideComplete = menu.findItem(R.id.hide_completed);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.sort_by_time_menu:
                Log.d("ITEM_MENU","Srt By Time");
                itemViewModel.sortedByTime();
                return true;

            case R.id.sort_by_name_menu:
                Log.d("ITEM_MENU","Srt By Name");
                itemViewModel.sortedByTitle();
                return true;

            case R.id.delete_completed:
                itemViewModel.deleteComplete();
                return true;

            case R.id.hide_completed:
                Toast.makeText(this,"Hiding",Toast.LENGTH_SHORT).show();
                if (item.isChecked()) {
                    item.setChecked(false);
                    itemViewModel.incompleteItems();
                }
                else {
                    item.setChecked(true);
                    itemViewModel.all();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==ADD_NOTE_REQUEST && resultCode==RESULT_OK){
            String title=data.getStringExtra(NewList.EXTRA_TITLE);
            String description=data.getStringExtra(NewList.EXTRA_DESCRIPTION);
            Log.d("EXTRA_TIME",data.getStringExtra(NewList.EXTRA_TIME));
            long time=Long.valueOf(data.getStringExtra(NewList.EXTRA_TIME));

            Item item=new Item(time,title,description);
            itemViewModel.insert(item);

            Toast.makeText(this,"Item saved",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"Item not saved",Toast.LENGTH_SHORT).show();


    }
}