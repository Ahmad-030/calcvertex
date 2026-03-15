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

public class PercentCalcActivity extends BaseCalcActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_spinner);
        setupToolbar("Percentage Calculator");
        animateContainer(findViewById(R.id.calcContainer));
        setToolHeader("📊", "Percentage", AppConstants.COLOR_MATH);
        setLabel(R.id.tvSpinnerLabel, "Calculation Type");
        setLabel(R.id.tvLabel1, "Value A");
        setLabel(R.id.tvLabel2, "Value B");
        setHint(R.id.etInput1, "e.g. 25");
        setHint(R.id.etInput2, "e.g. 200");

        Spinner spinner = findViewById(R.id.spinner1);
        String[] types = {"A% of B  →  result", "A is what % of B", "% change from A to B"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, types);
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
                double a = Double.parseDouble(et1.getText().toString().trim());
                double b = Double.parseDouble(et2.getText().toString().trim());
                int idx = spinner.getSelectedItemPosition();
                double result;
                String res;
                switch (idx) {
                    case 0:
                        result = CalcHelper.percentOf(a, b);
                        res = a + "% of " + b + " = " + CalcHelper.fmt(result);
                        break;
                    case 1:
                        result = CalcHelper.whatPercent(a, b);
                        res = a + " is " + CalcHelper.fmt(result) + "% of " + b;
                        break;
                    default:
                        result = CalcHelper.percentChange(a, b);
                        String dir = result >= 0 ? "Increase" : "Decrease";
                        res = dir + ": " + CalcHelper.fmt(Math.abs(result)) + "%"
                                + "\nFrom " + a + " to " + b;
                }
                showResult(tvResult, null, res, AppConstants.COLOR_MATH);
                dm.saveHistory("Percentage", AppConstants.CAT_MATH,
                        types[idx] + " A=" + a + " B=" + b,
                        CalcHelper.fmt(result), AppConstants.COLOR_MATH);
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
