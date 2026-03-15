package com.tooldynoapps.calcvertex.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.tooldynoapps.calcvertex.R;

public class PrivacyPolicyActivity extends BaseCalcActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Privacy Policy");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        LinearLayout container = findViewById(R.id.privacyContainer);
        animateContainer(container);
    }
}