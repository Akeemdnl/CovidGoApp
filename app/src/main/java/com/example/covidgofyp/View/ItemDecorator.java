package com.example.covidgofyp.View;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDecorator extends RecyclerView.ItemDecoration {
    private final int verticalHeight;

    public ItemDecorator(int verticalHeight) {
        this.verticalHeight = verticalHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = verticalHeight;
      //  super.getItemOffsets(outRect, view, parent, state);
    }
}
