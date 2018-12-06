package se.iths.ateam.bring2you.Utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import se.iths.ateam.bring2you.R;


public class ListItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView adress;
    private final TextView name;
    private final TextView postalCode;
    private final TextView senderId;
    public final CardView constraintLayout;
    private View itemview;
    //public Button openMap;
    public ImageButton openMap;

    public ListItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemview = itemView;
        adress = itemview.findViewById(R.id.textAdress);
        name = itemView.findViewById(R.id.textName);
        postalCode = itemView.findViewById(R.id.textPostalCode);
        senderId = itemView.findViewById(R.id.textSenderId);
        this.constraintLayout = itemView.findViewById(R.id.cardview);
        this.openMap = itemView.findViewById(R.id.imageButton_maps);
    }

    public void setData(ListItemInfo info) {
        adress.setText(info.getAdress());
        name.setText(info.getName());
        postalCode.setText(info.getPostalCode());
        senderId.setText(info.getSenderId());
    }
}
