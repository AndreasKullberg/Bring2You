package se.iths.ateam.bring2you.Utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import se.iths.ateam.bring2you.R;
import se.iths.ateam.bring2you.Utils.ListItemInfo;

public class ListItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView adress;
    private final TextView name;
    private final TextView postalCode;
    private final TextView senderId;
    public final CardView constraintLayout;
    private View itemview;
    public Button openMap;

    public ListItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemview = itemView;
        adress = itemview.findViewById(R.id.textAdress);
        name = itemView.findViewById(R.id.textName);
        postalCode = itemView.findViewById(R.id.textPostalCode);
        senderId = itemView.findViewById(R.id.textSenderId);
        this.constraintLayout = itemView.findViewById(R.id.cardview);
        this.openMap = itemView.findViewById(R.id.button2);
    }

    public void setData(ListItemInfo info) {
        adress.setText(info.getAdress());
        name.setText(info.getName());
        postalCode.setText(info.getPostalCode());
        senderId.setText(info.getSenderId());
    }
}
