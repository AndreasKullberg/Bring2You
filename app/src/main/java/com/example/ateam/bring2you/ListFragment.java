package com.example.ateam.bring2you;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


import javax.annotation.Nullable;

public class ListFragment extends Fragment {
    List<ListItemInfo> listItems = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private RecyclerView mRecyclerView;
    FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        firestore = FirebaseFirestore.getInstance();
        mRecyclerView.setHasFixedSize(true);

        if(!model.isListFilled()){
            adapter = new RecyclerViewAdapter(listItems);
            model.setRecyclerViewAdapter(adapter);
            model.setListFilled(true);
        }
        else{
            adapter = model.getRecyclerViewAdapter();
        }

        mRecyclerView.setAdapter(adapter);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        firestore.collection("Deliveries").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                Log.d("hej","Event?");
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
                        Log.d("hej","added?");
                        String id = dc.getDocument().getId();
                        ListItemInfo sending = dc.getDocument().toObject(ListItemInfo.class);
                        sending.setId(id);
                        adapter.addItem(sending);

                    } else if (dc.getType() == DocumentChange.Type.REMOVED) {
                        String id = dc.getDocument().getId();
                        adapter.removeItem(id);
                    }
                }
            }
        });
        //Floating action button, register onclick listener
        view.findViewById(R.id.floatingActionButton).setOnClickListener(v -> {

            ListItemInfo info = new ListItemInfo("Mellangården 55", "Andreas Kullberg", "414 82", "63978256");


            firestore.collection("Deliveries")
                    .add(info)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("firebase", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("firebase", "Error adding document", e);
                        }
                    });

        });
        return view;
    }
}


