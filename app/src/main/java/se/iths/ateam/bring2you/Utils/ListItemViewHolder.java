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
    private final String infoSignedHolder;
    private final String infopostholder;
    private final String infoPlaceHolder;
    private final String infoDateHolder;
    private final String infoadressholder;
    private final String space;
    protected ImageView cnClImgBtn;
    protected final ImageView listIcon;

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
        infoPlaceHolder = itemView.getResources().getString(R.string.infoTime);
        infoDateHolder = itemView.getResources().getString(R.string.infoDate);
        infopostholder = itemView.getResources().getString(R.string.infoPostalcode);
        infoadressholder = itemView.getResources().getString(R.string.infoAddress);
        infoSignedHolder = itemView.getResources().getString(R.string.infoSignedBy);
        space = " ";
        listIcon = itemView.findViewById(R.id.listIcon);
        cnClImgBtn = itemView.findViewById(R.id.cllogo3);



    }

    public void setData(ListItemInfo info) {
        adress.setText(infoadressholder+space+info.getAdress());
        name.setText(info.getName());
        postalCode.setText(infopostholder+space+info.getPostalCode());
        senderId.setText(info.getSenderId());
        infoAdress.setText(infoadressholder+space+info.getAdress());
        infoName.setText(info.getName());
        infoPostalCode.setText(infopostholder+space+info.getPostalCode());
        infoSignedBy.setText(infoSignedHolder+space+info.getSignedBy());
        infoDate.setText(infoDateHolder+space+info.getDate());
        infoTime.setText(infoPlaceHolder+space+info.getTime());
        imageUrl = info.getSignImageUrl();

    }
}
