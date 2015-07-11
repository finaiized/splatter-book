package com.example.finaiized.splatterbook.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.example.finaiized.splatterbook.R;

public class ChooseAmountFragment extends Fragment {
    public interface OnAmountChosen {
        void amountChosen(String s);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_choose_amount, container, false);
        GridLayout grid = (GridLayout) v.findViewById(R.id.grid);
        for (int i = 0; i < grid.getChildCount(); i++) {
            View child = grid.getChildAt(i);
            if (child instanceof Button) {
                Button b = (Button) child;
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment parent = getParentFragment();
                        if (parent instanceof OnAmountChosen) {
                            OnAmountChosen callback = (OnAmountChosen) parent;
                            callback.amountChosen(((Button) v).getText().toString());
                        } else {
                            throw new ClassCastException("Parent fragment of ChooseAmountFragment must implement OnAmountChosen");
                        }
                    }
                });
            }
        }
        return v;
    }
}
