package com.tooldynoapps.calcvertex.activities;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tooldynoapps.calcvertex.R;
import com.tooldynoapps.calcvertex.utils.AppConstants;

public class EverydayActivity extends BaseCalcActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_hub);
        setupToolbar("Everyday Tools");
        animateContainer(findViewById(R.id.hubContainer));
        setupTools();
        setupBottomNav(-1);
    }

    private void setupTools() {
        findViewById(R.id.tool1).setOnClickListener(v -> go(AgeCalcActivity.class));
        findViewById(R.id.tool2).setOnClickListener(v -> go(DateDiffActivity.class));
        findViewById(R.id.tool3).setOnClickListener(v -> go(UnitConverterActivity.class));

        setCard(R.id.tool1, "🎂", "Age Calculator",   "Exact age from DOB");
        setCard(R.id.tool2, "📅", "Date Difference",  "Days between dates");
        setCard(R.id.tool3, "📏", "Unit Converter",   "Length, weight, temp");
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
        int color = AppConstants.COLOR_EVERYDAY;
        GradientDrawable bg = new GradientDrawable();
        bg.setShape(GradientDrawable.RECTANGLE);
        bg.setCornerRadius(14 * getResources().getDisplayMetrics().density);
        bg.setColor((0x22 << 24) | ((color >> 16 & 0xFF) << 16) | ((color >> 8 & 0xFF) << 8) | (color & 0xFF));
        card.findViewById(R.id.toolIconContainer).setBackground(bg);
    }

    private void hideTool(int id) { View v = findViewById(id); if (v != null) v.setVisibility(View.GONE); }
}
