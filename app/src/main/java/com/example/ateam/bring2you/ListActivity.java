package com.example.ateam.bring2you;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class ListActivity extends AppCompatActivity {
    List<ListItemInfo> listItems = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        firestore = FirebaseFirestore.getInstance();



        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        //mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(adapter);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(listItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        firestore.collection("Deliveries").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        /*String adress = dc.getDocument().getString("adress");
                        String name = dc.getDocument().getString("name");
                        String postalCode = dc.getDocument().getString("postalCode");
                        String sendingId = dc.getDocument().getString("senderId");
                        ListItemInfo sending = new ListItemInfo(adress,name,postalCode,sendingId);*/
                        String id = dc.getDocument().getId();
                        ListItemInfo sending = dc.getDocument().toObject(ListItemInfo.class);
                        sending.id = id;
                        adapter.addItem(sending);

                    }
                    else if(dc.getType() == DocumentChange.Type.REMOVED){

                    }
                }
            }
        });
        //Floating action button, register onclick listener
        findViewById(R.id.floatingActionButton).setOnClickListener(view -> {

            ListItemInfo info = new ListItemInfo("Pennygången 59", "Andreas Kullberg", "414 82", "63978256");


            firestore.collection("Deliveries").document()
                    .set(info)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });;
        });
    }

}
