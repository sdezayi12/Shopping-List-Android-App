package uk.ac.le.co2103.part2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    private List<ShoppingList> shoppingLists;

    private Context context;

    public ShoppingListAdapter(List<ShoppingList> shoppingLists, Context context) {
        this.shoppingLists = shoppingLists;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView SLName;

        public ImageView SLImage;

        public ViewHolder(View itemView) {
            super(itemView);
            SLName = itemView.findViewById(R.id.SLName);
            SLImage = itemView.findViewById(R.id.SLImage);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoppinglist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingList shoppingList = shoppingLists.get(position);

        // Setting name and image of shopping list
        holder.SLName.setText(shoppingList.getName());
        if (shoppingList.getImage() != null && !shoppingList.getImage().isEmpty()) {
            holder.SLImage.setImageURI(Uri.parse(shoppingList.getImage()));
        } else {
            holder.SLImage.setVisibility(View.VISIBLE);
        }

        // Regular click to view shopping list
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShoppingListActivity.class);
                intent.putExtra("listId", shoppingList.getListId());
                context.startActivity(intent);
            }
        });

        // Long click to delete shopping list
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view2) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Shopping List")
                        // If OK delete Shopping List
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int option) {
                                shoppingLists.remove(position);
                                notifyItemRemoved(position);
                                Toast.makeText(context, "Shopping List Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        // If Cancel keep Shopping List
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                return true;
            }
        });
    }

}