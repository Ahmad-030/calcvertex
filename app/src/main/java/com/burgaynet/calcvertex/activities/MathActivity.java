package com.burgaynet.calcvertex.activities;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.burgaynet.calcvertex.R;
import com.burgaynet.calcvertex.utils.AppConstants;

public class MathActivity extends BaseCalcActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_hub);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Math & Science");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        animateContainer(findViewById(R.id.hubContainer));
        setupTools();
        setupBottomNav(3);
    }

    private void setupTools() {
        findViewById(R.id.tool1).setOnClickListener(v -> go(AreaCalcActivity.class));
        findViewById(R.id.tool2).setOnClickListener(v -> go(PercentCalcActivity.class));
        findViewById(R.id.tool3).setOnClickListener(v -> go(BaseConverterActivity.class));

        setCard(R.id.tool1, "📐", "Area & Volume",    "Shapes calculator");
        setCard(R.id.tool2, "📊", "Percentage",       "%, change, ratio");
        setCard(R.id.tool3, "🔢", "Base Converter",   "Binary, Hex, Octal");
        hideTool(R.id.tool4); hideTool(R.id.tool5); hideTool(R.id.tool6);
    }

    private void go(Class<?> cls) {
        startActivity(new Intent(this, cls));
        overridePendingTransition(R.anim.slide_up_in, R.anim.fade_out);
    }

    private void setCard(int id, String emoji, String title, String sub) {
        View card = findViewById(id); if (card == null) return;
        ((TextView) card.findViewById(R.id.tvToolEmoji)).setText(emoji);
        ((TextView) card.findViewById(R.id.tvToolTitle)).setText(title);
        ((TextView) card.findViewById(R.id.tvToolSub)).setText(sub);
        int color = AppConstants.COLOR_MATH;
        GradientDrawable bg = new GradientDrawable();
        bg.setShape(GradientDrawable.RECTANGLE);
        bg.setCornerRadius(14 * getResources().getDisplayMetrics().density);
        bg.setColor((0x22 << 24) | ((color >> 16 & 0xFF) << 16) | ((color >> 8 & 0xFF) << 8) | (color & 0xFF));
        card.findViewById(R.id.toolIconContainer).setBackground(bg);
    }

    private void hideTool(int id) { View v = findViewById(id); if (v != null) v.setVisibility(View.GONE); }
}
