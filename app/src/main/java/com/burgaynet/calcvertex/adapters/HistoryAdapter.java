package com.burgaynet.calcvertex.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burgaynet.calcvertex.R;
import com.burgaynet.calcvertex.models.HistoryModel;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.VH> {

    private final Context context;
    private List<HistoryModel> list;
    private int lastPosition = -1;

    public HistoryAdapter(Context ctx, List<HistoryModel> list) {
        this.context = ctx;
        this.list = list;
    }

    public void updateList(List<HistoryModel> newList) {
        this.list = newList;
        lastPosition = -1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        HistoryModel m = list.get(position);
        int color = m.getColor();

        h.tvToolName.setText(m.getToolName());
        h.tvInput.setText(m.getInput());
        h.tvResult.setText(m.getResult());
        h.tvDate.setText(m.getDate());
        h.tvCategory.setText(m.getCategory());
        h.tvCategory.setTextColor(color);

        // Safe programmatic tint on icon container
        float dp = context.getResources().getDisplayMetrics().density;
        GradientDrawable bg = new GradientDrawable();
        bg.setShape(GradientDrawable.RECTANGLE);
        bg.setCornerRadius(12 * dp);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8)  & 0xFF;
        int b =  color        & 0xFF;
        bg.setColor((0x22 << 24) | (r << 16) | (g << 8) | b);
        h.iconContainer.setBackground(bg);

        h.tvResultAmount.setTextColor(color);
        h.tvResultAmount.setText(m.getResult());

        if (position > lastPosition) {
            h.itemView.setAlpha(0f);
            h.itemView.setTranslationX(50f);
            h.itemView.animate()
                    .alpha(1f).translationX(0f)
                    .setDuration(280).setStartDelay(position * 40L)
                    .setInterpolator(new android.view.animation.DecelerateInterpolator())
                    .start();
            lastPosition = position;
        }
    }

    @Override public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView     tvToolName, tvInput, tvResult, tvDate, tvCategory, tvResultAmount;
        LinearLayout iconContainer;

        VH(@NonNull View v) {
            super(v);
            tvToolName      = v.findViewById(R.id.tvToolName);
            tvInput         = v.findViewById(R.id.tvInput);
            tvResult        = v.findViewById(R.id.tvResult);
            tvDate          = v.findViewById(R.id.tvDate);
            tvCategory      = v.findViewById(R.id.tvCategory);
            tvResultAmount  = v.findViewById(R.id.tvResultAmount);
            iconContainer   = v.findViewById(R.id.iconContainer);
        }
    }
}
