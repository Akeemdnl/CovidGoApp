package com.example.covidgofyp.View;

import android.graphics.Color;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidgofyp.Model.NgoForm;
import com.example.covidgofyp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class NgoStatusAdapter extends FirebaseRecyclerAdapter<NgoForm, NgoStatusAdapter.MyViewHolder> {

    public NgoStatusAdapter(@NonNull FirebaseRecyclerOptions<NgoForm> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NgoStatusAdapter.MyViewHolder holder, int position, @NonNull NgoForm model) {
        holder.ngoStatusDate.setText(model.getDate());
        holder.ngoStatusFullname.setText(model.getFullname());
        holder.ngoStatusPhoneNum.setText(model.getPhoneNum());
        holder.ngoStatusNric.setText(model.getNric());
        holder.ngoStatusAddress.setText(model.getAddress());
        holder.ngoStatusDescription.setText(model.getAidDescription());
        String status = model.getStatus();

        switch (status) {
            case "Processing":
                holder.imgNgoStatus.setImageResource(R.drawable.processing);
                holder.ngoStatus.setText(status);
                break;
            case "Approved":
                holder.imgNgoStatus.setImageResource(R.drawable.approve);
                holder.ngoStatus.setText(status);
                holder.ngoStatus.setTextColor(Color.GREEN);
                break;
            case "Declined":
                holder.imgNgoStatus.setImageResource(R.drawable.declined);
                holder.ngoStatus.setText(status);
                holder.ngoStatus.setTextColor(Color.RED);
                break;
        }

        holder.details.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                if(holder.hiddenView.getVisibility() == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.hiddenView.setVisibility(View.GONE);
                    holder.details.setImageResource(R.drawable.expand_more);
                }else {
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.hiddenView.setVisibility(View.VISIBLE);
                    holder.details.setImageResource(R.drawable.expand_less);
                }
            }
        });
    }

    @NonNull
    @Override
    public NgoStatusAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ngostatus_item,parent,false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView ngoStatusDate, ngoStatusFullname, ngoStatusPhoneNum, ngoStatusNric, ngoStatusAddress, ngoStatusDescription, ngoStatus;
        //TextView details;
        ImageView imgNgoStatus, details;
        LinearLayout hiddenView;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ngoStatusDate = itemView.findViewById(R.id.ngoStatusDate);
            ngoStatusFullname = itemView.findViewById(R.id.ngoStatusFullname);
            ngoStatusPhoneNum = itemView.findViewById(R.id.ngoStatusPhoneNum);
            ngoStatusNric = itemView.findViewById(R.id.ngoStatusNric);
            ngoStatusAddress = itemView.findViewById(R.id.ngoStatusAddrress);
            ngoStatusDescription = itemView.findViewById(R.id.ngoStatusDescription);
            ngoStatus = itemView.findViewById(R.id.ngoStatus);
            imgNgoStatus = itemView.findViewById(R.id.imgNgoStatus);
            details = itemView.findViewById(R.id.ngoStatusDetails);
            hiddenView = itemView.findViewById(R.id.ngoStatusHidden);
            cardView = itemView.findViewById(R.id.cvNgoStatus);
        }
    }
}
