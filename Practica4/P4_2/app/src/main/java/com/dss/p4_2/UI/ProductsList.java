package com.dss.p4_2.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.dss.p4_2.Modelo.Product;
import com.dss.p4_2.R;
import java.util.ArrayList;
import java.util.List;

public class ProductsList extends AppCompatActivity {
    private List<Product> productList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        productList.addAll(MainActivity.db.getAllProduct());
        ProductsAdapter mAdapter = new ProductsAdapter(this, productList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        getMenuInflater().inflate(R.menu.main_activity_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.add_product:
                Intent a = new Intent(this, AddProduct.class );
                startActivity(a);
                return true;
            case R.id.nav_home: // me coge siempre el último que ponga, no entiendo
                Intent i = new Intent(this, MainActivity.class );
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
