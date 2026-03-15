package com.burgaynet.calcvertex.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.burgaynet.calcvertex.R;
import com.burgaynet.calcvertex.utils.AppConstants;
import com.burgaynet.calcvertex.utils.CalcHelper;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

public class AgeCalcActivity extends BaseCalcActivity {

    private int selYear = 1990, selMonth = 1, selDay = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_date);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Age Calculator");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        animateContainer(findViewById(R.id.calcContainer));
        setToolHeader("🎂", "Age Calculator", AppConstants.COLOR_EVERYDAY);

        TextView tvDate1   = findViewById(R.id.tvDate1);
        TextView tvResult  = findViewById(R.id.tvResult);
        MaterialButton btnCalc  = findViewById(R.id.btnCalculate);
        MaterialButton btnClear = findViewById(R.id.btnClear);
        hide(R.id.date2Row);
        setDateLabel(R.id.tvDateLabel1, "Date of Birth");
        setupCopyButton(findViewById(R.id.btnCopy), tvResult);

        tvDate1.setText("Tap to select DOB");
        tvDate1.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (dp, y, m, d) -> {
                selYear = y; selMonth = m + 1; selDay = d;
                tvDate1.setText(String.format("%04d-%02d-%02d", y, m + 1, d));
            }, selYear, selMonth - 1, selDay).show();
        });

        btnCalc.setOnClickListener(v -> {
            String age = CalcHelper.calcAge(selYear, selMonth, selDay);
            showResult(tvResult, null, "Age: " + age, AppConstants.COLOR_EVERYDAY);
            dm.saveHistory("Age Calculator", AppConstants.CAT_EVERYDAY,
                    "DOB: " + selYear + "-" + selMonth + "-" + selDay, age, AppConstants.COLOR_EVERYDAY);
        });
        btnClear.setOnClickListener(v -> { tvDate1.setText("Tap to select DOB"); tvResult.setText("—"); });
    }

    private void setToolHeader(String e, String t, int c) {
        TextView te = findViewById(R.id.tvCalcEmoji); if (te != null) te.setText(e);
        TextView tt = findViewById(R.id.tvCalcTitle); if (tt != null) { tt.setText(t); tt.setTextColor(c); }
    }
    private void setDateLabel(int id, String t) { TextView tv = findViewById(id); if (tv != null) tv.setText(t); }
    private void hide(int id) { android.view.View v = findViewById(id); if (v != null) v.setVisibility(android.view.View.GONE); }
}
