package com.burgaynet.calcvertex.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.burgaynet.calcvertex.R;
import com.burgaynet.calcvertex.utils.AppConstants;
import com.burgaynet.calcvertex.utils.CalcHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class InterestCalcActivity extends BaseCalcActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_generic);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Interest Calculator");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        animateContainer(findViewById(R.id.calcContainer));
        setLabel(R.id.tvLabel1, "Principal (PKR)");
        setLabel(R.id.tvLabel2, "Annual Rate (%)");
        setLabel(R.id.tvLabel3, "Time (Years)");
        setLabel(R.id.tvLabel4, "Compounds/Year (for CI)");
        setHint(R.id.etInput1, "e.g. 100000");
        setHint(R.id.etInput2, "e.g. 10");
        setHint(R.id.etInput3, "e.g. 3");
        setHint(R.id.etInput4, "e.g. 12 (monthly)");
        setToolHeader("🏦", "Interest", AppConstants.COLOR_FINANCE);

        TextInputEditText et1 = findViewById(R.id.etInput1);
        TextInputEditText et2 = findViewById(R.id.etInput2);
        TextInputEditText et3 = findViewById(R.id.etInput3);
        TextInputEditText et4 = findViewById(R.id.etInput4);
        TextView tvResult     = findViewById(R.id.tvResult);
        MaterialButton btnCalc  = findViewById(R.id.btnCalculate);
        MaterialButton btnClear = findViewById(R.id.btnClear);
        setupCopyButton(findViewById(R.id.btnCopy), tvResult);

        btnCalc.setOnClickListener(v -> {
            try {
                double p = Double.parseDouble(et1.getText().toString().trim());
                double r = Double.parseDouble(et2.getText().toString().trim());
                double t = Double.parseDouble(et3.getText().toString().trim());
                int    n = et4.getText().toString().trim().isEmpty() ? 1
                         : Integer.parseInt(et4.getText().toString().trim());
                double si = CalcHelper.calcSimpleInterest(p, r, t);
                double ci = CalcHelper.calcCompoundInterest(p, r, t, n);
                String result = "Simple Interest: PKR " + CalcHelper.fmt(si)
                        + "\nSI Total: PKR " + CalcHelper.fmt(p + si)
                        + "\nCompound Interest: PKR " + CalcHelper.fmt(ci)
                        + "\nCI Total: PKR " + CalcHelper.fmt(p + ci);
                showResult(tvResult, null, result, AppConstants.COLOR_FINANCE);
                dm.saveHistory("Interest Calc", AppConstants.CAT_FINANCE,
                        "P=" + CalcHelper.fmt(p) + " R=" + r + "% T=" + t + "yr",
                        "SI PKR " + CalcHelper.fmt(si), AppConstants.COLOR_FINANCE);
            } catch (Exception e) { Toast.makeText(this, "Enter valid values", Toast.LENGTH_SHORT).show(); }
        });
        btnClear.setOnClickListener(v -> { et1.setText(""); et2.setText(""); et3.setText(""); et4.setText(""); tvResult.setText("—"); });
    }
    private void setLabel(int id, String t) { TextView tv = findViewById(id); if (tv != null) tv.setText(t); }
    private void setHint(int id, String h)  { TextInputEditText et = findViewById(id); if (et != null) et.setHint(h); }
    private void setToolHeader(String e, String t, int c) {
        TextView te = findViewById(R.id.tvCalcEmoji); if (te != null) te.setText(e);
        TextView tt = findViewById(R.id.tvCalcTitle); if (tt != null) { tt.setText(t); tt.setTextColor(c); }
    }
}
