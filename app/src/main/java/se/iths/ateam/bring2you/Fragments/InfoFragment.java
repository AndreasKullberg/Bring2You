package se.iths.ateam.bring2you.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import se.iths.ateam.bring2you.R;
import se.iths.ateam.bring2you.Utils.ListItemInfo;

public class InfoFragment extends Fragment {
    private ImageView signImage;
    private StorageReference storageReference;
    private String imageUrl;
    private ListItemInfo item;
    private TextView name, signedBy, adress, postalCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        item = (ListItemInfo) Objects.requireNonNull(bundle).getSerializable("Item");

        name = getActivity().findViewById(R.id.infoName);
        signedBy = getActivity().findViewById(R.id.infoSignedBy);
        adress = getActivity().findViewById(R.id.infoAdress);
        postalCode = getActivity().findViewById(R.id.infoPostalCode);

        postalCode.setText(item.getPostalCode());
        adress.setText(item.getAdress());
        signedBy.setText(item.getSignedBy());
        name.setText(item.getName());
        imageUrl = item.getSignImageUrl();

        signImage = getActivity().findViewById(R.id.signImage);
        storageReference = FirebaseStorage.getInstance()
                .getReferenceFromUrl(imageUrl);


        Glide.with(this).load(storageReference).into(signImage);
    }
}
