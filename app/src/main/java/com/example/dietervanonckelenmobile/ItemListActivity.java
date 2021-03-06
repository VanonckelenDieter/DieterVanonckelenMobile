package com.example.dietervanonckelenmobile;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private FirebaseFirestore db;
    private List<UurObject> urenList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private static final String TAG = "OverzichtUren";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        setTitle("Overzicht van de uren");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i(TAG, "In landscape mode");
            mTwoPane = true;
        }

        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference();
        db = FirebaseFirestore.getInstance();
        getData();
        recyclerView = findViewById(R.id.item_list_id);
        assert recyclerView != null;
        mAdapter = new SimpleItemRecyclerViewAdapter(this, urenList, mTwoPane);
        recyclerView.setAdapter(mAdapter);
        Log.d("TAG", "onCreate: overzicht activity rendered");


        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }
    }

    public void getData() {
        db.collection("uren").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot object : list) {
                                UurObject uur = object.toObject(UurObject.class);
                                urenList.add(uur);
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private ItemListActivity mParentActivity;
        private List<UurObject> mValues;
        private boolean mTwoPane;


        private View.OnClickListener mOnClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                UurObject item = (UurObject) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString("naam", item.getNaam());
                    arguments.putString("datum", item.getDatum());
                    arguments.putString("les", item.getLes());
                    arguments.putString("uren", item.getUren());



                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_land, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra("naam", item.getNaam());
                    intent.putExtra("datum", item.getDatum());
                    intent.putExtra("les", item.getLes());
                    intent.putExtra("uren", item.getUren());
                    context.startActivity(intent);
                }
            }

        };

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<UurObject> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);

            final ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            UurObject object = mValues.get(position);
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

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setId(position);
            holder.itemView.setOnClickListener(mOnClickListener);
            Log.v("LOL", String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            Log.v("Tag", "aantal" + mValues.size());
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView name;
            public TextView uur;
            public TextView les;
            public TextView datum;
            public ImageView icoon;

            ViewHolder(View view) {
                super(view);
                name = view.findViewById(R.id.nameR);
                uur = view.findViewById(R.id.urenR);
                les = view.findViewById(R.id.lesR);
                datum = view.findViewById(R.id.dateR);
                icoon = view.findViewById(R.id.imageViewR);
            }
        }

    }
}
