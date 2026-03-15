package com.tooldynoapps.calcvertex.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tooldynoapps.calcvertex.R;
import com.tooldynoapps.calcvertex.utils.AppConstants;
import com.tooldynoapps.calcvertex.utils.CalcHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class WaterCalcActivity extends BaseCalcActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_spinner);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Water Intake");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        animateContainer(findViewById(R.id.calcContainer));
        setToolHeader("💧", "Water Intake", AppConstants.COLOR_HEALTH);
        setLabel(R.id.tvLabel1, "Weight (kg)");
        setLabel(R.id.tvSpinnerLabel, "Activity Level");
        setHint(R.id.etInput1, "e.g. 70");
        hide(R.id.input2Row);

        Spinner spinner = findViewById(R.id.spinner1);
        String[] levels = {"Sedentary", "Moderate", "Active", "Athlete"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, levels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        TextInputEditText et1 = findViewById(R.id.etInput1);
        TextView tvResult     = findViewById(R.id.tvResult);
        MaterialButton btnCalc  = findViewById(R.id.btnCalculate);
        MaterialButton btnClear = findViewById(R.id.btnClear);
        setupCopyButton(findViewById(R.id.btnCopy), tvResult);

        btnCalc.setOnClickListener(v -> {
            try {
                double weight = Double.parseDouble(et1.getText().toString().trim());
                String level  = levels[spinner.getSelectedItemPosition()];
                double litres = CalcHelper.calcWaterIntake(weight, level);
                int glasses   = (int) Math.ceil(litres / 0.25);
                String res = "Daily Water Goal: " + CalcHelper.fmt(litres) + " litres"
                        + "\n≈ " + glasses + " glasses (250ml each)"
                        + "\nActivity: " + level;
                showResult(tvResult, null, res, AppConstants.COLOR_HEALTH);
                dm.saveHistory("Water Intake", AppConstants.CAT_HEALTH,
                        weight + "kg · " + level,
                        CalcHelper.fmt(litres) + " litres/day", AppConstants.COLOR_HEALTH);
            } catch (Exception e) { Toast.makeText(this, "Enter valid weight", Toast.LENGTH_SHORT).show(); }
        });
        btnClear.setOnClickListener(v -> { et1.setText(""); tvResult.setText("—"); });
    }
    private void setLabel(int id, String t) { TextView tv = findViewById(id); if (tv != null) tv.setText(t); }
    private void setHint(int id, String h)  { TextInputEditText et = findViewById(id); if (et != null) et.setHint(h); }
    private void hide(int id)               { android.view.View v = findViewById(id); if (v != null) v.setVisibility(android.view.View.GONE); }
    private void setToolHeader(String e, String t, int c) {
        TextView te = findViewById(R.id.tvCalcEmoji); if (te != null) te.setText(e);
        TextView tt = findViewById(R.id.tvCalcTitle); if (tt != null) { tt.setText(t); tt.setTextColor(c); }
    }
}
