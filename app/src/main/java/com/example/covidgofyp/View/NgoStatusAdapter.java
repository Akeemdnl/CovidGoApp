package com.example.covidgofyp.View;

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
        holder.ngoStatusPhoneNume.setText(model.getPhoneNum());
        holder.ngoStatusNric.setText(model.getNric());
        holder.ngoStatusAddress.setText(model.getAddress());
        holder.ngoStatusDescription.setText(model.getAidDescription());
        String status = model.getStatus();
        holder.ngoStatus.setText(status);


        if(status.equals("Processing")){
            holder.imgNgoStatus.setImageResource(R.drawable.processing);
        }else if (status.equals("Approved")){
            holder.imgNgoStatus.setImageResource(R.drawable.approve);
        }else if (status.equals("Declined")){
            holder.imgNgoStatus.setImageResource(R.drawable.declined);
        }

        holder.details.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if(holder.hiddenView.getVisibility() == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.hiddenView.setVisibility(View.GONE);
                }else {
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.hiddenView.setVisibility(View.VISIBLE);
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
        TextView ngoStatusDate, ngoStatusFullname, ngoStatusPhoneNume, ngoStatusNric, ngoStatusAddress, ngoStatusDescription, ngoStatus;
        TextView details;
        ImageView imgNgoStatus;
        LinearLayout hiddenView;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ngoStatusDate = itemView.findViewById(R.id.ngoStatusDate);
            ngoStatusFullname = itemView.findViewById(R.id.ngoStatusFullname);
            ngoStatusPhoneNume = itemView.findViewById(R.id.ngoStatusPhoneNum);
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
