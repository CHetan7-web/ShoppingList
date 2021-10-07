package com.demo.shoppinglist.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.shoppinglist.R;
import com.demo.shoppinglist.data.MyDbHandler;
import com.demo.shoppinglist.model.shopping;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<shopping> shoppingList;
    int color;
    public RecyclerViewAdapter(Context context, List<shopping> shoppingList) {
        this.context = context;
        this.shoppingList = shoppingList;
    }
    MyDbHandler db;
    shopping shop = new shopping();

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        shopping shopping = shoppingList.get(position);
        holder.itemName.setText(shopping.getName());
        holder.quantity.setText(shopping.getQuantity());
        int id = position+1;
        holder.itemId.setText(""+id);
        db = new MyDbHandler(context);
        if(db.getcolor(id)==1){
//            holder.itemId.setTextColor(Color.parseColor("#00ff00"));
            holder.con.setBackgroundColor(Color.GREEN);
        }

        Log.d("nishi", "colorno is");
        holder.itemId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                  Toast.makeText(context, "Clicked"+id, Toast.LENGTH_SHORT).show();
                    Log.d("nishi", "color Changed");
                    if (db.getcolor(id)==0){
//                        holder.itemId.setTextColor(Color.parseColor("#00ff00"));
                        holder.con.setBackgroundColor(Color.GREEN);
                        db.setcolor(id);
                    }
                    else{
                        holder.itemId.setTextColor(Color.parseColor("#000000"));
                        holder.con.setBackgroundColor(Color.WHITE);
                        db.resetcolor(id);
                    }

            }

        });
    }

    @Override
    public int getItemCount() {
        return shoppingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView itemName;
        private TextView quantity;
        private TextView itemId;
        private ConstraintLayout con;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            itemName = itemView.findViewById(R.id.disname);
            quantity = itemView.findViewById(R.id.disquantity);
            itemId = itemView.findViewById(R.id.itemNo);
            con = itemView.findViewById(R.id.background);

        }

        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();
            int id1;
            id1= position+1;
            shopping shop = shoppingList.get(position);
            String name = shop.getName();
            String number = shop.getQuantity();
//            con.setName(name);
//            con.setPhoneNumber(number);
//            id = db.getId(con);
            Intent intent = new Intent(context, EditList.class);
//            id = position+1;
            intent.putExtra("Rid", id1);
            intent.putExtra("Rname", name);
            intent.putExtra("Rquantity", number);
            Log.d("nishi", "onClick: Clicked on id :"+id1);

            context.startActivity(intent);
            Log.d("nishi", "onClick:after intent ");
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();

        }
    }
}
