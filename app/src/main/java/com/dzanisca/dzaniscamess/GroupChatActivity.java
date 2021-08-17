package com.dzanisca.dzaniscamess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class GroupChatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView sendMessage;
    private EditText inputMessageET;
    private ScrollView scrollView;
    private TextView displayMessage;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;

    private String currentGroupChatName, currentUserID, currentUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        currentGroupChatName = getIntent().getExtras().get("groupName").toString();

        init();

        GetUserInfo();
    }


    private void init() {
        toolbar = findViewById(R.id.group_chat_bar_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(currentGroupChatName);

        sendMessage = findViewById(R.id.group_chat_send_message);
        inputMessageET = findViewById(R.id.input_group_chat);
        scrollView = findViewById(R.id.group_scroll_view);
        displayMessage = findViewById(R.id.group_chat_text_display);
    }

    private void GetUserInfo()
    {
        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    currentUserName = dataSnapshot.child("name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}