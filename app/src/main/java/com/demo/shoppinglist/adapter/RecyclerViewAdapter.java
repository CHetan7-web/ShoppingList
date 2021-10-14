package com.demo.shoppinglist.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.shoppinglist.Entity.Item;
import com.demo.shoppinglist.R;
import com.demo.shoppinglist.ViewModel.ItemViewModel;
import com.demo.shoppinglist.data.MyDbHandler;
import com.demo.shoppinglist.model.shopping;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {
    private Context mContext;
    private List<Item> items,itemsFull;
    ItemViewModel mItemViewModel;

    public RecyclerViewAdapter(Context context) {
        this.mContext = context;
        this.items = new ArrayList<>();
        this.itemsFull = new ArrayList<>();
        this.mItemViewModel = new ViewModelProvider((ViewModelStoreOwner) mContext).get(ItemViewModel.class);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());

        if (item.getIsComplete()==null)
            holder.IsComplete.setChecked(false);
        else if(item.getIsComplete()==1)
            holder.IsComplete.setChecked(true);
        else
            holder.IsComplete.setChecked(false);

        if (item.getIsImportant()==null)
            holder.IsImportant.setImageResource(R.drawable.important);
        else if(item.getIsImportant()==1)
                holder.IsImportant.setImageResource(R.drawable.gold_star);
            else
                holder.IsImportant.setImageResource(R.drawable.important);

        holder.IsImportant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getIsImportant() == null){
                    holder.IsImportant.setImageResource(R.drawable.gold_star);
                    Log.d("SET_COLOR","Important Task");
                    item.setIsImportant(Integer.valueOf(1));
                }
                else if(item.getIsImportant()==1){
                        holder.IsImportant.setImageResource(R.drawable.important);
                        Log.d("SET_COLOR","Not Important Task");
                        item.setIsImportant(Integer.valueOf(0));
                }
                    else{
                        holder.IsImportant.setImageResource(R.drawable.gold_star);
                        Log.d("SET_COLOR","Important Task");
                        item.setIsImportant(Integer.valueOf(1));
                }
                mItemViewModel.update(item);
            }
        });

        holder.IsComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getIsComplete() == null){
//                    holder.IsComplete.
//                    Log.d("SET_COLOR","Important Task");
                    item.setIsComplete(Integer.valueOf(1));//1
                }
                else if(item.getIsComplete()==1){
//                    holder.IsImportant.setImageResource(R.drawable.important);
//                    Log.d("SET_COLOR","Not Important Task");
                    item.setIsComplete(Integer.valueOf(0));//0
                }
                else{
//                    holder.IsImportant.setImageResource(R.drawable.gold_star);
//                    Log.d("SET_COLOR","Important Task");
                    item.setIsComplete(Integer.valueOf(1));//1
                }
                mItemViewModel.update(item);
            }
        });

        Log.d("DATA","View Created for"+item.toString());
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public void setItems(List<Item> itemS){
        this.items.clear();
        this.items=itemS;
        this.itemsFull = new ArrayList<>(itemS);
        notifyDataSetChanged();
        Log.d("DATA_ADDED",""+items.size()+" : "+itemsFull.size());
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Item> filterList = new ArrayList<>();
            Log.d("CONSTAINT",String.valueOf(constraint));
            if (constraint==null | constraint.length() == 0) {
                filterList.addAll(itemsFull);
                Log.d("EMPTY_TEXT","NO TEXT "+items.size()+" "+itemsFull.size());
            }
            else {

                String filterPattern = constraint.toString().toLowerCase().trim();
                //Log.d("SEARCH_FILTER",filterPattern+" : "+filterList.size());
                for (Item item:itemsFull){
                    Log.d("ITEM_DATA",item.getTitle()+" : "+item.getTitle().contains(filterPattern)+" : "+filterPattern);
                    if (item.getTitle().toLowerCase().contains(filterPattern)|item.getDescription().toLowerCase().contains(filterPattern)) {
                        filterList.add(item);
                        Log.d("SEARCH_FILTER",item.toString());
                    }
                }

            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items.clear();
            items.addAll((List)results.values);
            Log.d("SEARCH_FILTER","FILTER ADDED");
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView titleTextView;
        private TextView descriptionTextView;
        private CheckBox IsComplete;
        private ImageView IsImportant;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            titleTextView = itemView.findViewById(R.id.title);
            descriptionTextView = itemView.findViewById(R.id.description);
            IsComplete=itemView.findViewById(R.id.isComplete);
            IsImportant=itemView.findViewById(R.id.isImportant);

        }

        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();
            int id1;
            id1= position+1;
            Item item = items.get(position);
//            String name = shop.getName();
//            String number = shop.getQuantity();
//            con.setName(name);
//            con.setPhoneNumber(number);
//            id = db.getId(con);
            Intent intent = new Intent(mContext, EditList.class);
//            id = position+1;
//            intent.putExtra("Rid", id1);
//            intent.putExtra("Rname", name);
//            intent.putExtra("Rquantity", number);
            Log.d("nishi", "onClick: Clicked on id :"+id1);

            mContext.startActivity(intent);
            Log.d("nishi", "onClick:after intent ");
            Toast.makeText(mContext, "Clicked", Toast.LENGTH_SHORT).show();

        }

    }
}
