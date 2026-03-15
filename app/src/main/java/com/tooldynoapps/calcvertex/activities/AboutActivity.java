package com.tooldynoapps.calcvertex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.tooldynoapps.calcvertex.R;

public class AboutActivity extends BaseCalcActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setupToolbar("About");
        LinearLayout container = findViewById(R.id.aboutContainer);
        animateContainer(container);

        View heroIcon = findViewById(R.id.tvHeroIcon);
        if (heroIcon != null) {
            heroIcon.postDelayed(() ->
                    heroIcon.animate().scaleX(1.08f).scaleY(1.08f).setDuration(800)
                            .withEndAction(() -> heroIcon.animate().scaleX(1f).scaleY(1f)
                                    .setDuration(600).start()).start(), 400);
        }

        View cardPrivacy = findViewById(R.id.cardPrivacyPolicy);
        if (cardPrivacy != null) {
            cardPrivacy.setOnClickListener(v -> {
                startActivity(new Intent(this, PrivacyPolicyActivity.class));
                overridePendingTransition(R.anim.slide_up_in, R.anim.fade_out);
            });
        }
    }
}
