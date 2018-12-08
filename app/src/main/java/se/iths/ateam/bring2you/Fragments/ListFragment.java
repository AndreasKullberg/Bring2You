package se.iths.ateam.bring2you.Fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;

import se.iths.ateam.bring2you.R;
import se.iths.ateam.bring2you.Utils.ListItemInfo;
import se.iths.ateam.bring2you.Utils.MyUser;
import se.iths.ateam.bring2you.Utils.RecyclerViewAdapter;
import se.iths.ateam.bring2you.Utils.ViewModel;

public class ListFragment extends Fragment {

    List<ListItemInfo> listItems = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private RecyclerView mRecyclerView;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String collection;
    private String title;
    private boolean status;
    private MyUser myUser = new MyUser();
    ListenerRegistration registration;
    ViewModel model;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        firestore = FirebaseFirestore.getInstance();
        mRecyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(listItems);
        mRecyclerView.setAdapter(adapter);
        setHasOptionsMenu(true);
        model = ViewModelProviders.of(getActivity()).get(ViewModel.class);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

         if(firebaseUser != null) {
             collection = Objects.requireNonNull(firebaseUser).getEmail();
         }

        view.findViewById(R.id.floatingActionButton).setOnClickListener(v -> {
            Fragment createDeliveryFragment = new CreateDeliveryFragment();
            getFragmentManager().beginTransaction().replace(R.id.frameLayout,createDeliveryFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {

            int positionDragged = dragged.getAdapterPosition();
            int positionTarget = target.getAdapterPosition();

            Collections.swap(listItems,positionDragged,positionTarget);

            adapter.notifyItemMoved(positionDragged,positionTarget);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        }

    });


    public void onResume() {
        if(!model.isStart()){
            title = getString(R.string.titleNonDelivered);

        }
        else {
            title = model.getTitle();
        }
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
        status = model.isStatus();

        touchHelper.attachToRecyclerView(mRecyclerView);
        super.onResume();
        listItems.clear();
        mRecyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();

        if(firebaseUser != null) {
            firestore.collection("Users").document(firebaseUser.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (Objects.requireNonNull(document).exists()) {
                            String id = document.getId();
                            myUser = document.toObject(MyUser.class);
                           try {
                               Objects.requireNonNull(myUser).setId(id);
                           }
                           catch (Exception e){

                           }

                            if (myUser.isAdmin()) {
                                collection = "Delivered";
                                Log.d("Collection", collection);
                                try {
                                    getActivity().findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                            registration = firestore.collection(collection).whereEqualTo("delivered",status).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                    Log.d("hej", "Event?");
                                    if (e != null) {
                                        return;
                                    }

                                    for (DocumentChange dc : Objects.requireNonNull(queryDocumentSnapshots).getDocumentChanges()) {
                                        if (dc.getType() == DocumentChange.Type.ADDED) {
                                            Log.d("hej", "added?");
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
                        }
                    }
                }
            });
        }
    }

    public void onPause() {
        super.onPause();

        if(registration != null) {
            registration.remove();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.delivered:
                model.setStatus(true);
                model.setTitle(getString(R.string.titleDelivered));
                model.setStart(true);
                onResume();
                return true;
            case R.id.nonDelivered:
                model.setStatus(false);
                model.setTitle(getString(R.string.titleNonDelivered));
                model.setStart(true);
                onResume();
                return true;
            default:
                break;
        }

        return false;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.delivered).setVisible(true);
        menu.findItem(R.id.nonDelivered).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }
}


