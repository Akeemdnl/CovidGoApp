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

public class AdminNgoStatusAdapterNew extends FirebaseRecyclerAdapter<NgoForm, AdminNgoStatusAdapterNew.MyViewHolder> {

    public AdminNgoStatusAdapterNew(@NonNull FirebaseRecyclerOptions<NgoForm> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdminNgoStatusAdapterNew.MyViewHolder holder, int position, @NonNull NgoForm model) {
        holder.ngoAdminStatusDate.setText(model.getDate());
        holder.ngoAdminStatusFullname.setText(model.getFullname());
        holder.ngoAdminStatusPhoneNum.setText(model.getPhoneNum());
        holder.ngoAdminStatusNric.setText(model.getNric());
        holder.ngoAdminStatusAddress.setText(model.getAddress());
        holder.ngoAdminStatusDescription.setText(model.getAidDescription());
        String status = model.getStatus();
        holder.ngoAdminStatus.setText(status);
        holder.ngoAdminStatusUsername.setText(model.getUsername());


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
    public AdminNgoStatusAdapterNew.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_ngo_application_item,parent,false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView ngoAdminStatusDate, ngoAdminStatusFullname, ngoAdminStatusPhoneNum, ngoAdminStatusNric, ngoAdminStatusAddress, ngoAdminStatusDescription, ngoAdminStatus, ngoAdminStatusUsername;
        TextView details;
        ImageView imgNgoStatus;
        LinearLayout hiddenView;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ngoAdminStatusDate = itemView.findViewById(R.id.ngoAdminStatusDate);
            ngoAdminStatusFullname = itemView.findViewById(R.id.ngoAdminStatusFullname);
            ngoAdminStatusPhoneNum = itemView.findViewById(R.id.ngoAdminStatusPhoneNum);
            ngoAdminStatusNric = itemView.findViewById(R.id.ngoAdminStatusNric);
            ngoAdminStatusAddress = itemView.findViewById(R.id.ngoAdminStatusAddress);
            ngoAdminStatusDescription = itemView.findViewById(R.id.ngoAdminStatusDescription);
            ngoAdminStatus = itemView.findViewById(R.id.ngoAdminStatus);
            ngoAdminStatusUsername = itemView.findViewById(R.id.ngoAdminStatusUsername);
            imgNgoStatus = itemView.findViewById(R.id.imgAdminNgoStatus);
            hiddenView = itemView.findViewById(R.id.ngoStatusHiddenView);
            cardView = itemView.findViewById(R.id.cvAdminNgoStatus);
            details = itemView.findViewById(R.id.ngoAdminStatusDetails);
        }
    }
}
