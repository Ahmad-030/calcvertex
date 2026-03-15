package com.tooldynoapps.calcvertex.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.tooldynoapps.calcvertex.R;
import com.tooldynoapps.calcvertex.utils.AppConstants;
import com.tooldynoapps.calcvertex.utils.CalcHelper;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateDiffActivity extends BaseCalcActivity {

    private String date1Str = "", date2Str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_date);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Date Difference");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        animateContainer(findViewById(R.id.calcContainer));
        setToolHeader("📅", "Date Difference", AppConstants.COLOR_EVERYDAY);
        setDateLabel(R.id.tvDateLabel1, "Start Date");
        setDateLabel(R.id.tvDateLabel2, "End Date");

        TextView tvDate1  = findViewById(R.id.tvDate1);
        TextView tvDate2  = findViewById(R.id.tvDate2);
        TextView tvResult = findViewById(R.id.tvResult);
        MaterialButton btnCalc  = findViewById(R.id.btnCalculate);
        MaterialButton btnClear = findViewById(R.id.btnClear);
        setupCopyButton(findViewById(R.id.btnCopy), tvResult);

        tvDate1.setText("Tap to select start date");
        tvDate2.setText("Tap to select end date");

        tvDate1.setOnClickListener(v -> pickDate(tvDate1, true));
        tvDate2.setOnClickListener(v -> pickDate(tvDate2, false));

        btnCalc.setOnClickListener(v -> {
            if (date1Str.isEmpty() || date2Str.isEmpty()) {
                android.widget.Toast.makeText(this, "Select both dates", android.widget.Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date d1 = sdf.parse(date1Str);
                Date d2 = sdf.parse(date2Str);
                long days = CalcHelper.dateDiffDays(d1, d2);
                long weeks = days / 7;
                long months = days / 30;
                String res = "Days: " + days
                        + "\nWeeks: " + weeks
                        + "\nApprox. Months: " + months;
                showResult(tvResult, null, res, AppConstants.COLOR_EVERYDAY);
                dm.saveHistory("Date Difference", AppConstants.CAT_EVERYDAY,
                        date1Str + " → " + date2Str, days + " days", AppConstants.COLOR_EVERYDAY);
            } catch (ParseException e) {
                android.widget.Toast.makeText(this, "Invalid dates", android.widget.Toast.LENGTH_SHORT).show();
            }
        });
        btnClear.setOnClickListener(v -> {
            tvDate1.setText("Tap to select start date");
            tvDate2.setText("Tap to select end date");
            date1Str = ""; date2Str = "";
            tvResult.setText("—");
        });
    }

    private void pickDate(TextView tv, boolean isFirst) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (dp, y, m, d) -> {
            String dateStr = String.format("%04d-%02d-%02d", y, m + 1, d);
            tv.setText(dateStr);
            if (isFirst) date1Str = dateStr; else date2Str = dateStr;
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setToolHeader(String e, String t, int c) {
        TextView te = findViewById(R.id.tvCalcEmoji); if (te != null) te.setText(e);
        TextView tt = findViewById(R.id.tvCalcTitle); if (tt != null) { tt.setText(t); tt.setTextColor(c); }
    }
    private void setDateLabel(int id, String t) { TextView tv = findViewById(id); if (tv != null) tv.setText(t); }
}
