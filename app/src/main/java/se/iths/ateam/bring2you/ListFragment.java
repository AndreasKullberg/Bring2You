package se.iths.ateam.bring2you;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;


import javax.annotation.Nullable;

public class ListFragment extends Fragment {
    List<ListItemInfo> listItems = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private RecyclerView mRecyclerView;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String collection;
    private MyUser myUser = new MyUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        firestore = FirebaseFirestore.getInstance();
        mRecyclerView.setHasFixedSize(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        collection = firebaseUser.getEmail();

        firestore.collection("Users").document(firebaseUser.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String id = document.getId();
                        myUser = document.toObject(MyUser.class);
                        myUser.setId(id);
                        if(myUser.isAdmin()){
                            collection = "Delivered";
                            Log.d("Collection", collection);
                            view.findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);
                        }
                        adapter = new RecyclerViewAdapter(listItems);
                        mRecyclerView.setAdapter(adapter);

                        firestore.collection(collection).addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                Log.d("hej","Event?");
                                if (e != null) {
                                    return;
                                }

                                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
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
                            }
                        });
                    } else {

                    }
                } else {

                }
            }
        });

        view.findViewById(R.id.floatingActionButton).setOnClickListener(v -> {
            Fragment createDeliveryFragment = new CreateDeliveryFragment();
            getFragmentManager().beginTransaction().replace(R.id.frameLayout,createDeliveryFragment)
                    .commit();
        });
        return view;
    }
}


