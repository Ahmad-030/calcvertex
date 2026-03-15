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

public class AreaCalcActivity extends BaseCalcActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_spinner);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Area & Volume");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        animateContainer(findViewById(R.id.calcContainer));
        setToolHeader("📐", "Area & Volume", AppConstants.COLOR_MATH);
        setLabel(R.id.tvSpinnerLabel, "Shape");
        setLabel(R.id.tvLabel1, "Value A (radius/length/base)");
        setLabel(R.id.tvLabel2, "Value B (height/width) if needed");
        setHint(R.id.etInput1, "e.g. 5");
        setHint(R.id.etInput2, "e.g. 10");

        Spinner spinner = findViewById(R.id.spinner1);
        String[] shapes = {"Circle (Area)", "Rectangle (Area)", "Triangle (Area)",
                "Square (Area)", "Cube (Volume)", "Cylinder (Volume)", "Sphere (Volume)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, shapes);
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
                double b = et2.getText().toString().trim().isEmpty() ? 0
                         : Double.parseDouble(et2.getText().toString().trim());
                int idx = spinner.getSelectedItemPosition();
                String shape = shapes[idx];
                double result;
                String unit;
                if (idx <= 3) {
                    String s = shape.split(" ")[0];
                    result = CalcHelper.calcArea(s, a, b);
                    unit = "sq units";
                } else {
                    String s = shape.split(" ")[0];
                    result = CalcHelper.calcVolume(s, a, b);
                    unit = "cubic units";
                }
                String res = shape + "\nResult: " + CalcHelper.fmt(result) + " " + unit;
                showResult(tvResult, null, res, AppConstants.COLOR_MATH);
                dm.saveHistory("Area & Volume", AppConstants.CAT_MATH,
                        shape + " A=" + a + (b > 0 ? " B=" + b : ""),
                        CalcHelper.fmt(result) + " " + unit, AppConstants.COLOR_MATH);
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
