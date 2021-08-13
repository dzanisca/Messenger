package com.dzanisca.dzaniscamess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;


public class GroupChatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView sendMessage;
    private EditText inputMessageET;
    private ScrollView scrollView;
    private TextView displayMessage;

    private String currentGroupChatName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        currentGroupChatName = getIntent().getExtras().get("groupName").toString();

        init();
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
}