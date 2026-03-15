package com.tooldynoapps.calcvertex.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.tooldynoapps.calcvertex.R;
import com.tooldynoapps.calcvertex.utils.AppConstants;
import com.tooldynoapps.calcvertex.utils.CalcHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class CurrencyCalcActivity extends BaseCalcActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_generic);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Currency Converter");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        animateContainer(findViewById(R.id.calcContainer));
        setLabel(R.id.tvLabel1, "Amount");
        setLabel(R.id.tvLabel2, "Exchange Rate (1 unit = ?)");
        setLabel(R.id.tvLabel3, "From Currency (label)");
        setLabel(R.id.tvLabel4, "To Currency (label)");
        setHint(R.id.etInput1, "e.g. 100");
        setHint(R.id.etInput2, "e.g. 278.5");
        setHint(R.id.etInput3, "e.g. USD");
        setHint(R.id.etInput4, "e.g. PKR");
        setToolHeader("💱", "Currency", AppConstants.COLOR_FINANCE);

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
                double amount = Double.parseDouble(et1.getText().toString().trim());
                double rate   = Double.parseDouble(et2.getText().toString().trim());
                String from   = et3.getText().toString().trim();
                String to     = et4.getText().toString().trim();
                double result = CalcHelper.convertCurrency(amount, rate);
                String res = CalcHelper.fmt(amount) + " " + (from.isEmpty() ? "FROM" : from)
                        + " = " + CalcHelper.fmt(result) + " " + (to.isEmpty() ? "TO" : to)
                        + "\nRate: 1 " + (from.isEmpty() ? "unit" : from)
                        + " = " + CalcHelper.fmt(rate) + " " + (to.isEmpty() ? "unit" : to);
                showResult(tvResult, null, res, AppConstants.COLOR_FINANCE);
                dm.saveHistory("Currency Convert", AppConstants.CAT_FINANCE,
                        CalcHelper.fmt(amount) + " " + from,
                        CalcHelper.fmt(result) + " " + to, AppConstants.COLOR_FINANCE);
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
