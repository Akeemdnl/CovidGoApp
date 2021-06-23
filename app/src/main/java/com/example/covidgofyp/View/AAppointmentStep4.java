package com.example.covidgofyp.View;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.covidgofyp.Model.AppointmentInformation;
import com.example.covidgofyp.Model.User;
import com.example.covidgofyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class AAppointmentStep4 extends Fragment {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID, fullname, phone, userEmail;

    SimpleDateFormat simpleDateFormat;
    LocalBroadcastManager localBroadcastManager;
    Unbinder unbinder;
    Calendar calendar;
    AlertDialog dialog;

    @BindView(R.id.txt_booking_doctor_text)
    TextView txt_booking_doctor_text;
    @BindView(R.id.txt_booking_time_text)
    TextView txt_booking_time_text;
    @BindView(R.id.txt_hospital_address)
    TextView txt_hospital_address;
    @BindView(R.id.txt_hospital_name)
    TextView txt_hospital_name;
    @BindView(R.id.txt_hospital_open_hours)
    TextView txt_hospital_open_hours;
    @BindView(R.id.txt_hospital_phone)
    TextView txt_hospital_phone;
    @BindView(R.id.txt_hospital_website)
    TextView txt_hospital_website;

    @OnClick(R.id.btn_confirm)
    void confirmAppointment(){

        dialog.show();

        String startTime = Common.convertTimeSlotToString(Common.currentTimeSlot);
        String[] convertTime = startTime.split("-");

        String[] startTimeConvert = convertTime[0].split(":");
        int startHourInt = Integer.parseInt(startTimeConvert[0].trim());
        int startMinInt = Integer.parseInt(startTimeConvert[1].trim());

        Calendar appointmentDateWithourHouse = Calendar.getInstance();
        appointmentDateWithourHouse.setTimeInMillis(Common.appointmentDate.getTimeInMillis());
        appointmentDateWithourHouse.set(Calendar.HOUR_OF_DAY, startHourInt);
        appointmentDateWithourHouse.set(Calendar.MINUTE,startMinInt);

        Timestamp timestamp = new Timestamp(appointmentDateWithourHouse.getTime());

        AppointmentInformation appointmentInformation = new AppointmentInformation();

        appointmentInformation.setDone(false);
        appointmentInformation.setTimestamp(timestamp);
        appointmentInformation.setDoctorId(Common.currentDoctor.getDoctorID());
        appointmentInformation.setDoctorName(Common.currentDoctor.getName());
        appointmentInformation.setHospitalId(Common.currentHospital.getHospitalID());
        appointmentInformation.setHospitalAddress(Common.currentHospital.getAddress());
        appointmentInformation.setHospitalName(Common.currentHospital.getName());
        appointmentInformation.setTime(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
                .append(" at ")
                .append(simpleDateFormat.format(appointmentDateWithourHouse.getTime())).toString());
        appointmentInformation.setSlot(Long.valueOf(Common.currentTimeSlot));

        //customer
        appointmentInformation.setCustomerName(fullname);
        appointmentInformation.setCustomerPhone(phone);

        //submit to doctor document
        DocumentReference appointmentDate = FirebaseFirestore.getInstance()
                .collection("AllState")
                .document(Common.state)
                .collection("Hospital")
                .document(Common.currentHospital.getHospitalID())
                .collection("Doctor")
                .document(Common.currentDoctor.getDoctorID())
                .collection(Common.simpleDateFormat.format(Common.appointmentDate.getTime()))
                .document(String.valueOf(Common.currentTimeSlot));

        //write data
        appointmentDate.set(appointmentInformation)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        addToUserAppointment(appointmentInformation);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    static AAppointmentStep4 instance;

    public static AAppointmentStep4 getInstance() {
        if (instance == null)
            instance = new AAppointmentStep4();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(confirmAppointmentReceiver, new IntentFilter(Common.KEY_CONFIRM_APPOINTMENT));

        dialog = new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View itemView =  inflater.inflate(R.layout.aappointment_step_four,container,false);
        unbinder = ButterKnife.bind(this,itemView);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null){
                    fullname = userProfile.fullname;
                    phone = userProfile.email;
                    userEmail = userProfile.email;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "database error", Toast.LENGTH_LONG).show();
            }
        });
        return itemView;
    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(confirmAppointmentReceiver);
        super.onDestroy();
    }

    private void addToUserAppointment(final AppointmentInformation appointmentInformation) {
        // create new collection
        final CollectionReference userAppointment = FirebaseFirestore.getInstance()
                .collection("User")
                .document(userID)
                .collection("Appointment");

        //get current date
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        Timestamp todayTimeStamp = new Timestamp(calendar.getTime());

        userAppointment
                .whereGreaterThanOrEqualTo("timestamp", todayTimeStamp)
                .whereEqualTo("done", false)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.getResult().isEmpty()){
                            //set data
                            userAppointment.document()
                                    .set(appointmentInformation)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            if (dialog.isShowing())
                                                dialog.dismiss();

                                            addToCalendar(Common.appointmentDate, Common.convertTimeSlotToString(Common.currentTimeSlot));

                                            resetStaticData();
                                            calendar = Calendar.getInstance();
                                            Intent intent = new Intent(getContext(), MainActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(getContext(), "Application Success!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            if (dialog.isShowing())
                                                dialog.dismiss();

                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        else {
                            if (dialog.isShowing())
                                dialog.dismiss();

                            resetStaticData();
                            calendar = Calendar.getInstance();
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getContext(), "Application Success !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addToCalendar(Calendar appointmentDate, String startDate) {
        String startTime = Common.convertTimeSlotToString(Common.currentTimeSlot);
        String[] convertTime = startTime.split("-");

        String[] startTimeConvert = convertTime[0].split(":");
        int startHourInt = Integer.parseInt(startTimeConvert[0].trim());
        int startMinInt = Integer.parseInt(startTimeConvert[1].trim());

        String[] endTimeConvert = convertTime[0].split(":");
        int endHourInt = Integer.parseInt(endTimeConvert[0].trim());
        int endMinInt = Integer.parseInt(endTimeConvert[1].trim());

        Calendar startEvent = Calendar.getInstance();
        startEvent.setTimeInMillis(appointmentDate.getTimeInMillis());
        startEvent.set(Calendar.HOUR_OF_DAY,startHourInt);
        startEvent.set(Calendar.MINUTE,startMinInt);

        Calendar endEvent = Calendar.getInstance();
        endEvent.setTimeInMillis(appointmentDate.getTimeInMillis());
        endEvent.set(Calendar.HOUR_OF_DAY,endHourInt);
        endEvent.set(Calendar.MINUTE,endMinInt);

        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String startEventTime = calendarDateFormat.format(startEvent.getTime());
        String endEventTime = calendarDateFormat.format(endEvent.getTime());

        addToDeviceCalendar(startEventTime,endEventTime, "Medical Appointment", new StringBuilder("Appointment from ")
                        .append(startTime)
                        .append(" with ")
                        .append(Common.currentDoctor.getName())
                        .append(" at ")
                        .append(Common.currentHospital.getName()).toString(),
                new StringBuilder("Address: ").append(Common.currentHospital.getAddress()).toString());
    }

    private void addToDeviceCalendar(String startEventTime, String endEventTime, String title, String description, String location) {
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        try {
            Date start = calendarDateFormat.parse(startEventTime);
            Date end = calendarDateFormat.parse(endEventTime);

            ContentValues event = new ContentValues();

            event.put(CalendarContract.Events.CALENDAR_ID, getCalendar(getContext()));
            event.put(CalendarContract.Events.TITLE,title);
            event.put(CalendarContract.Events.DESCRIPTION,description);
            event.put(CalendarContract.Events.EVENT_LOCATION,location);

            //time
            event.put(CalendarContract.Events.DTSTART,start.getTime());
            event.put(CalendarContract.Events.DTEND,end.getTime());
            event.put(CalendarContract.Events.ALL_DAY,0);
            event.put(CalendarContract.Events.HAS_ALARM,1);

            String timeZone = TimeZone.getDefault().getID();
            event.put(CalendarContract.Events.EVENT_TIMEZONE,timeZone);

            Uri calendars;
            if (Build.VERSION.SDK_INT>=8)
                calendars = Uri.parse("content://com.android.calendar/events");

            else
                calendars = Uri.parse("content://calendar/events");


            getActivity().getContentResolver().insert(calendars,event);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String getCalendar(Context context) {

        String gmailIdCalendar = "";
        String[] projection ={"_id", "calendar_displayName"};
        Uri calendars = Uri.parse("content://com.android.calendar/calendars");

        ContentResolver contentResolver = context.getContentResolver();
        Cursor managedCursor = contentResolver.query(calendars,projection,null,null,null);

        if (managedCursor.moveToFirst()){
            String calname;
            int nameCol = managedCursor.getColumnIndex(projection[1]);
            int idCol = managedCursor.getColumnIndex(projection[0]);

            do {
                calname = managedCursor.getString(nameCol);

                if (calname.contains("@gmail.com")){
                    gmailIdCalendar = managedCursor.getString(idCol);
                    break;
                }
            } while (managedCursor.moveToNext());
            managedCursor.close();
        }
        return gmailIdCalendar;
    }

    private void resetStaticData() {
        Common.step = 0;
        Common.currentTimeSlot = -1;
        Common.currentHospital = null;
        Common.currentDoctor = null;
        Common.appointmentDate = Calendar.getInstance();
    }

    BroadcastReceiver confirmAppointmentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setData();
        }
    };

    private void setData() {
        txt_booking_doctor_text.setText(Common.currentDoctor.getName());
        txt_booking_time_text.setText(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
                .append(" at ")
                .append(simpleDateFormat.format(Common.appointmentDate.getTime())));

        txt_hospital_address.setText(Common.currentHospital.getAddress());
        txt_hospital_website.setText(Common.currentHospital.getWebsite());
        txt_hospital_name.setText(Common.currentHospital.getName());
        txt_hospital_phone.setText(Common.currentHospital.getPhone());
        txt_hospital_open_hours.setText(Common.currentHospital.getOpenHours());
    }

}
