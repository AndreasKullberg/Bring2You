package se.iths.ateam.bring2you.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
    private TextView name, signedBy, adress, postalCode, time, date;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.titleInfo);

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
        time = getActivity().findViewById(R.id.infoTime);
        date = getActivity().findViewById(R.id.infoDate);

        postalCode.setText(getString(se.iths.ateam.bring2you.R.string.infoPostalcode)+ "  " + item.getPostalCode());
        adress.setText(getString(se.iths.ateam.bring2you.R.string.infoAddress)+ "  " + item.getAdress());
        signedBy.setText(getString(se.iths.ateam.bring2you.R.string.infoSignedBy)+ "  " + item.getSignedBy());
        name.setText(getString(se.iths.ateam.bring2you.R.string.infoName)+ "  " + item.getName());
        date.setText(getString(se.iths.ateam.bring2you.R.string.infoDate)+ "  " + item.getDate());
        time.setText(getString(se.iths.ateam.bring2you.R.string.infoTime)+ "  " + item.getTime());
        imageUrl = item.getSignImageUrl();


        signImage = getActivity().findViewById(R.id.signImage);
        storageReference = FirebaseStorage.getInstance()
                .getReferenceFromUrl(imageUrl);


        Glide.with(this).load(storageReference).into(signImage);
    }
}
