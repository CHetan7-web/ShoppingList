package com.demo.shoppinglist.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.demo.shoppinglist.Database.ShoppingDatabase;
import com.demo.shoppinglist.Entity.Item;
import com.demo.shoppinglist.Interfaces.ItemDao;

import java.util.List;

public class ItemRepository {
    private ItemDao itemDao;
    LiveData<List<Item>> items;

    private ShoppingDatabase database;
    static ItemRepository instnace;

    private void setItems(LiveData<List<Item>> items) {
        this.items = items;
    }

    public LiveData<List<Item>> getItems(){
        return this.items;
    }

    public ItemRepository(Application application){
        this.database = ShoppingDatabase.getInstance(application);
        this.itemDao = database.getItemDao();
        this.items = itemDao.getItems();
    }

    public void insert(Item item){
        new InsertItemAsync(itemDao).execute(item);
    }

    public void delete(Item item){
        new DeleteItemAsync(itemDao).execute(item);
    }

    public void update(Item item){
        new UpdateItemAsync(itemDao).execute(item);
    }

    public void getAllItems(){
        new AllItemAsync(itemDao).execute();
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

    private static class InsertItemAsync extends AsyncTask<Item ,Void,Void>{

        private ItemDao itemDao;

        private InsertItemAsync(ItemDao itemDao){
            itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.deleteItem(items[0]);
            return null;
        }
    }

    private static class UpdateItemAsync extends AsyncTask<Item ,Void,Void>{

        private ItemDao itemDao;

        private UpdateItemAsync(ItemDao itemDao){
            itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.updateItem(items[0]);
            return null;
        }
    }

    private static class DeleteItemAsync extends AsyncTask<Item ,Void,Void>{

        private ItemDao itemDao;

        private DeleteItemAsync(ItemDao itemDao){
            itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.deleteItem(items[0]);
            return null;
        }
    }

    private static class AllItemAsync extends AsyncTask<Void ,Void,LiveData<List<Item>>>{

        private ItemDao itemDao;

        private AllItemAsync(ItemDao itemDao){
            itemDao = itemDao;
        }

        @Override
        protected LiveData<List<Item>> doInBackground(Void... voids) {
            return itemDao.getItems();
        }
    }

    private static class SortedItemsByTitleItemAsync extends AsyncTask<Void ,Void,LiveData<List<Item>>>{

        private ItemDao itemDao;
        private ItemRepository itemRepository;

        private SortedItemsByTitleItemAsync(ItemRepository itemRepository){
            itemRepository = itemRepository;
            itemDao = itemRepository.itemDao;
        }

        @Override
        protected LiveData<List<Item>> doInBackground(Void... voids) {
            return itemDao.getItemsSortedByTitle();
        }

        @Override
        protected void onPostExecute(LiveData<List<Item>> listLiveData) {
            super.onPostExecute(listLiveData);
            itemRepository.setItems(listLiveData);
        }
    }

    private static class SortedItemsByTimeItemAsync extends AsyncTask<Void,Void,LiveData<List<Item>>>{

        private ItemDao itemDao;
        private ItemRepository itemRepository;

        private SortedItemsByTimeItemAsync(ItemRepository itemRepository){
            itemRepository = itemRepository;
            itemDao = itemRepository.itemDao;
        }

        @Override
        protected LiveData<List<Item>> doInBackground(Void... voids) {
            return itemDao.getItemsSortedByTime();
        }

        @Override
        protected void onPostExecute(LiveData<List<Item>> listLiveData) {
            super.onPostExecute(listLiveData);
            itemRepository.setItems(listLiveData);
        }
    }

    private static class IncompleteItemsAsync extends AsyncTask<Void ,Void,LiveData<List<Item>>>{

        private ItemDao itemDao;
        private ItemRepository itemRepository;

        private IncompleteItemsAsync(ItemRepository itemRepository){
            itemRepository = itemRepository;
            itemDao = itemRepository.itemDao;
        }

        @Override
        protected LiveData<List<Item>> doInBackground(Void... voids) {
            return itemDao.getIncompleteItems();
        }

        @Override
        protected void onPostExecute(LiveData<List<Item>> listLiveData) {
            super.onPostExecute(listLiveData);
            itemRepository.setItems(listLiveData);
        }
    }

    private static class DeleteCompleteItemsAsync extends AsyncTask<Void ,Void,Void>{

        private ItemDao itemDao;

        private DeleteCompleteItemsAsync(ItemDao itemDao){
            itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemDao.deleteImportantItems();
            return null;
        }
    }

}
