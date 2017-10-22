package com.example.mgigena.sendtoapp;

import android.content.Intent;

import java.util.Calendar;
import java.util.List;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText mEditText1, mEditText2;
    Button mButton1, mButton2, mButton3, mButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText1 = (EditText) findViewById(R.id.editText1);
        mButton1 = (Button) findViewById(R.id.button1);

        mEditText2 = (EditText) findViewById(R.id.editText3);
        mButton2 = (Button) findViewById(R.id.button2);

        mButton3 = (Button) findViewById(R.id.button3);

        mButton4 = (Button) findViewById(R.id.button4);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mEditText1.getText().toString();

                Uri number = Uri.parse(phone);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                if (checkIntent(callIntent)) {
                    startActivity(callIntent);
                }
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mEditText2.getText().toString();

                Uri webpage = Uri.parse(url);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                String chooserTitle = getResources().getString(R.string.webChooserTitle);
                Intent chooser = Intent.createChooser(webIntent, chooserTitle);

                if (webIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setType("text/plan");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"m.gigena@globant.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");

                if (checkIntent(emailIntent)) {
                    startActivity(emailIntent);
                }
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calendarIntent = new Intent(Intent.ACTION_INSERT,
                        CalendarContract.Events.CONTENT_URI);
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(2017, 9, 21, 23, 45);
                Calendar endTime = Calendar.getInstance();
                endTime.set(2017, 9, 21, 23, 55);

                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        beginTime.getTimeInMillis());
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                        endTime.getTimeInMillis());
                calendarIntent.putExtra(CalendarContract.Events.TITLE, "Ninja Class");
                calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret Dojo");

                if (checkIntent(calendarIntent)) {
                    startActivity(calendarIntent);
                }
            }
        });
    }

    private boolean checkIntent(Intent intent) {
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return activities.size() > 0;
    }
}
