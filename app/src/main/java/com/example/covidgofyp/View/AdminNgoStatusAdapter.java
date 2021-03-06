package com.example.covidgofyp.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidgofyp.Model.NgoForm;
import com.example.covidgofyp.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        holder.ngoAdminStatusUsername.setText(ngoFormList.get(position).getUsername());
        holder.ngoEmail.setText(ngoFormList.get(position).getEmail());
        holder.ngoMarital.setText(ngoFormList.get(position).getMarital());
        holder.ngoOccupation.setText(ngoFormList.get(position).getOccupation());
        String salary = ngoFormList.get(position).getSalary();

        if (salary.equals("1")){
            holder.ngoSalary.setText("< RM1000");
        }else if (salary.equals("2")){
            holder.ngoSalary.setText("> RM1000 and < RM3000");
        }else if (salary.equals("3")){
            holder.ngoSalary.setText("> RM3000");
        }

        String status = ngoFormList.get(position).getStatus();
        holder.ngoAdminStatus.setText(status);
        if(status.equals("Processing")){
            holder.imgNgoStatus.setImageResource(R.drawable.hourglass);
        }else if (status.equals("Approved")){
            holder.imgNgoStatus.setImageResource(R.drawable.approve);
            holder.btnApprove.setVisibility(View.GONE);
            holder.btnDecline.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.ngoAdminStatus.setTextColor(Color.GREEN);
        }else if (status.equals("Declined")){
            holder.imgNgoStatus.setImageResource(R.drawable.declined);
            holder.btnApprove.setVisibility(View.GONE);
            holder.btnDecline.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.ngoAdminStatus.setTextColor(Color.RED);
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

        holder.btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setTitle("Approve")
                        .setBackground(context.getResources().getDrawable(R.drawable.dialog_background))
                        .setMessage("Approve the application?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String userId = ngoFormList.get(position).getUserId();
                                String key = ngoFormList.get(position).getKey();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Sumbangan").child(userId).child(key);
                                ref.child("status").setValue("Approved");
                                ngoFormList.get(position).setStatus("Approved");
                                notifyItemChanged(position);
                                Snackbar.make(holder.layout, "Application approved", Snackbar.LENGTH_INDEFINITE)
                                        .setAction("DISMISS", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //close snackbar
                                            }
                                        }).show();
                            }
                        }).show();
            }
        });

        holder.btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setTitle("Decline")
                        .setBackground(context.getResources().getDrawable(R.drawable.dialog_background))
                        .setMessage("Decline the application?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Decline", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String userId = ngoFormList.get(position).getUserId();
                                String key = ngoFormList.get(position).getKey();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Sumbangan").child(userId).child(key);
                                ref.child("status").setValue("Declined");
                                ngoFormList.get(position).setStatus("Declined");
                                notifyItemChanged(position);
                                Snackbar.make(holder.layout, "Application declined", Snackbar.LENGTH_INDEFINITE)
                                        .setAction("DISMISS", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //close snackbar
//                                                fragment = new AdminNgoApplicationFragment();
//                                                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
//                                                fragmentManager.beginTransaction()
//                                                        .setReorderingAllowed(true)
//                                                        .replace(R.id.containerAdmin, fragment,null )
//                                                        .addToBackStack(null)
//                                                        .commit();
                                            }
                                        }).show();
                            }
                        }).show();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setTitle("Delete")
                        .setBackground(context.getResources().getDrawable(R.drawable.dialog_background))
                        .setMessage("Are you sure")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String userId = ngoFormList.get(position).getUserId();
                                String key = ngoFormList.get(position).getKey();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Sumbangan").child(userId);
                                ref.child(key).removeValue();
                                ngoFormList.remove(position);
                                notifyItemChanged(position);
                                Snackbar.make(holder.layout, "Application deleted", Snackbar.LENGTH_INDEFINITE)
                                        .setAction("DISMISS", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        }).show();
                            }
                        }).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return ngoFormList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ngoAdminStatusDate, ngoAdminStatusFullname, ngoAdminStatusPhoneNum, ngoAdminStatusNric, ngoAdminStatusAddress, ngoAdminStatusDescription, ngoAdminStatus, ngoAdminStatusUsername;
        TextView ngoEmail, ngoMarital, ngoOccupation, ngoSalary;
        ImageView imgNgoStatus, details;
        LinearLayout hiddenView;
        CardView cardView;
        Button btnApprove, btnDecline, btnDelete;
        ConstraintLayout layout;
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
            ngoEmail = itemView.findViewById(R.id.ngoAdminStatusEmail);
            ngoMarital = itemView.findViewById(R.id.ngoAdminStatusMarital);
            ngoOccupation = itemView.findViewById(R.id.ngoAdminStatusOccupation);
            ngoSalary = itemView.findViewById(R.id.ngoAdminStatusSalary);
            imgNgoStatus = itemView.findViewById(R.id.imgAdminNgoStatus);
            hiddenView = itemView.findViewById(R.id.ngoStatusHiddenView);
            cardView = itemView.findViewById(R.id.cvAdminNgoStatus);
            details = itemView.findViewById(R.id.ngoAdminStatusDetails);
            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnDecline = itemView.findViewById(R.id.btnDecline);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            layout = itemView.findViewById(R.id.ngoApplicationLayout);
        }
    }
}
