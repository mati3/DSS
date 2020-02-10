package com.dss.p4_2.UI;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import com.dss.p4_2.Modelo.Product;
import com.dss.p4_2.R;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private Context context;
    private List<Product> productList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView resumen;
        public TextView dot;
        public TextView descripcion;

        public MyViewHolder(View view) {
            super(view);
            resumen = view.findViewById(R.id.resum);
            dot = view.findViewById(R.id.dot);
            descripcion = view.findViewById(R.id.descrip);
        }
    }


    public ProductsAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product p = productList.get(position);

        holder.resumen.setText(p.getResumen());

        // Displaying dot from HTML character code
        holder.dot.setText(Html.fromHtml("&#8226;"));

        // Formatting and displaying timestamp
        holder.descripcion.setText(p.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
