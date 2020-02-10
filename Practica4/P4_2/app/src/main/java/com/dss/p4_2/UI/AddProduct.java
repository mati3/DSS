package com.dss.p4_2.UI;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dss.p4_2.Modelo.Product;
import com.dss.p4_2.R;
public class AddProduct extends AppCompatActivity {
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        bt = findViewById(R.id.add_button);
        final Intent e = new Intent(this, ProductsList.class );

        // Add a OnClickListener object to bt.
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resField = findViewById(R.id.EditTextResumen);
                EditText desField = findViewById(R.id.EditTextDescripcion);
                if (!TextUtils.isEmpty(resField.getText().toString())){
                    Product p = new Product();
                    p.setResumen(resField.getText().toString());
                    p.setDescripcion(desField.getText().toString());
                    MainActivity.db.insertProduct(p);
                    startActivity(e);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        getMenuInflater().inflate(R.menu.main_activity_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent i = new Intent(this, MainActivity.class );
                startActivity(i);
                return true;
            case R.id.nav_products:
                Intent e = new Intent(this, ProductsList.class );
                startActivity(e);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}