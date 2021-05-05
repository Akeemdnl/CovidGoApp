package com.example.covidgofyp.View;

import android.content.Context;
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

import java.util.List;

public class AdminNgoStatusAdapter extends RecyclerView.Adapter<AdminNgoStatusAdapter.MyViewHolder> {

    Context context;
    List<NgoForm> ngoFormList;

    public AdminNgoStatusAdapter(Context context, List<NgoForm> ngoFormList) {
        this.context = context;
        this.ngoFormList = ngoFormList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.admin_ngo_application_item, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.ngoAdminStatusDate.setText(ngoFormList.get(position).getDate());
        holder.ngoAdminStatusFullname.setText(ngoFormList.get(position).getFullname());
        holder.ngoAdminStatusPhoneNum.setText(ngoFormList.get(position).getPhoneNum());
        holder.ngoAdminStatusNric.setText(ngoFormList.get(position).getNric());
        holder.ngoAdminStatusAddress.setText(ngoFormList.get(position).getAddress());
        holder.ngoAdminStatusDescription.setText(ngoFormList.get(position).getAidDescription());
        holder.ngoAdminStatus.setText(ngoFormList.get(position).getStatus());
        holder.ngoAdminStatusUsername.setText(ngoFormList.get(position).getUsername());
        String status = ngoFormList.get(position).getStatus();

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

    @Override
    public int getItemCount() {
        return ngoFormList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
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
