package se.iths.ateam.bring2you.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import se.iths.ateam.bring2you.Utils.ListItemInfo;
import se.iths.ateam.bring2you.R;
import se.iths.ateam.bring2you.Utils.MyCanvas;

@SuppressWarnings("deprecation")
public class SignFragment extends Fragment {
    private FirebaseFirestore firestore;
    private EditText signedByView;
    static String scanResult;
    private ListItemInfo item;
    private ImageView signature;
    private StorageReference storageReference;
    private StorageTask storageTask;
    private String collection;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private TextView name,  adress, postalCode;


    public static void setScanResult(String myResult) {

        SignFragment.scanResult = myResult;
    }
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.titleSign);
        firestore = FirebaseFirestore.getInstance();
        signedByView = view.findViewById(R.id.signedBy);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        collection = firebaseUser.getEmail();
        storageReference = FirebaseStorage.getInstance().getReference("Signatures");


        if (scanResult != null){
        firestore.collection(collection).document(scanResult).get().addOnCompleteListener(task -> checkScan(task));
        }

        if (scanResult == null) {
            Bundle bundle = getArguments();
            item = (ListItemInfo) Objects.requireNonNull(bundle).getSerializable("Item");
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        name = getActivity().findViewById(R.id.infoName);
        adress = getActivity().findViewById(R.id.infoAdress);
        postalCode = getActivity().findViewById(R.id.infoPostalCode);

        name.setText(getString(se.iths.ateam.bring2you.R.string.infoName)+ "  " + item.getName());
        postalCode.setText(getString(se.iths.ateam.bring2you.R.string.infoPostalcode)+ "  " + item.getPostalCode());
        adress.setText(getString(se.iths.ateam.bring2you.R.string.infoAddress)+ "  " + item.getAdress());
        clear();

        Objects.requireNonNull(getActivity()).findViewById(R.id.sendButton).setOnClickListener(v -> {
            item.setSignedBy(signedByView.getText().toString());
            item.setDate(getDate());
            item.setTime(getTime());
            item.setDelivered(true);


            if(item.getSignedBy().equals("")){
                toastMessage(getString(R.string.no_blank_fields));
            }
            else {
                upload();
            }
        });
    }

    private void upload() {
        StorageReference fileRef = storageReference.child(item.getId() + ".png");
        storageTask = fileRef.putBytes(makeSignature()).addOnSuccessListener(taskSnapshot -> {
        });

        item.setSignImageUrl("gs://" + fileRef.getBucket() + "/Signatures/" + item.getId() + ".png");

        Task task1 = firestore.collection(collection).document(item.getId()).set(item);

        Task task2 = firestore.collection("Delivered").document(item.getId()).set(item);

        Tasks.whenAllSuccess(task1, task2, storageTask).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
            @Override
            public void onSuccess(List<Object> objects) {
                toastMessage(getString(R.string.successDelivered));
                getFragmentManager().popBackStack();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                toastMessage(getString(R.string.deliveryFailed));
            }
        });
    }

    private void clear() {
        getActivity().findViewById(R.id.clear_btn).setOnClickListener(v ->{

            MyCanvas mycanvas = getActivity().findViewById(R.id.my_canvas);
            mycanvas.clear();

        }  );
    }


    private byte[] makeSignature() {
        View content = getActivity().findViewById(R.id.my_canvas);
        content.setDrawingCacheEnabled(true);
        content.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = content.getDrawingCache();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 80,stream);


        return stream.toByteArray();

    }

    private String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = simpleDateFormat.format(new Date());

        return currentDate;
    }
    private String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String currentTime = simpleDateFormat.format(new Date());

        return currentTime;
    }

    private void checkScan(Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
            DocumentSnapshot document = task.getResult();
            if (Objects.requireNonNull(document).exists()) {
                item = document.toObject(ListItemInfo.class);
                if(item.isDelivered()){
                    toastMessage(getString(R.string.alreadyDelivered));
                    getFragmentManager().popBackStack();
                }
                Objects.requireNonNull(item).setId(scanResult);
            }
            else {
                toastMessage(getString(R.string.idNotExist));
                getFragmentManager().popBackStack();
            }
        }
        else {}
    }
    private void toastMessage(String Message){
        Toast.makeText(getActivity(), Message, Toast.LENGTH_LONG).show();
    }

}


