package com.example.cscb07_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cscb07_project.database.DataManager;
import com.example.cscb07_project.database.ProductInfo;
import com.example.cscb07_project.database.ShoppingRecord;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCar extends AppCompatActivity {
    //    public List<Product> products;
    private Button viewShoppingCarBtn;
    private Order order;
    private Integer price = 0;

    //TODO 加入购物车的信息
    private OrderInfo orderInfo = new OrderInfo();
    //    public static final String BUNDLE_ARG_ORDER_INFO = "ORDER_INFO";
    private NewCustomOrderInfoAdapter newCustomOrderInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = getIntent();
//        if (intent != null && intent.getExtras() != null) {
//            orderInfo = (OrderInfo) intent.getExtras().getSerializable(BUNDLE_ARG_ORDER_INFO);
//        }
        setContentView(R.layout.shopping_car);
        viewShoppingCarBtn = (Button) findViewById(R.id.checkoutButton);
        viewShoppingCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDetailShoppingCar();
            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvOrderInfo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        newCustomOrderInfoAdapter = new NewCustomOrderInfoAdapter(ShoppingCar.this, getData(), new NewCustomOrderInfoAdapter.OnWidgetDealListener() {
            @Override
            public void deleteShoppingCar(CustomProductView customProductView) {
                //删除数量1

                ShoppingRecord shoppingRecord = new ShoppingRecord();
                ProductInfo productInfo = new ProductInfo();
                productInfo.setProductName(customProductView.getProductName());
                productInfo.setStoreName(customProductView.getStoreName());
                productInfo.setProductPrice(customProductView.getProductPrice());
                shoppingRecord.setAccount(Global.account);
                shoppingRecord.setProductInfo(productInfo);

                int amount = DataManager.getInstance().getShoppingRecord(shoppingRecord).get(0).getProductAmount();
                if (amount == 1) {
                    //最少一条,直接删除数据 刷新数据
                    DataManager.getInstance().deleteShoppingRecord(shoppingRecord);
                } else {
                    //修改数据 刷新数据
                    amount = amount-1;
                    shoppingRecord.setProductAmount(amount);
                    DataManager.getInstance().alterShoppingRecord(shoppingRecord);
                }
                newCustomOrderInfoAdapter.update(getData());
            }
        });
        recyclerView.setAdapter(newCustomOrderInfoAdapter);

//        products.add(new Product("Apple", 2.3, 3));
//        products.add(new Product("Pear", 1.8, 1));
//        products.add(new Product("Banana", 3.0, 4));
        TextView tv = (TextView)findViewById(R.id.price);
        tv.setText("Total price: " + price);


    }

    private List<CustomProductView> getData(){
        List<CustomProductView> list = new ArrayList<>();
        List<ShoppingRecord> data = DataManager.getInstance().getAllShoppingRecord(Global.account);

        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                CustomProductView customProductView = new CustomProductView(
                        data.get(i).getProductInfo().getStoreName(),
                        data.get(i).getProductInfo().getProductName(),
                        data.get(i).getProductAmount(),
                        data.get(i).getProductInfo().getProductPrice());
                list.add(customProductView);
                price += data.get(i).getProductInfo().getProductPrice();
            }
        }
        return list;
    }

    public void viewDetailShoppingCar() {
        //Intent intent = new Intent(ShoppingCar.this, PlaceOrderActivity.class);
//        order.orderProducts.add(new Product("Apple", 2.3, 3));
//        order.orderProducts.add(new Product("Banana", 1.8, 1));
//        order.orderProducts.add(new Product("Pear", 3.5, 4));

        //删除购物车数据，刷新列表

        DataManager.getInstance().deleteAllShoppingRecord(Global.account);
        newCustomOrderInfoAdapter.update();

        Intent intent = new Intent(ShoppingCar.this, PopupActivity.class);
        intent.putExtra("order", (Parcelable) order);
        startActivity(intent);
    }

//    public List<Product> getOrderList (){
//        return products;
//    }
}