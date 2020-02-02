package com.example.dietervanonckelenmobile;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<UurObject> mDataset;
    private Context context;
    Dialog myDialog;
    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<UurObject> myDataset, Context context) {
        this.mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout ll = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);

        final MyViewHolder vh = new MyViewHolder(ll);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "TESTCLICK" + vh.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        UurObject object = mDataset.get(position);
        holder.name.setText("Naam: " + object.getNaam());
        holder.uur.setText("Uren: " + object.getUren());
        holder.les.setText("Les: " + object.getLes());
        holder.datum.setText("Datum: " + object.getDatum());
        try {
            if (object.getNaam().contains("Dieter")) {
                holder.icoon.setImageResource(R.drawable.class.getField("ic_verified").getInt(null));
            } else {
                holder.icoon.setImageResource(R.drawable.class.getField("ic_not").getInt(null));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;
        public TextView uur;
        public TextView les;
        public TextView datum;
        public ImageView icoon;

        public MyViewHolder(LinearLayout ll) {
            super(ll);
            name = ll.findViewById(R.id.nameR);
            uur = ll.findViewById(R.id.urenR);
            les = ll.findViewById(R.id.lesR);
            datum = ll.findViewById(R.id.dateR);
            icoon = ll.findViewById(R.id.imageViewR);
        }
    }
}