package se.iths.ateam.bring2you.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import se.iths.ateam.bring2you.Utils.ListItemInfo;
import se.iths.ateam.bring2you.R;

public class CreateDeliveryFragment extends Fragment {
    private FirebaseFirestore firestore;
    private EditText createAdress, createName, createPostal, createSender, sendTo;
    private String adress, name, postalCode, senderId;
    private String collection;
    private String user;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_createdelivery, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.titleCreateDelivery);
        createAdress = view.findViewById(R.id.createAdress);
        createName = view.findViewById(R.id.createName);
        createPostal = view.findViewById(R.id.createPostal);
        createSender = view.findViewById(R.id.createSender);
        sendTo = view.findViewById(R.id.sendTo);
        firestore = FirebaseFirestore.getInstance();

        view.findViewById(R.id.createButton).setOnClickListener(v -> {
            user = sendTo.getText().toString();
            adress = createAdress.getText().toString();
            name = createName.getText().toString();
            postalCode = createPostal.getText().toString();
            senderId = createSender.getText().toString();

            if(adress.equals("")||name.equals("")||postalCode.equals("")||senderId.equals("")||sendTo.equals("")){
                toastMessage(getString(R.string.no_blank_fields));
            }

            else {
                userExist();
            }
        });

        return view;

    }

    private void userExist() {
        firestore.collection("Users").document(user).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    collection = user;

                    ListItemInfo info = new ListItemInfo(adress, name, postalCode, senderId);
                    //toastMessage("Successfully added new delivery!");

                    addDelivery(info);
                }
                else{
                    toastMessage(getString(R.string.user_doesnt_exist));
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void addDelivery(ListItemInfo info) {
        firestore.collection(collection).document().set(info)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid) {

                        getFragmentManager().popBackStack();
                        toastMessage(getString(R.string.delivery_added));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        toastMessage(getString(R.string.delivery_error));
                    }
                });
    }

    private void toastMessage(String Message){
        Toast.makeText(getActivity(), Message, Toast.LENGTH_LONG).show();
    }


}

