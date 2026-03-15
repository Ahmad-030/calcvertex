package com.tooldynoapps.calcvertex.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.tooldynoapps.calcvertex.R;
import com.tooldynoapps.calcvertex.utils.AppConstants;
import com.tooldynoapps.calcvertex.utils.CalcHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class EmiCalcActivity extends BaseCalcActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_generic);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("EMI Calculator");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        animateContainer(findViewById(R.id.calcContainer));

        // Customise labels
        setLabel(R.id.tvLabel1, "Principal Amount (PKR)");
        setLabel(R.id.tvLabel2, "Annual Interest Rate (%)");
        setLabel(R.id.tvLabel3, "Tenure (Months)");
        setHint(R.id.etInput1, "e.g. 500000");
        setHint(R.id.etInput2, "e.g. 12");
        setHint(R.id.etInput3, "e.g. 24");
        hide(R.id.input4Row);
        setToolHeader("💳", "EMI Calculator", AppConstants.COLOR_FINANCE);

        TextInputEditText et1 = findViewById(R.id.etInput1);
        TextInputEditText et2 = findViewById(R.id.etInput2);
        TextInputEditText et3 = findViewById(R.id.etInput3);
        TextView tvResult     = findViewById(R.id.tvResult);
        MaterialButton btnCalc = findViewById(R.id.btnCalculate);
        MaterialButton btnClear = findViewById(R.id.btnClear);

        setupCopyButton(findViewById(R.id.btnCopy), tvResult);

        btnCalc.setOnClickListener(v -> {
            try {
                double p = Double.parseDouble(et1.getText().toString().trim());
                double r = Double.parseDouble(et2.getText().toString().trim());
                int    n = Integer.parseInt(et3.getText().toString().trim());
                double emi = CalcHelper.calcEmi(p, r, n);
                double total = emi * n;
                double interest = total - p;
                String res = "Monthly EMI: PKR " + CalcHelper.fmt(emi)
                        + "\nTotal Payment: PKR " + CalcHelper.fmt(total)
                        + "\nTotal Interest: PKR " + CalcHelper.fmt(interest);
                showResult(tvResult, null, res, AppConstants.COLOR_FINANCE);
                dm.saveHistory("EMI Calculator", AppConstants.CAT_FINANCE,
                        "P=" + CalcHelper.fmt(p) + " R=" + r + "% N=" + n + "mo",
                        "EMI PKR " + CalcHelper.fmt(emi), AppConstants.COLOR_FINANCE);
            } catch (Exception e) { Toast.makeText(this, "Enter valid values", Toast.LENGTH_SHORT).show(); }
        });

        btnClear.setOnClickListener(v -> {
            et1.setText(""); et2.setText(""); et3.setText("");
            tvResult.setText("—");
        });
    }

    private void setLabel(int id, String text) {
        TextView tv = findViewById(id); if (tv != null) tv.setText(text);
    }
    private void setHint(int id, String hint) {
        TextInputEditText et = findViewById(id); if (et != null) et.setHint(hint);
    }
    private void hide(int id) {
        android.view.View v = findViewById(id); if (v != null) v.setVisibility(android.view.View.GONE);
    }
    private void setToolHeader(String emoji, String title, int color) {
        TextView tvEmoji = findViewById(R.id.tvCalcEmoji);
        TextView tvTitle = findViewById(R.id.tvCalcTitle);
        if (tvEmoji != null) tvEmoji.setText(emoji);
        if (tvTitle != null) { tvTitle.setText(title); tvTitle.setTextColor(color); }
    }
}
