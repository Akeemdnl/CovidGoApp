package com.example.covidgofyp.View;

import android.content.Context;
import android.content.DialogInterface;
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
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidgofyp.Model.User;
import com.example.covidgofyp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminUserAdapter extends FirebaseRecyclerAdapter<User, AdminUserAdapter.MyViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public AdminUserAdapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdminUserAdapter.MyViewHolder holder, int position, @NonNull User model) {
        holder.username.setText(model.getUsername());
        holder.fullname.setText(model.getFullname());
        holder.email.setText(model.getEmail());
        holder.type.setText(model.getType());
        holder.status.setText(model.getStatus());
        holder.userId.setText(model.getUserId());
        holder.phone.setText(model.getPhone());
        String status = model.getStatus();

        if(status.equals("Disabled")){
            holder.btnActivate.setVisibility(View.VISIBLE);
            holder.btnDisable.setVisibility(View.GONE);
            holder.username.setTextColor(Color.RED);
            holder.status.setTextColor(Color.RED);
        }else if (status.equals("Active")){
            holder.btnActivate.setVisibility(View.GONE);
            holder.btnDisable.setVisibility(View.VISIBLE);
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

        holder.btnDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(holder.context);
                builder.setTitle("Disable")
                        .setBackground(holder.context.getResources().getDrawable(R.drawable.dialog_background))
                        .setMessage("Disable user?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Disable", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String userId = model.userId;
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                                ref.child("status").setValue("Disabled");
                                Snackbar.make(holder.layout, "User is disabled", Snackbar.LENGTH_INDEFINITE)
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

        holder.btnActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(holder.context);
                builder.setTitle("Activate")
                        .setBackground(holder.context.getResources().getDrawable(R.drawable.dialog_background))
                        .setMessage("Activate user?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Activate", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String userId = model.userId;
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                                ref.child("status").setValue("Active");
                                Snackbar.make(holder.layout, "User is Activated", Snackbar.LENGTH_INDEFINITE)
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

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(holder.context);
                builder.setTitle("Delete")
                        .setBackground(holder.context.getResources().getDrawable(R.drawable.dialog_background))
                        .setMessage("Delete user?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String userId = model.userId;
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                                //Remove user and applications
                                ref.child(userId).removeValue();
                                ref = FirebaseDatabase.getInstance().getReference("Sumbangan");
                                ref.child(userId).removeValue();
                                Snackbar.make(holder.layout, "User Deleted", Snackbar.LENGTH_INDEFINITE)
                                        .setAction("DISMISS", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //close snackbar
//                                                fragment = new AdminUserFragment();
//                                                FragmentManager fragmentManager = ((AppCompatActivity)holder.context).getSupportFragmentManager();
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
    }

    @NonNull
    @Override
    public AdminUserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_user_item, parent, false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username, fullname, email, status, type, userId, phone;
        CardView cardView;
        LinearLayout hiddenView;
        ImageView details;
        Button btnDisable, btnDelete, btnActivate;
        Context context;
        ConstraintLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.adminUsername);
            fullname = itemView.findViewById(R.id.adminUserFullname);
            email = itemView.findViewById(R.id.adminUserEmail);
            type = itemView.findViewById(R.id.adminUserType);
            status = itemView.findViewById(R.id.adminUserStatus);
            phone = itemView.findViewById(R.id.adminUserPhone);
            cardView = itemView.findViewById(R.id.cvAdminUsers);
            hiddenView = itemView.findViewById(R.id.adminUserHiddenView);
            details = itemView.findViewById(R.id.adminUsersDetail);
            btnDisable = itemView.findViewById(R.id.btnDisable);
            btnDelete= itemView.findViewById(R.id.btnDelete);
            btnActivate = itemView.findViewById(R.id.btnActivate);
            userId = itemView.findViewById(R.id.adminUserId);
            layout = itemView.findViewById(R.id.adminUsersLayout);
            context = itemView.getContext();
        }

    }
}
