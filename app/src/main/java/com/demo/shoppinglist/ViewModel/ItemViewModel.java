package com.demo.shoppinglist.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.demo.shoppinglist.Entity.Item;
import com.demo.shoppinglist.Repository.ItemRepository;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private MutableLiveData<List<Item>> allItems;
    private ItemRepository itemRepository;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        itemRepository = new ItemRepository(application);
//        this.allItems= new LiveData<>();
        allItems= itemRepository.getItems();
    }

    public void insert(Item item){
        itemRepository.insert(item);
    }

    public void update(Item item){
        itemRepository.update(item);
    }

    public void delete(Item item){
        itemRepository.delete(item);
    }

    public void sortedByTitle(){
        itemRepository.getSortedItemsByTitle();
    }

    public void sortedByTime(){
        itemRepository.getSortedItemsByTime();
    }

    public void incompleteItems(){
        itemRepository.incompleteItems();
    }

    public void deleteComplete(){
        itemRepository.deleteComplete();
    }

    public void all(){
        itemRepository.getAllItems();
    }

    public MutableLiveData<List<Item>> getAllItems() {
        return allItems;
    }
}
