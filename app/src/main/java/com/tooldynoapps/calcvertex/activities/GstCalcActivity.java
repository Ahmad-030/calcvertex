package com.tooldynoapps.calcvertex.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.tooldynoapps.calcvertex.R;
import com.tooldynoapps.calcvertex.utils.AppConstants;
import com.tooldynoapps.calcvertex.utils.CalcHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class GstCalcActivity extends BaseCalcActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_generic);
        setupToolbar("GST / Tax Calculator");
        animateContainer(findViewById(R.id.calcContainer));
        setLabel(R.id.tvLabel1, "Original Amount (PKR)");
        setLabel(R.id.tvLabel2, "GST / Tax Rate (%)");
        setHint(R.id.etInput1, "e.g. 10000");
        setHint(R.id.etInput2, "e.g. 17");
        hide(R.id.input3Row); hide(R.id.input4Row);
        setToolHeader("🧾", "GST / Tax", AppConstants.COLOR_FINANCE);

        TextInputEditText et1 = findViewById(R.id.etInput1);
        TextInputEditText et2 = findViewById(R.id.etInput2);
        TextView tvResult     = findViewById(R.id.tvResult);
        MaterialButton btnCalc  = findViewById(R.id.btnCalculate);
        MaterialButton btnClear = findViewById(R.id.btnClear);
        setupCopyButton(findViewById(R.id.btnCopy), tvResult);

        btnCalc.setOnClickListener(v -> {
            try {
                double amount = Double.parseDouble(et1.getText().toString().trim());
                double gstPct = Double.parseDouble(et2.getText().toString().trim());
                double[] res  = CalcHelper.calcGst(amount, gstPct);
                String result = "Tax Amount: PKR " + CalcHelper.fmt(res[0])
                        + "\nTotal (incl. tax): PKR " + CalcHelper.fmt(res[1]);
                showResult(tvResult, null, result, AppConstants.COLOR_FINANCE);
                dm.saveHistory("GST Calculator", AppConstants.CAT_FINANCE,
                        "Amt=" + CalcHelper.fmt(amount) + " Rate=" + gstPct + "%",
                        "Tax PKR " + CalcHelper.fmt(res[0]), AppConstants.COLOR_FINANCE);
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
