package uk.ac.le.co2103.part2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> products;

    private Context context;

    public ProductAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView prodName;

        public TextView prodQuantity;

        public TextView prodUnit;

        public ViewHolder(View view) {
            super(view);
            prodName = view.findViewById(R.id.prodName);
            prodQuantity = view.findViewById(R.id.prodQuantity);
            prodUnit = view.findViewById(R.id.prodUnit);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prod_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Getting product info
        Product product = products.get(position);
        holder.prodName.setText(product.getName());
        holder.prodQuantity.setText(String.valueOf(product.getQuantity()));
        holder.prodUnit.setText(product.getUnit());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                // Clicking update and delete option
                builder.setItems(new String[]{"Update", "Delete"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pick) {
                        switch (pick) {
                            // Updating product not implemented
                            case 0:
                                Intent intent = new Intent(context, UpdateProductActivity.class);
                                intent.putExtra("product", product);
                                intent.putExtra("position", position);
                                ((ShoppingListActivity) context).startActivityForResult(intent, 1);
                                break;
                            // Deleting product
                            case 1:
                                products.remove(position);
                                notifyItemRemoved(position);
                                notifyDataSetChanged();
                                break;
                        }
                    }
                });
                builder.show();
            }
        });
    }
}