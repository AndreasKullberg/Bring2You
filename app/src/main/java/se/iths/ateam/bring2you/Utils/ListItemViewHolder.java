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
    protected final FoldingCell foldingCell;
    private final TextView infoDate;
    private final TextView infoTime;
    private final TextView infoName;
    private final TextView infoSignedBy;
    private final TextView infoAdress;
    private final TextView infoPostalCode;
    protected final ImageView signImage;

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
}
