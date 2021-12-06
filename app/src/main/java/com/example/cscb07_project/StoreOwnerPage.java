package com.example.cscb07_project;

import static com.example.cscb07_project.R.drawable.ic_home_fish;
import static com.example.cscb07_project.R.drawable.ic_home_fruits;
import static com.example.cscb07_project.R.drawable.ic_home_meats;
import static com.example.cscb07_project.R.drawable.ic_home_veggies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07_project.adapter.CategoryAdapter;
import com.example.cscb07_project.model.Category;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class StoreOwnerPage extends AppCompatActivity{

    private String username = "";
    private String ownerId = "";
    private String storeName;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private Button button;
    private Store store;
    private Model model;

    RecyclerView categoryRecyclerView;
    private ListView products;
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_page);

        categoryRecyclerView = findViewById(R.id.categoryRecycler);
        Intent intent = getIntent();

        username = intent.getStringExtra("username");
        ownerId = intent.getStringExtra("ownerId");
        storeName = intent.getStringExtra("storeName");
        //TextView tv = (TextView)findViewById(R.id.welcome);
        //tv.setText("Welcome Owner" + username);

        // adding data to model
        categoryList = new ArrayList<>();
        categoryList.add(new Category(1,ic_home_fruits));
        categoryList.add(new Category(2,ic_home_fish));
        categoryList.add(new Category(3,ic_home_meats));
        categoryList.add(new Category(4,ic_home_veggies));

        setCategoryRecycler(categoryList);


        model = Model.getInstance();
        getStore();
        //products = (ListView) findViewById(R.id.products);
        //products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //    @Override
        //      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //        Intent intent = new Intent(StoreOwnerPage.this, ProductListActivity.class);
        //        intent.putExtra("ownerId", ownerId);
        //        intent.putExtra("i", i);
        //        startActivity(intent);
        //    }
        //});

    }

    private void getStore() {
        model.getStoreByOwner(ownerId, (Store store) -> {
            if (store == null) {
                Intent intent = new Intent(this, CreateStoreActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("ownerId", ownerId);
                startActivity(intent);
                return;
            }
            this.store = store;
            //ProductListAdapter adapter = new ProductListAdapter(this, R.layout.activity_products_list, store.products_inventory);
            //products.setAdapter(adapter);
            storeName = this.store.storeName;
        });
    }

    private void setCategoryRecycler(List<Category> categoryDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this,categoryDataList);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    public void ownerLogout(View view){
        startActivity (new Intent(StoreOwnerPage.this, MainActivity.class));
    }



    public void addItem(View view) {
        Intent intent = new Intent(this, updateProductActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("ownerId", ownerId);
        intent.putExtra("storeName", storeName);

        startActivity(intent);
    }

    public void seeAll(View view) {
        Intent intent = new Intent(this, ProductListActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("ownerId", ownerId);
        intent.putExtra("storeName", storeName);

        startActivity(intent);
    }

    public void Toshowallorders(View view) {
        startActivity(new Intent(this, allorders.class));
    }

}

