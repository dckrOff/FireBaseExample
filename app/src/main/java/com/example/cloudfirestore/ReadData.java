package com.example.cloudfirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

public class ReadData extends AppCompatActivity {

    Button read, mainBtn;
    FirebaseFirestore db;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch male_female;
    String switchstatus = "";
    static final String TAG = "Read Data Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);

        db = FirebaseFirestore.getInstance();
        read = findViewById(R.id.readbtn);
        male_female = findViewById(R.id.male_female);
        mainBtn = findViewById(R.id.mainBtn);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (male_female.isChecked()) {
                    switchstatus = "Female";
                } else {
                    switchstatus = "Male";
                }
                db.collection("user")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ReadData.this, "Succesfull", Toast.LENGTH_SHORT).show();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.e(TAG, document.getId() + " => " + document.getData());
                                    }
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });
                mainBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ReadData.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }
}