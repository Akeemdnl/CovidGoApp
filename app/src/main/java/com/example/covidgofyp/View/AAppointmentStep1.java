package com.example.covidgofyp.View;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidgofyp.Model.Hospital;
import com.example.covidgofyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class AAppointmentStep1 extends Fragment implements IAllStateLoadListener,  IHospitalLoadListener{

//    RecyclerView recyclerView;
//    MaterialSpinner spinner;

    CollectionReference allStateRef;
    CollectionReference hospitalRef;

    IAllStateLoadListener iAllStateLoadListener;
    IHospitalLoadListener iHospitalLoadListener;

    @BindView(R.id.spinner)
    MaterialSpinner spinner;
    @BindView(R.id.recycler_hospital)
    RecyclerView recyclerView;

    Unbinder unbinder;
    AlertDialog dialog;

    static AAppointmentStep1 instance;

    public static AAppointmentStep1 getInstance() {
        if (instance == null)
            instance = new AAppointmentStep1();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        allStateRef = FirebaseFirestore.getInstance().collection("AllState");

        iAllStateLoadListener = this;
        iHospitalLoadListener = this;

        dialog = new SpotsDialog.Builder().setContext(getActivity()).setCancelable(false).build();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.aappointment_step_one,container,false);
       // spinner = itemView.findViewById(R.id.spinner);
       // recyclerView = itemView.findViewById(R.id.recycler_hospital);
        unbinder = ButterKnife.bind(this,itemView);
        initView();
        loadAllSalon();
        return itemView;
    }

    private void initView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new SpacesItemDecoration(4));
    }

    private void loadAllSalon() {
        allStateRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<String> list = new ArrayList<>();
                            list.add("Please choose location");
                            for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                list.add(documentSnapshot.getId());
                            }
                            iAllStateLoadListener.onAllSalonLoadSuccess(list);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iAllStateLoadListener.onAllSalonLoadFailed(e.getMessage());
            }
        });
    }

    @Override
    public void onAllSalonLoadSuccess(List<String> areaNameList) {
        //Toast.makeText(getActivity(), "onAllSalonLoadSuccess!!", Toast.LENGTH_SHORT).show();
        spinner.setItems(areaNameList);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position>0){
                    loadHospitalofState(item.toString());
                }
                else recyclerView.setVisibility(View.GONE);
            }
        });
    }

    private void loadHospitalofState(String stateName) {
        dialog.show();

        Common.state = stateName;

        hospitalRef = FirebaseFirestore.getInstance()
                .collection("AllState")
                .document(stateName)
                .collection("Hospital");

        hospitalRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Hospital> list = new ArrayList<>();
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot:task.getResult()){

                        Hospital hospital = documentSnapshot.toObject(Hospital.class);
                        hospital.setHospitalID(documentSnapshot.getId());
                        list.add(hospital);
                    }
                    iHospitalLoadListener.onHospitalLoadSuccess(list);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iHospitalLoadListener.onHospitalLoadFailed(e.getMessage());
            }
        });
    }

    @Override
    public void onAllSalonLoadFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHospitalLoadSuccess(List<Hospital> hospitalList) {
        MyHospitalAdapter adapter = new MyHospitalAdapter(getActivity(),hospitalList);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);

        dialog.dismiss();
    }

    @Override
    public void onHospitalLoadFailed(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }
}
