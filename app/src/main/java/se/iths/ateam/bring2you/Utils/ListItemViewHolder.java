package se.iths.ateam.bring2you.Utils;

import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.ramotion.foldingcell.FoldingCell;

import se.iths.ateam.bring2you.R;
import se.iths.ateam.bring2you.Utils.ListItemInfo;

public class ListItemViewHolder extends RecyclerView.ViewHolder {

    public final ViewFlipper viewFlipper;
    private final TextView adress;
    private final TextView name;
    private final TextView postalCode;
    private final TextView senderId;
    public final FoldingCell constraintLayout;
    public final View cContentTimeDate;
    public final View cContentName;
    public final View infoPostalCode;
    public final View infoTime;
    public final View infoName;
    public final View infoDate;
    public final View infoAdress;
    public final View infoSignedBy;
    public final View signimage;
    public final ProgressBar cardProgress;

    public EditText signedByView;
    public View itemview;
    public MyCanvas canvas;
    //public Buttons
    public  Button clearBtn;
    public Button sendButton;
    public ImageButton openMap;

    public ListItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemview = itemView;
        adress = itemview.findViewById(R.id.textAdress);
        this.name = itemView.findViewById(R.id.textName);
        this.cContentTimeDate = itemView.findViewById(R.id.cardcontentTimeDate);
        this.cContentName = itemView.findViewById(R.id.cardcontentname);
        postalCode = itemView.findViewById(R.id.textPostalCode);
        senderId = itemView.findViewById(R.id.textSenderId);
        this.constraintLayout = itemView.findViewById(R.id.cardview);
        this.openMap = itemView.findViewById(R.id.imageButton_maps);
        this.viewFlipper = itemView.findViewById(R.id.cell_content_view);
        this.sendButton = itemView.findViewById(R.id.sendButton);
        this.clearBtn = itemView.findViewById(R.id.clear_btn);
        this.signedByView = itemView.findViewById(R.id.signedBy);
        this.signimage = itemView.findViewById(R.id.signImage);
        this.infoSignedBy = itemView.findViewById(R.id.infoSignedBy);
        this.infoAdress = itemView.findViewById(R.id.infoAdress);
        this.infoDate = itemView.findViewById(R.id.infoDate);
        this.infoName = itemView.findViewById(R.id.infoName);
        this.infoTime = itemView.findViewById(R.id.infoTime);
        this.infoPostalCode = itemView.findViewById(R.id.infoPostalCode);
        this.canvas = itemView.findViewById(R.id.my_canvas);
        this.cardProgress = itemView.findViewById(R.id.card_proggress);



    }

    public void setData(ListItemInfo info) {
        adress.setText(info.getAdress());
        name.setText(info.getName());
        postalCode.setText(info.getPostalCode());
        senderId.setText(info.getSenderId());
    }
}
