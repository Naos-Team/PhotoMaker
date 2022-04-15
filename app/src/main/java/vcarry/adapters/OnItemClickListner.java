package vcarry.adapters;

import android.view.View;

public interface OnItemClickListner<T> {
    void onItemClick(View view, T t);
}
