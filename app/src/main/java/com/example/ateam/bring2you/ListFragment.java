package com.example.ateam.bring2you;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListFragment extends Fragment {
    private final List<ListItemInfo> listItems = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private FirebaseFirestore firestore;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView);
        firestore = FirebaseFirestore.getInstance();
        mRecyclerView.setHasFixedSize(true);

        adapter = new RecyclerViewAdapter(listItems);
        mRecyclerView.setAdapter(adapter);

        firestore.collection("Deliveries").addSnapshotListener((queryDocumentSnapshots, e) -> {
            Log.d("hej","Event?");
            if (e != null) {
                return;
            }

            for (DocumentChange dc : Objects.requireNonNull(queryDocumentSnapshots).getDocumentChanges()) {
                if (dc.getType() == DocumentChange.Type.ADDED) {
                    Log.d("hej","added?");
                    String id = dc.getDocument().getId();

                    ListItemInfo sending = dc.getDocument().toObject(ListItemInfo.class);
                    sending.setId(id);
                    adapter.addItem(sending);
                }
                else if (dc.getType() == DocumentChange.Type.REMOVED) {
                    String id = dc.getDocument().getId();
                    adapter.removeItem(id);
                }
            }
        });
        //Floating action button, register onclick listener
        view.findViewById(R.id.floatingActionButton).setOnClickListener(v -> {

            ListItemInfo info = new ListItemInfo("Mellangården 55", "Andreas Kullberg", "414 82", "63978256");


            firestore.collection("Deliveries")
                    .add(info)
                    .addOnSuccessListener(documentReference -> Log.d("firebase", "DocumentSnapshot added with ID: " + documentReference.getId()))
                    .addOnFailureListener(e -> Log.w("firebase", "Error adding document", e));

        });
        return view;
    }
}


