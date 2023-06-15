package com.example.android.carzenia.AdminActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.carzenia.R;
import com.example.android.carzenia.SystemDatabase.DBHolder;

import com.squareup.picasso.Picasso;


public class ConnectCarsActivity extends AppCompatActivity {
    private ImageView carImageView;
    private TextView typeTextView;
    private ImageButton connectButton, lockButton, unlockButton;
    private int position;
    private Uri carImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        carImageView = (ImageView) findViewById(R.id.circleImageView);
        typeTextView = (TextView) findViewById(R.id.type_text_view);
        connectButton = (ImageButton) findViewById(R.id.connectBtn);
        lockButton = (ImageButton) findViewById(R.id.lockButton);
        unlockButton = (ImageButton) findViewById(R.id.unlockButton);

        try {
            position = getIntent().getIntExtra("position", 0);
        } catch (Exception e) {
            position = 0;
        }

        Picasso.get().load(DBHolder.carsData.get(position).getImageUri())
                .placeholder(R.mipmap.ic_launcher_round).into(carImageView);
        typeTextView.setText(DBHolder.carsData.get(position).getType());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "My Channel";
            String channelDescription = "My Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("111", channelName, importance);
            channel.setDescription(channelDescription);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inside your button's click listener
                Intent notificationIntent = new Intent(ConnectCarsActivity.this, ConnectCarsActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(ConnectCarsActivity.this, 0, notificationIntent, 0);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(ConnectCarsActivity.this, "111")
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("Connection Success")
                        .setContentText("Mobile phone was connected to Car successfully")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setFullScreenIntent(pendingIntent, true);

                // Create an intent for when the notification is clicked

                builder.setContentIntent(pendingIntent);

                // Show the notification
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ConnectCarsActivity.this);
                int notificationId = 1; // Assign a unique ID for the notification
                notificationManager.notify(notificationId, builder.build());
            }
        });

        lockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ConnectCarsActivity.this, "Engine was locked successfully.", Toast.LENGTH_SHORT).show();
            }
        });

        unlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ConnectCarsActivity.this, "Engine was unlocked successfully.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}