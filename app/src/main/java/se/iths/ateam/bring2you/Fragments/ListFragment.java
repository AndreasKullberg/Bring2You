package se.iths.ateam.bring2you.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;

import se.iths.ateam.bring2you.Activities.MainActivity;
import se.iths.ateam.bring2you.Activities.ScannerActivity;
import se.iths.ateam.bring2you.R;
import se.iths.ateam.bring2you.Utils.ListItemInfo;
import se.iths.ateam.bring2you.Utils.MyCanvas;
import se.iths.ateam.bring2you.Utils.MyUser;
import se.iths.ateam.bring2you.Utils.RecyclerViewAdapter;
import se.iths.ateam.bring2you.Utils.ViewModel;
@SuppressWarnings("deprecation")
public class ListFragment extends Fragment  {

    private static ListItemInfo item;
    private static int flipper;
    List<ListItemInfo> listItems = new ArrayList<>();
    ListenerRegistration registration;
    ViewModel model;
    private ImageView signImage;
    private StorageReference storageReference;
    private String imageUrl;
//    private ListItemInfo item;
    private TextView name, signedBy, adress, postalCode, time, date, currentDate;
    static String scanResult;
    private RecyclerViewAdapter adapter;
    private RecyclerView mRecyclerView;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String collection;
    private String title;
    private boolean status;
    private MyUser myUser = new MyUser();
    private EditText signedByView;
    private ImageView signature;
    private StorageTask storageTask;
    private ScrollView mScrollView;
    ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {

            int positionDragged = dragged.getAdapterPosition();
            int positionTarget = target.getAdapterPosition();

            Collections.swap(listItems, positionDragged, positionTarget);

            adapter.notifyItemMoved(positionDragged, positionTarget);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        }

    });
    private SearchView searchView;


    public static void setScanResult(String myResult) {

        ListFragment.scanResult = myResult;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        firestore = FirebaseFirestore.getInstance();
        mRecyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(listItems);
        mRecyclerView.setAdapter(adapter);
        setHasOptionsMenu(true);
        model = ViewModelProviders.of(getActivity()).get(ViewModel.class);
        signedByView = view.findViewById(R.id.signedBy);




        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        collection = firebaseUser.getEmail();
        storageReference = FirebaseStorage.getInstance().getReference("Signatures");

        if (firebaseUser != null) {
            collection = Objects.requireNonNull(firebaseUser).getEmail();
        }

        if (scanResult != null) {
            firestore.collection(collection).document(scanResult).get().addOnCompleteListener(task -> checkScan(task));
            }



//        getView().findViewById(R.id.action_search).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                adapter.filter(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.filter(newText);
//                return true;
//            }
//        });

        view.findViewById(R.id.floatingActionButton).setOnClickListener(v -> {
            Fragment createDeliveryFragment = new CreateDeliveryFragment();
            getFragmentManager().beginTransaction().replace(R.id.frameLayout, createDeliveryFragment)
                    .commit();
        });

        return view;



    }



    public void send(){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        if (item!=null);

    }








    public void onResume() {
        if (!model.isStart()) {
            title = getString(R.string.titleNonDelivered);

        } else {
            title = model.getTitle();
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        status = model.isStatus();

        touchHelper.attachToRecyclerView(mRecyclerView);
        super.onResume();
        listItems.clear();
        mRecyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();

        if (firebaseUser != null) {
            firestore.collection("Users").document(firebaseUser.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (Objects.requireNonNull(document).exists()) {
                            String id = document.getId();
                            myUser = document.toObject(MyUser.class);
                            Objects.requireNonNull(myUser).setId(id);
                            if (myUser.isAdmin()) {
                                collection = "Delivered";
                                Log.d("Collection", collection);
                                getActivity().findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);
                            }
                            registration = firestore.collection(collection).whereEqualTo("delivered", status).addSnapshotListener(new EventListener<QuerySnapshot>() {
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

        if (registration != null) {
            registration.remove();
        }
    }


    private void checkScan(Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
            DocumentSnapshot document = task.getResult();
            if (Objects.requireNonNull(document).exists()) {
                item = document.toObject(ListItemInfo.class);
                Objects.requireNonNull(item).setId(scanResult);
            }
            else {
                Toast.makeText(getActivity(),"Package not found",Toast.LENGTH_LONG).show();

            }
        }
    }

    private String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = simpleDateFormat.format(new Date());

        return currentDate;
    }

    private String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String currentTime = simpleDateFormat.format(new Date());

        return currentTime;
    }

    private byte[] makeSignature() {
        View content = getActivity().findViewById(R.id.my_canvas);
        content.setDrawingCacheEnabled(true);
        content.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = content.getDrawingCache();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);


        return stream.toByteArray();


    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        final MenuItem searchItem = menu.findItem(R.id.action_search);

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

