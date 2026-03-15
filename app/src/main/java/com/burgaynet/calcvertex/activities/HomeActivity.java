package com.burgaynet.calcvertex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.burgaynet.calcvertex.R;
import com.burgaynet.calcvertex.utils.DataManager;

public class HomeActivity extends BaseCalcActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        animateChildren();
        setupCategoryCards();
        setupBottomNav(0);
    }

    private void setupCategoryCards() {
        findViewById(R.id.cardFinance).setOnClickListener(v -> launch(FinanceActivity.class));
        findViewById(R.id.cardHealth).setOnClickListener(v -> launch(HealthActivity.class));
        findViewById(R.id.cardMath).setOnClickListener(v -> launch(MathActivity.class));
        findViewById(R.id.cardEveryday).setOnClickListener(v -> launch(EverydayActivity.class));
        findViewById(R.id.cardHistory).setOnClickListener(v -> launch(HistoryActivity.class));
        findViewById(R.id.cardAbout).setOnClickListener(v -> launch(AboutActivity.class));
    }

    private void launch(Class<?> cls) {
        startActivity(new Intent(this, cls));
        overridePendingTransition(R.anim.slide_up_in, R.anim.fade_out);
    }

    private void animateChildren() {
        LinearLayout container = findViewById(R.id.homeContainer);
        if (container == null) return;
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            child.setAlpha(0f);
            child.setTranslationY(40f);
            child.animate().alpha(1f).translationY(0f)
                    .setDuration(400).setStartDelay(i * 80L)
                    .setInterpolator(new android.view.animation.DecelerateInterpolator(2f))
                    .start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataManager dm2 = new DataManager(this);
        android.widget.TextView tvCount = findViewById(R.id.tvCalcCount);
        if (tvCount != null) {
            int count = dm2.getHistory().size();
            tvCount.setText(count + " calculation" + (count == 1 ? "" : "s") + " in history");
        }
    }
}
