package com.demo.shoppinglist.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.demo.shoppinglist.Database.ShoppingDatabase;
import com.demo.shoppinglist.Entity.Item;
import com.demo.shoppinglist.Interfaces.ItemDao;

import java.util.List;

public class ItemRepository {
    private ItemDao itemDao;
    MutableLiveData<List<Item>> items;

    private ShoppingDatabase database;
    static ItemRepository instnace;

    private void setItems(MutableLiveData<List<Item>> items) {
        this.items.postValue((List<Item>) items);
        Log.d("LIVE_DATA","DATA SET");
    }

    public MutableLiveData<List<Item>> getItems(){
        return this.items;
    }

    public ItemRepository(Application application){
        this.database = ShoppingDatabase.getInstance(application);
        this.itemDao = database.getItemDao();
        this.items = new MutableLiveData<>();
        this.items.postValue( itemDao.getItems());
    }

    public void insert(Item item){
        new InsertItemAsync(this).execute(item);
    }

    public void delete(Item item){
        new DeleteItemAsync(itemDao).execute(item);
    }

    public void update(Item item){
        new UpdateItemAsync(itemDao).execute(item);
    }

    public void getAllItems(){
        new AllItemAsync(this).execute();
    }

    public void getSortedItemsByTitle(){
        new SortedItemsByTitleItemAsync(this).execute();
    }

    public void getSortedItemsByTime(){
        new SortedItemsByTimeItemAsync(this).execute();
    }

    public void incompleteItems(){
        new IncompleteItemsAsync(this).execute();
    }

    public void deleteComplete(){
        new DeleteCompleteItemsAsync(itemDao).execute();
    }

    //************//ASYN TASKs

    private static class InsertItemAsync extends AsyncTask<Item ,Void,Void>{

        private ItemRepository itemRepository;
        private ItemDao itemDao;

        private InsertItemAsync(ItemRepository itemRepository){
            this.itemRepository = itemRepository;
            this.itemDao = this.itemRepository.itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            this.itemDao.addItem(items[0]);
            return null;
        }
    }

    private static class UpdateItemAsync extends AsyncTask<Item ,Void,Void>{

        private ItemDao itemDao;

        private UpdateItemAsync(ItemDao itemDao){
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            this.itemDao.updateItem(items[0]);
            return null;
        }
    }

    private static class DeleteItemAsync extends AsyncTask<Item ,Void,Void>{

        private ItemDao itemDao;

        private DeleteItemAsync(ItemDao itemDao){
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            this.itemDao.deleteItem(items[0]);
            return null;
        }
    }

    private static class AllItemAsync extends AsyncTask<Void ,Void,MutableLiveData<List<Item>>>{

        private ItemRepository itemRepository;
        private ItemDao itemDao;

        private AllItemAsync(ItemRepository itemRepository){
            this.itemRepository = itemRepository;
            this.itemDao = this.itemRepository.itemDao;
        }

        @Override
        protected MutableLiveData<List<Item>> doInBackground(Void... voids) {
            return (MutableLiveData<List<Item>>) this.itemDao.getItems();

        }

        @Override
        protected void onPostExecute(MutableLiveData<List<Item>> listMutableLiveData) {
            super.onPostExecute(listMutableLiveData);
            this.itemRepository.setItems(listMutableLiveData);
        }
    }

    private static class SortedItemsByTitleItemAsync extends AsyncTask<Void ,Void,MutableLiveData<List<Item>>>{

        private ItemDao itemDao;
        private ItemRepository itemRepository;

        private SortedItemsByTitleItemAsync(ItemRepository itemRepository){
            this.itemRepository = itemRepository;
            this.itemDao = this.itemRepository.itemDao;
        }

        @Override
        protected MutableLiveData<List<Item>> doInBackground(Void... voids) {
            return (MutableLiveData<List<Item>>) this.itemDao.getItemsSortedByTitle();
        }

        @Override
        protected void onPostExecute(MutableLiveData<List<Item>> listMutableLiveData) {
            super.onPostExecute(listMutableLiveData);
            this.itemRepository.setItems(listMutableLiveData);
            Log.d("SORT_TIME","Sorted By Title");
        }
    }

    private static class SortedItemsByTimeItemAsync extends AsyncTask<Void,Void,MutableLiveData<List<Item>>>{

        private ItemDao itemDao;
        private ItemRepository itemRepository;

        private SortedItemsByTimeItemAsync(ItemRepository itemRepository){
            this.itemRepository = itemRepository;
            this.itemDao = this.itemRepository.itemDao;
        }

        @Override
        protected MutableLiveData<List<Item>> doInBackground(Void... voids) {
            return (MutableLiveData<List<Item>>) this.itemDao.getItemsSortedByTime();
        }

        @Override
        protected void onPostExecute(MutableLiveData<List<Item>> listMutableLiveData) {
            super.onPostExecute(listMutableLiveData);
            this.itemRepository.setItems(listMutableLiveData);
            Log.d("SORT_TIME","Sorted By time");
        }
    }

    private static class IncompleteItemsAsync extends AsyncTask<Void ,Void,MutableLiveData<List<Item>>>{

        private ItemDao itemDao;
        private ItemRepository itemRepository;

        private IncompleteItemsAsync(ItemRepository itemRepository){
            this.itemRepository = itemRepository;
            this.itemDao = this.itemRepository.itemDao;
        }

        @Override
        protected MutableLiveData<List<Item>> doInBackground(Void... voids) {
            return (MutableLiveData<List<Item>>) this.itemDao.getIncompleteItems();
        }

        @Override
        protected void onPostExecute(MutableLiveData<List<Item>> listMutableLiveData) {
            super.onPostExecute(listMutableLiveData);
            this.itemRepository.setItems(listMutableLiveData);
        }
    }

    private static class DeleteCompleteItemsAsync extends AsyncTask<Void ,Void,Void>{

        private ItemDao itemDao;

        private DeleteCompleteItemsAsync(ItemDao itemDao){
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            this.itemDao.deleteImportantItems();
            return null;
        }
    }

}
