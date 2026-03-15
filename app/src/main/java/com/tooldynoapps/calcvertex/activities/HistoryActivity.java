package com.tooldynoapps.calcvertex.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tooldynoapps.calcvertex.R;
import com.tooldynoapps.calcvertex.adapters.HistoryAdapter;
import com.tooldynoapps.calcvertex.models.HistoryModel;

import java.util.List;

public class HistoryActivity extends BaseCalcActivity {

    private HistoryAdapter adapter;
    private TextView tvEmpty;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setupToolbar("History");

        recyclerView = findViewById(R.id.recyclerView);
        tvEmpty      = findViewById(R.id.tvEmpty);
        TextView btnClear = findViewById(R.id.btnClearAll);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter(this, dm.getHistory());
        recyclerView.setAdapter(adapter);

        btnClear.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Clear History")
                    .setMessage("Delete all 20 saved calculations?")
                    .setPositiveButton("Clear", (d, w) -> {
                        dm.clearHistory();
                        loadHistory();
                    })
                    .setNegativeButton("Cancel", null).show();
        });

        loadHistory();
        setupBottomNav(4);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHistory();
    }

    private void loadHistory() {
        List<HistoryModel> list = dm.getHistory();
        adapter.updateList(list);
        tvEmpty.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(list.isEmpty() ? View.GONE : View.VISIBLE);
        if (list.isEmpty()) {
            tvEmpty.setAlpha(0f);
            tvEmpty.animate().alpha(1f).setDuration(400).start();
        }
    }
}
