package com.tooldynoapps.calcvertex.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.tooldynoapps.calcvertex.R;
import com.tooldynoapps.calcvertex.utils.AppConstants;
import com.tooldynoapps.calcvertex.utils.CalcHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class BmiCalcActivity extends BaseCalcActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_generic);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("BMI Calculator");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        animateContainer(findViewById(R.id.calcContainer));
        setLabel(R.id.tvLabel1, "Weight (kg)");
        setLabel(R.id.tvLabel2, "Height (cm)");
        setHint(R.id.etInput1, "e.g. 70");
        setHint(R.id.etInput2, "e.g. 175");
        hide(R.id.input3Row); hide(R.id.input4Row);
        setToolHeader("⚖️", "BMI Calculator", AppConstants.COLOR_HEALTH);

        TextInputEditText et1 = findViewById(R.id.etInput1);
        TextInputEditText et2 = findViewById(R.id.etInput2);
        TextView tvResult     = findViewById(R.id.tvResult);
        MaterialButton btnCalc  = findViewById(R.id.btnCalculate);
        MaterialButton btnClear = findViewById(R.id.btnClear);
        setupCopyButton(findViewById(R.id.btnCopy), tvResult);

        btnCalc.setOnClickListener(v -> {
            try {
                double w = Double.parseDouble(et1.getText().toString().trim());
                double h = Double.parseDouble(et2.getText().toString().trim());
                double bmi = CalcHelper.calcBmi(w, h);
                String cat = CalcHelper.getBmiCategory(bmi);
                int color = bmi < 18.5 ? 0xFF64B5F6 : bmi < 25 ? 0xFF95D5B2 : bmi < 30 ? 0xFFFFBE0B : 0xFFFF6B6B;
                String res = "BMI: " + CalcHelper.fmt(bmi) + "\nCategory: " + cat
                        + "\n\nHealthy range: 18.5 – 24.9";
                showResult(tvResult, null, res, color);
                dm.saveHistory("BMI Calculator", AppConstants.CAT_HEALTH,
                        "W=" + w + "kg H=" + h + "cm",
                        "BMI " + CalcHelper.fmt(bmi) + " (" + cat + ")", AppConstants.COLOR_HEALTH);
            } catch (Exception e) { Toast.makeText(this, "Enter valid values", Toast.LENGTH_SHORT).show(); }
        });
        btnClear.setOnClickListener(v -> { et1.setText(""); et2.setText(""); tvResult.setText("—"); });
    }
    private void setLabel(int id, String t) { TextView tv = findViewById(id); if (tv != null) tv.setText(t); }
    private void setHint(int id, String h)  { TextInputEditText et = findViewById(id); if (et != null) et.setHint(h); }
    private void hide(int id)               { android.view.View v = findViewById(id); if (v != null) v.setVisibility(android.view.View.GONE); }
    private void setToolHeader(String e, String t, int c) {
        TextView te = findViewById(R.id.tvCalcEmoji); if (te != null) te.setText(e);
        TextView tt = findViewById(R.id.tvCalcTitle); if (tt != null) { tt.setText(t); tt.setTextColor(c); }
    }
}
