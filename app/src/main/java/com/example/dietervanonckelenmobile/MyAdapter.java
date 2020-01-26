package com.example.dietervanonckelenmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<UurObject> mDataset;
    private Context context;

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

        MyViewHolder vh = new MyViewHolder(ll);
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

        public MyViewHolder(LinearLayout ll) {
            super(ll);
            name = ll.findViewById(R.id.nameR);
            uur = ll.findViewById(R.id.urenR);
            les = ll.findViewById(R.id.lesR);
            datum = ll.findViewById(R.id.dateR);
        }
    }
}