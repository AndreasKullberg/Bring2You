package se.iths.ateam.bring2you.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
    private int i = 1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_createdelivery, container, false);
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
                toastMessage("No blank fields!");
            }

            else {
                firestore.collection("Users").document(user).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            collection = user;

                            ListItemInfo info = new ListItemInfo(adress, name, postalCode, senderId);
                            //toastMessage("Successfully added new delivery!");

                            firestore.collection(collection).document().set(info)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void avoid) {
                                            toastMessage("Successfully added new delivery!");
                                            getActivity().recreate();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            toastMessage("Error adding new delivery..");
                                        }
                                    });
                        }
                        else{
                            toastMessage("User do not exist!");
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });



        return view;

    }
    private void toastMessage(String Message){
        Toast.makeText(getActivity(), Message, Toast.LENGTH_LONG).show();
    }


}
