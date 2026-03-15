package com.burgaynet.calcvertex.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.burgaynet.calcvertex.R;

public class AboutActivity extends BaseCalcActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("About");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        LinearLayout container = findViewById(R.id.aboutContainer);
        animateContainer(container);

        View heroIcon = findViewById(R.id.tvHeroIcon);
        if (heroIcon != null) {
            heroIcon.postDelayed(() ->
                heroIcon.animate().scaleX(1.08f).scaleY(1.08f).setDuration(800)
                    .withEndAction(() -> heroIcon.animate().scaleX(1f).scaleY(1f)
                            .setDuration(600).start()).start(), 400);
        }
    }
}
