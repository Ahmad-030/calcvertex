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

public class UnitConverterActivity extends BaseCalcActivity {

    private static final String[][] UNITS = {
        {"m", "km", "cm", "mm", "mi", "ft", "in"},   // Length
        {"kg", "g", "lb", "oz"},                       // Weight
        {"C", "F", "K"}                                // Temperature
    };
    private static final String[] CATEGORIES = {"Length", "Weight", "Temperature"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_converter);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Unit Converter");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        animateContainer(findViewById(R.id.calcContainer));
        setToolHeader("📏", "Unit Converter", AppConstants.COLOR_EVERYDAY);

        Spinner spinnerCat  = findViewById(R.id.spinnerCategory);
        Spinner spinnerFrom = findViewById(R.id.spinnerFrom);
        Spinner spinnerTo   = findViewById(R.id.spinnerTo);
        TextInputEditText etValue = findViewById(R.id.etValue);
        TextView tvResult   = findViewById(R.id.tvResult);
        MaterialButton btnCalc  = findViewById(R.id.btnCalculate);
        MaterialButton btnClear = findViewById(R.id.btnClear);
        setupCopyButton(findViewById(R.id.btnCopy), tvResult);

        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, CATEGORIES);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCat.setAdapter(catAdapter);

        spinnerCat.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(android.widget.AdapterView<?> p, android.view.View v, int pos, long id) {
                updateUnitSpinners(spinnerFrom, spinnerTo, UNITS[pos]);
            }
            @Override public void onNothingSelected(android.widget.AdapterView<?> p) {}
        });
        updateUnitSpinners(spinnerFrom, spinnerTo, UNITS[0]);

        btnCalc.setOnClickListener(v -> {
            try {
                double val = Double.parseDouble(etValue.getText().toString().trim());
                int catIdx = spinnerCat.getSelectedItemPosition();
                String from = spinnerFrom.getSelectedItem().toString();
                String to   = spinnerTo.getSelectedItem().toString();
                double result;
                switch (catIdx) {
                    case 1: result = CalcHelper.convertWeight(val, from, to); break;
                    case 2: result = CalcHelper.convertTemp(val, from, to);   break;
                    default: result = CalcHelper.convertLength(val, from, to);
                }
                String res = CalcHelper.fmt(val) + " " + from + " = " + CalcHelper.fmt(result) + " " + to;
                showResult(tvResult, null, res, AppConstants.COLOR_EVERYDAY);
                dm.saveHistory("Unit Converter", AppConstants.CAT_EVERYDAY,
                        CATEGORIES[catIdx] + ": " + val + " " + from + " → " + to,
                        CalcHelper.fmt(result) + " " + to, AppConstants.COLOR_EVERYDAY);
            } catch (Exception e) { Toast.makeText(this, "Enter valid value", Toast.LENGTH_SHORT).show(); }
        });
        btnClear.setOnClickListener(v -> { etValue.setText(""); tvResult.setText("—"); });
    }

    private void updateUnitSpinners(Spinner from, Spinner to, String[] units) {
        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        from.setAdapter(a);
        to.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units));
        ((ArrayAdapter) to.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (units.length > 1) to.setSelection(1);
    }

    private void setToolHeader(String e, String t, int c) {
        TextView te = findViewById(R.id.tvCalcEmoji); if (te != null) te.setText(e);
        TextView tt = findViewById(R.id.tvCalcTitle); if (tt != null) { tt.setText(t); tt.setTextColor(c); }
    }
}
