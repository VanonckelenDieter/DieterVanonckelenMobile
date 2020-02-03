package com.example.dietervanonckelenmobile;

import android.content.Context;
import android.content.Intent;
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
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    public ItemListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference();
        db = FirebaseFirestore.getInstance();
        getData();
        recyclerView = findViewById(R.id.item_list_id);
        mAdapter = new SimpleItemRecyclerViewAdapter(this, urenList, mTwoPane);
        recyclerView.setAdapter(mAdapter);
        Log.d("TAG", "onCreate: overzicht activity rendered");





        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
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
                Log.v("GETTAG", String.valueOf(view.getId()));
                if (mTwoPane) {
                    Log.v("if", "in if");
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, String.valueOf(view.getId()));
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Log.v("else", "in else");
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, String.valueOf(item.getNaam()));
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
