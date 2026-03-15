package com.tooldynoapps.calcvertex.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tooldynoapps.calcvertex.R;
import com.tooldynoapps.calcvertex.utils.DataManager;

public abstract class BaseCalcActivity extends AppCompatActivity {

    protected DataManager dm;

    @Override
    public void setContentView(int layoutResID) {
        EdgeToEdge.enable(this);
        super.setContentView(layoutResID);
        dm = new DataManager(this);
        View root = findViewById(android.R.id.content);
        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }

    protected void setupToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar == null) return;
        TextView tvTitle = toolbar.findViewById(R.id.toolbarTitle);
        TextView tvBack  = toolbar.findViewById(R.id.toolbarBack);
        if (tvTitle != null) tvTitle.setText(title);
        if (tvBack  != null) {
            tvBack.setOnClickListener(v -> {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.slide_down_out);
            });
        }
    }

    protected void showResult(TextView tvResult, TextView tvResultBox, String result, int accentColor) {
        if (tvResult != null) {
            tvResult.setText(result);
            tvResult.setTextColor(accentColor);
            tvResult.animate().scaleX(0.9f).scaleY(0.9f).setDuration(80)
                    .withEndAction(() -> tvResult.animate().scaleX(1f).scaleY(1f).setDuration(150).start()).start();
        }
        if (tvResultBox != null) {
            tvResultBox.setText(result);
            tvResultBox.setTextColor(accentColor);
        }
    }

    protected void setupCopyButton(View btnCopy, TextView tvResult) {
        if (btnCopy == null || tvResult == null) return;
        btnCopy.setOnClickListener(v -> {
            String text = tvResult.getText().toString();
            if (!text.isEmpty() && !text.equals("—")) {
                ClipboardManager cb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cb.setPrimaryClip(ClipData.newPlainText("result", text));
                Toast.makeText(this, "✓ Copied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void animateContainer(View container) {
        if (container == null) return;
        container.setAlpha(0f);
        container.setTranslationY(30f);
        container.animate().alpha(1f).translationY(0f).setDuration(400)
                .setInterpolator(new android.view.animation.DecelerateInterpolator()).start();
    }

    protected void setupBottomNav(int selectedIdx) {
        int[] ids = {R.id.navHome, R.id.navFinance, R.id.navHealth, R.id.navMath, R.id.navHistory};
        Class<?>[] targets = {HomeActivity.class, FinanceActivity.class,
                HealthActivity.class, MathActivity.class, HistoryActivity.class};

        for (int i = 0; i < ids.length; i++) {
            View nav = findViewById(ids[i]);
            if (nav == null) continue;
            TextView icon  = (TextView) ((LinearLayout) nav).getChildAt(0);
            TextView label = (TextView) ((LinearLayout) nav).getChildAt(1);
            boolean sel = (i == selectedIdx);
            if (label != null) label.setTextColor(sel ? 0xFF00D4AA : 0xFF4A5568);
            if (icon  != null) icon.setAlpha(sel ? 1f : 0.5f);
            if (!sel) {
                final Class<?> target = targets[i];
                nav.setOnClickListener(v -> {
                    startActivity(new android.content.Intent(this, target));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                });
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.slide_down_out);
        return true;
    }
}