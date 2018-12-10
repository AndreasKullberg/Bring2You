package se.iths.ateam.bring2you.Utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.StorageReference;
import com.ramotion.foldingcell.FoldingCell;

import se.iths.ateam.bring2you.R;


public class ListItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView adress;
    private final TextView name;
    private final TextView postalCode;
    private final TextView senderId;
    public final FoldingCell foldingCell;
    private final TextView infoDate;
    private final TextView infoTime;
    private final TextView infoName;
    private final TextView infoSignedBy;
    private final TextView infoAdress;
    private final TextView infoPostalCode;
    public final ImageView signImage;
    private final String signedbyHolder;
    private final String adressHolder;
    private final String postCodeHolder;
    private final String deteHolder;
    private final String timeHolder;

    private View itemview;
    public ImageButton openMap;
    public String imageUrl;
    private StorageReference storageReference;


    public ListItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemview = itemView;
        adress = itemview.findViewById(R.id.textAdress);
        name = itemView.findViewById(R.id.textName);
        postalCode = itemView.findViewById(R.id.textPostalCode);
        senderId = itemView.findViewById(R.id.textSenderId);
        this.foldingCell = itemView.findViewById(R.id.cell);
        this.openMap = itemView.findViewById(R.id.imageButton_maps);
        infoName = itemView.findViewById(R.id.infoName);
        infoSignedBy = itemView.findViewById(R.id.infoSignedBy);
        infoAdress = itemView.findViewById(R.id.infoAdress);
        infoPostalCode = itemView.findViewById(R.id.infoPostalCode);
        infoTime = itemView.findViewById(R.id.infoTime);
        infoDate = itemView.findViewById(R.id.infoDate);
        signImage = itemView.findViewById(R.id.signImage);
        signedbyHolder = itemView.getResources().getString(R.string.infoSignedBy);
        adressHolder = itemView.getResources().getString(R.string.infoAddress);
        postCodeHolder = itemView.getResources().getString(R.string.infoPostalcode);
        deteHolder = itemView.getResources().getString(R.string.infoDate);
        timeHolder = itemView.getResources().getString(R.string.infoTime);



    }

    public void setData(ListItemInfo info) {
        adress.setText(info.getAdress());
        name.setText(info.getName());
        postalCode.setText(info.getPostalCode());
        senderId.setText(info.getSenderId());
        infoAdress.setText(info.getAdress());
        infoName.setText(info.getName());
        infoPostalCode.setText(info.getPostalCode());
        infoSignedBy.setText(info.getSignedBy());
        infoDate.setText(info.getDate());
        infoTime.setText(info.getTime());
        imageUrl = info.getSignImageUrl();

    }

    public void setInfoData(ListItemInfo info) {
    }


}
