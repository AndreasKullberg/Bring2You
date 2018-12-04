package se.iths.ateam.bring2you.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import se.iths.ateam.bring2you.Utils.ListItemInfo;
import se.iths.ateam.bring2you.R;

public class SignFragment extends Fragment {
    private FirebaseFirestore firestore;
    private EditText signedByView;
    static String scanResult;
    private ListItemInfo item;
    private ImageView signature;
    private StorageReference storageReference;
    private StorageTask storageTask;
    private String collection;

    public static void setScanResult(String myResult) {

        SignFragment.scanResult = myResult;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        firestore = FirebaseFirestore.getInstance();
        signedByView = view.findViewById(R.id.signedBy);
        storageReference = FirebaseStorage.getInstance().getReference("SignImage");


        if (scanResult != null){
        firestore.collection("Deliveries").document(scanResult).get().addOnCompleteListener(task -> checkScan(task));
        }

        if (scanResult == null) {
            Bundle bundle = getArguments();
            item = (ListItemInfo) bundle.getSerializable("Item");
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.sendButton).setOnClickListener(v -> {

            /*storageTask = storageReference.putFile().addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    item.setSignImageUrl(taskSnapshot.getStorage().getDownloadUrl().toString());
                }
            });*/

            item.setSignedBy(signedByView.getText().toString());

            firestore.collection("Delivered")
                    .document(item.getId())
                    .set(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("succsesSet", "DocumentSnapshot successfully added!");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("succsesSet", "Error deleting document", e);
                }
            });
            firestore.collection("Deliveries")
                    .document(item.getId())
                    .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("succsesDelete", "DocumentSnapshot successfully deleted!");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("succsesDelete", "Error deleting document", e);
                }
            });
        });
        getActivity().recreate();
    }

    private void checkScan(Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
            DocumentSnapshot document = task.getResult();
            if (document.exists()) {
                item = document.toObject(ListItemInfo.class);
                item.setId(scanResult);
            }
            else {}
        }
        else {}
    }

}
