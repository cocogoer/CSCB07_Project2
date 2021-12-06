package com.example.cscb07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07_project.database.DataManager;
import com.example.cscb07_project.database.ProductInfo;
import com.example.cscb07_project.database.ShoppingRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomProductPage extends AppCompatActivity {

    public static final String BUNDLE_ARG_STORE_DATA = "STORE_DATA";
    private CustomStoreView storeData;//商品界面传递过来的数据
    private Button ShoppingCarBtn;
    FirebaseDatabase db;
    DatabaseReference Products;
    List<CustomProductView> products;
    private RecyclerView recyclerView;
    //DatabaseReference ProductsPrice;
    //DatabaseReference ProductsQuantity;
    //ArrayList<String> arrayList_ProductsData = new ArrayList<>();
    //ArrayList<Integer> arrayList_ProductPrice = new ArrayList<>();
    //ArrayList<Integer> arrayList_ProductQuantity = new ArrayList<>();

    //TODO 测试加入购物车的数据列表
//    private List<CustomProductView> customProductViewsInCars = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_product_page);
        //intent
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            storeData = (CustomStoreView) intent.getExtras().getSerializable(BUNDLE_ARG_STORE_DATA);
        }
        ShoppingCarBtn = (Button) findViewById(R.id.ShoppingCarButton);
        ShoppingCarBtn.setOnClickListener(this::viewShoppingCar);
        initView();
    }

    public void viewShoppingCar(View view) {
        //TODO 这个地方利用此界面加购物车的数据跳转
        Intent intent = new Intent(CustomProductPage.this, ShoppingCar.class);
//      intent.putExtra("items", orderProductList);
//        OrderInfo orderInfo = new OrderInfo();
//        orderInfo.setData(customProductViewsInCars);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(ShoppingCar.BUNDLE_ARG_ORDER_INFO, orderInfo);
//        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void SaveData(ArrayList<String> arrayList_ProductsData, ArrayList<Integer> arrayList_ProductPrice, ArrayList<Integer> arrayList_ProductQuantity) {

        //int amount = 0;
        db = FirebaseDatabase.getInstance();
        Products = db.getReference("Stores").child("products");
        Products.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String ProductName = ds.child("name").getValue(String.class);
                    Integer ProductPrice = ds.child("price").getValue(Integer.class);
                    Integer ProductQuantity = ds.child("inventory_quantity").getValue(Integer.class);
                    arrayList_ProductsData.add(ProductName);
                    arrayList_ProductPrice.add(ProductPrice);
                    arrayList_ProductQuantity.add(ProductQuantity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.ViewProduct);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (storeData != null) {
            ((TextView) findViewById(R.id.textView6)).setText(storeData.StoreName);
            requestDataAsync();
//            CustomProductAdapter CustomProductAdapter = new CustomProductAdapter(CustomProductPage.this, products);
//            listView.setAdapter(CustomProductAdapter);
        } else {
            Toast.makeText(CustomProductPage.this, "数据错误！", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO 从原界面回来重置数据
//        customProductViewsInCars.clear();
    }

    private void InitData() {
        //HashMap<String, String> StoreDataMap = new HashMap<String, String>();
        //SaveData(StoreDataMap);
        //stores = new ArrayList<>();
        //for(Map.Entry<String, String>entry: StoreDataMap.entrySet()){
        //CustomStoreView store = new CustomStoreView(entry.getKey(),entry.getValue());//这里写传入的数据
        //stores.add(store);
        products = new ArrayList<>();
        CustomProductView p0 = new CustomProductView(storeData.StoreName, "apple", 1, 2);
        products.add(p0);
        CustomProductView p1 = new CustomProductView(storeData.StoreName, "banana", 2, 2);
        products.add(p1);
        CustomProductView p2 = new CustomProductView(storeData.StoreName, "bananananananan", 3, 4);
        products.add(p2);
    }

    private void requestDataAsync() {
        db = FirebaseDatabase.getInstance();
        Products = db.getReference("Stores").child(storeData.StoreName).child("products");
        products = new ArrayList<>();
        Products.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String ProductName = ds.child("name").getValue(String.class);
                    Integer ProductPrice = ds.child("price").getValue(Integer.class);
                    Integer ProductQuantity = ds.child("inventory_quantity").getValue(Integer.class);
                    products.add(new CustomProductView(storeData.StoreName, ProductName, ProductPrice, ProductQuantity));
                }
                if (products.isEmpty()) {
                    Toast.makeText(CustomProductPage.this, "No data~", Toast.LENGTH_SHORT).show();
                    return;
                }
                flushList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CustomProductPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void flushList() {
        NewCustomProductAdapter customProductAdapter = new NewCustomProductAdapter(CustomProductPage.this, products, new NewCustomProductAdapter.OnWidgetDealListener() {
            @Override
            public void addShoppingCar(CustomProductView customProductView) {
                //TODO 这个地方加入你加购物车的逻辑，暂时先用测试数据
//                    customProductViewsInCars.add(customProductView);

                ShoppingRecord shoppingRecord = new ShoppingRecord();
                ProductInfo productInfo = new ProductInfo();
                productInfo.setProductName(customProductView.getProductName());
                productInfo.setStoreName(customProductView.getStoreName());
                productInfo.setProductPrice(customProductView.getProductPrice());
                shoppingRecord.setAccount(Global.account);
                shoppingRecord.setProductInfo(productInfo);

                int amount = DataManager.getInstance().getShoppingRecord(shoppingRecord).size();
                if (amount > 0) {
                    //存在就修改数量
                    amount = DataManager.getInstance().getShoppingRecord(shoppingRecord).get(0).getProductAmount() + 1;
                    shoppingRecord.setProductAmount(amount);
                    DataManager.getInstance().alterShoppingRecord(shoppingRecord);
                } else {
                    //不存在添加一条数据
                    shoppingRecord.setProductAmount(1);
                    DataManager.getInstance().addShoppingRecord(shoppingRecord);
                }

            }
        });
        recyclerView.setAdapter(customProductAdapter);
    }

}