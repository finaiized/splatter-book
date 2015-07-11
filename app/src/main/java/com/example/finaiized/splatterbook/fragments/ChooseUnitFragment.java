package com.example.finaiized.splatterbook.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.finaiized.splatterbook.R;

public class ChooseUnitFragment extends Fragment {

    public interface OnUnitChosen {
        void unitChosen(String unit);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_choose_unit, container, false);
        LinearLayout volumes = (LinearLayout) v.findViewById(R.id.unit_volume);
        for (int i = 0; i < volumes.getChildCount(); i++) {
            Button tv = (Button) volumes.getChildAt(i);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment parent = getParentFragment();
                    if (parent instanceof OnUnitChosen) {
                        OnUnitChosen callback = (OnUnitChosen) parent;
                        callback.unitChosen(((Button) v).getText().toString());
                    } else {
                        throw new ClassCastException("Parent fragment of ChooseAmountFragment must implement OnUnitChosen");
                    }
                }
            });
        }

        return v;
    }
}
