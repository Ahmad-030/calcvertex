package com.tooldynoapps.calcvertex.activities;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.tooldynoapps.calcvertex.R;

public class PrivacyPolicyActivity extends BaseCalcActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        setupToolbar("Privacy Policy");
        LinearLayout container = findViewById(R.id.privacyContainer);
        animateContainer(container);
    }
}
