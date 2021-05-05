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

public class NgoStatusAdapter extends RecyclerView.Adapter<NgoStatusAdapter.MyViewHolder> {

    private Context context;
    private List<NgoForm> ngoFormList;

    public NgoStatusAdapter(Context context, List<NgoForm> ngoFormList) {
        this.context = context;
        this.ngoFormList = ngoFormList;
    }

    @NonNull
    @Override
    public NgoStatusAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.ngostatus_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NgoStatusAdapter.MyViewHolder holder, int position) {
        holder.ngoStatusDate.setText(ngoFormList.get(position).getDate());
        holder.ngoStatusFullname.setText(ngoFormList.get(position).getFullname());
        holder.ngoStatusPhoneNume.setText(ngoFormList.get(position).getPhoneNum());
        holder.ngoStatusNric.setText(ngoFormList.get(position).getNric());
        holder.ngoStatusAddress.setText(ngoFormList.get(position).getAddress());
        holder.ngoStatusDescription.setText(ngoFormList.get(position).getAidDescription());
        holder.ngoStatus.setText(ngoFormList.get(position).getStatus());
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
