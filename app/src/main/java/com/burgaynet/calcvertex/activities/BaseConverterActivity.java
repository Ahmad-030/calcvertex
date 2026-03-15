package com.burgaynet.calcvertex.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.burgaynet.calcvertex.R;
import com.burgaynet.calcvertex.utils.AppConstants;
import com.burgaynet.calcvertex.utils.CalcHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class BaseConverterActivity extends BaseCalcActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_spinner);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Number Base Converter");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        animateContainer(findViewById(R.id.calcContainer));
        setToolHeader("🔢", "Base Converter", AppConstants.COLOR_MATH);
        setLabel(R.id.tvSpinnerLabel, "Convert FROM");
        setLabel(R.id.tvLabel1, "Input Number");
        setHint(R.id.etInput1, "e.g. 255");
        hide(R.id.input2Row);

        Spinner spinner = findViewById(R.id.spinner1);
        String[] bases = {"Decimal (Base 10)", "Binary (Base 2)", "Octal (Base 8)", "Hexadecimal (Base 16)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, bases);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        TextInputEditText et1 = findViewById(R.id.etInput1);
        TextView tvResult     = findViewById(R.id.tvResult);
        MaterialButton btnCalc  = findViewById(R.id.btnCalculate);
        MaterialButton btnClear = findViewById(R.id.btnClear);
        setupCopyButton(findViewById(R.id.btnCopy), tvResult);

        btnCalc.setOnClickListener(v -> {
            try {
                String input = et1.getText().toString().trim();
                int idx = spinner.getSelectedItemPosition();
                long decimal;
                switch (idx) {
                    case 1: decimal = CalcHelper.binaryToDecimal(input); break;
                    case 2: decimal = CalcHelper.octalToDecimal(input);  break;
                    case 3: decimal = CalcHelper.hexToDecimal(input);    break;
                    default: decimal = Long.parseLong(input);
                }
                String res = "Decimal:     " + decimal
                        + "\nBinary:      " + CalcHelper.decimalToBinary(decimal)
                        + "\nOctal:       " + CalcHelper.decimalToOctal(decimal)
                        + "\nHex:         " + CalcHelper.decimalToHex(decimal);
                showResult(tvResult, null, res, AppConstants.COLOR_MATH);
                dm.saveHistory("Base Converter", AppConstants.CAT_MATH,
                        bases[idx] + ": " + input,
                        "Dec=" + decimal + " Bin=" + CalcHelper.decimalToBinary(decimal),
                        AppConstants.COLOR_MATH);
            } catch (Exception e) { Toast.makeText(this, "Invalid number for selected base", Toast.LENGTH_SHORT).show(); }
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
