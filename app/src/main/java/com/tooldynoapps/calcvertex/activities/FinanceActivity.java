package com.tooldynoapps.calcvertex.activities;

import android.content.Intent;
import android.os.Bundle;

import com.tooldynoapps.calcvertex.R;

public class FinanceActivity extends BaseCalcActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_hub);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Finance");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        animateContainer(findViewById(R.id.hubContainer));
        setupTools();
        setupBottomNav(1);
    }

    private void setupTools() {
        findViewById(R.id.tool1).setOnClickListener(v -> go(EmiCalcActivity.class));
        findViewById(R.id.tool2).setOnClickListener(v -> go(ProfitLossActivity.class));
        findViewById(R.id.tool3).setOnClickListener(v -> go(GstCalcActivity.class));
        findViewById(R.id.tool4).setOnClickListener(v -> go(InterestCalcActivity.class));
        findViewById(R.id.tool5).setOnClickListener(v -> go(CurrencyCalcActivity.class));

        setToolCard(R.id.tool1, "💳", "EMI Calculator",      "Loan monthly payment");
        setToolCard(R.id.tool2, "📈", "Profit & Loss",       "Cost vs selling price");
        setToolCard(R.id.tool3, "🧾", "GST / Tax",           "Tax on amount");
        setToolCard(R.id.tool4, "🏦", "Interest",            "Simple & compound");
        setToolCard(R.id.tool5, "💱", "Currency Convert",    "Offline rate convert");
        hideTool(R.id.tool6);
    }

    private void go(Class<?> cls) {
        startActivity(new Intent(this, cls));
        overridePendingTransition(R.anim.slide_up_in, R.anim.fade_out);
    }

    private void setToolCard(int id, String emoji, String title, String sub) {
        android.view.View card = findViewById(id);
        if (card == null) return;
        ((android.widget.TextView) card.findViewById(R.id.tvToolEmoji)).setText(emoji);
        ((android.widget.TextView) card.findViewById(R.id.tvToolTitle)).setText(title);
        ((android.widget.TextView) card.findViewById(R.id.tvToolSub)).setText(sub);
        int color = com.tooldynoapps.calcvertex.utils.AppConstants.COLOR_FINANCE;
        android.graphics.drawable.GradientDrawable bg = new android.graphics.drawable.GradientDrawable();
        bg.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
        bg.setCornerRadius(14 * getResources().getDisplayMetrics().density);
        int r2 = (color >> 16) & 0xFF, g2 = (color >> 8) & 0xFF, b2 = color & 0xFF;
        bg.setColor((0x22 << 24) | (r2 << 16) | (g2 << 8) | b2);
        card.findViewById(R.id.toolIconContainer).setBackground(bg);
    }

    private void hideTool(int id) {
        android.view.View v = findViewById(id);
        if (v != null) v.setVisibility(android.view.View.GONE);
    }
}
