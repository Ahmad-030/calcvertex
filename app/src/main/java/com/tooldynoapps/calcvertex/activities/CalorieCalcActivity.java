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

public class CalorieCalcActivity extends BaseCalcActivity {

    private static final String[] ACTIVITIES = {
        "Walking (3.5 MET)", "Running (7 MET)", "Cycling (6 MET)",
        "Swimming (6 MET)", "Yoga (3 MET)", "Weight Training (5 MET)"
    };
    private static final double[] METS = {3.5, 7.0, 6.0, 6.0, 3.0, 5.0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_spinner);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Calorie Burn");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        animateContainer(findViewById(R.id.calcContainer));
        setToolHeader("🔥", "Calorie Burn", AppConstants.COLOR_HEALTH);

        setLabel(R.id.tvLabel1, "Weight (kg)");
        setLabel(R.id.tvLabel2, "Duration (minutes)");
        setLabel(R.id.tvSpinnerLabel, "Activity Type");
        setHint(R.id.etInput1, "e.g. 70");
        setHint(R.id.etInput2, "e.g. 30");

        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ACTIVITIES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        TextInputEditText et1 = findViewById(R.id.etInput1);
        TextInputEditText et2 = findViewById(R.id.etInput2);
        TextView tvResult     = findViewById(R.id.tvResult);
        MaterialButton btnCalc  = findViewById(R.id.btnCalculate);
        MaterialButton btnClear = findViewById(R.id.btnClear);
        setupCopyButton(findViewById(R.id.btnCopy), tvResult);

        btnCalc.setOnClickListener(v -> {
            try {
                double weight = Double.parseDouble(et1.getText().toString().trim());
                int    mins   = Integer.parseInt(et2.getText().toString().trim());
                int    idx    = spinner.getSelectedItemPosition();
                double cals   = CalcHelper.calcCalories(weight, mins, METS[idx]);
                String res = "Calories Burned: " + CalcHelper.fmt(cals) + " kcal"
                        + "\nActivity: " + ACTIVITIES[idx]
                        + "\nDuration: " + mins + " min";
                showResult(tvResult, null, res, AppConstants.COLOR_HEALTH);
                dm.saveHistory("Calorie Burn", AppConstants.CAT_HEALTH,
                        weight + "kg " + mins + "min " + ACTIVITIES[idx].split(" ")[0],
                        CalcHelper.fmt(cals) + " kcal", AppConstants.COLOR_HEALTH);
            } catch (Exception e) { Toast.makeText(this, "Enter valid values", Toast.LENGTH_SHORT).show(); }
        });
        btnClear.setOnClickListener(v -> { et1.setText(""); et2.setText(""); tvResult.setText("—"); });
    }
    private void setLabel(int id, String t) { TextView tv = findViewById(id); if (tv != null) tv.setText(t); }
    private void setHint(int id, String h)  { TextInputEditText et = findViewById(id); if (et != null) et.setHint(h); }
    private void setToolHeader(String e, String t, int c) {
        TextView te = findViewById(R.id.tvCalcEmoji); if (te != null) te.setText(e);
        TextView tt = findViewById(R.id.tvCalcTitle); if (tt != null) { tt.setText(t); tt.setTextColor(c); }
    }
}
