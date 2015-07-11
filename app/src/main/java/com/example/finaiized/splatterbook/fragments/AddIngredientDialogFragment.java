package com.example.finaiized.splatterbook.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.finaiized.splatterbook.R;

public class AddIngredientDialogFragment extends DialogFragment implements
        ChooseAmountFragment.OnAmountChosen,
        ChooseUnitFragment.OnUnitChosen {

    private ViewPager pager;
    private PagerAdapter adapter;

    private IngredientDialogListener listener;

    private TextView amountText;
    private TextView unitText;
    private TextView ingredientText;

    private Button cancelButton;
    private Button okButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alert_dialog, container);
        pager = (ViewPager) v.findViewById(R.id.ingredient_pager);

        adapter = new IngredientAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);

        listener = (IngredientDialogListener) getTargetFragment();

        amountText = (TextView) v.findViewById(R.id.text_amount);
        amountText.setTypeface(null, Typeface.BOLD);
        unitText = (TextView) v.findViewById(R.id.text_unit);
        ingredientText = (TextView) v.findViewById(R.id.text_ingredient);

        cancelButton = (Button) v.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDialogNegativeClick(AddIngredientDialogFragment.this);
                getDialog().cancel();
            }
        });

        okButton = (Button) v.findViewById(R.id.button_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDialogPositiveClick(AddIngredientDialogFragment.this);
                getDialog().dismiss();
            }
        });

        getDialog().setTitle("Add Ingredient");

        return v;
    }

    @Override
    public void amountChosen(String s) {
        pager.setCurrentItem(1, true);
        amountText.setText(s);
        amountText.setTypeface(null, Typeface.NORMAL);
        unitText.setTypeface(null, Typeface.BOLD);
    }

    @Override
    public void unitChosen(String unit) {
        pager.setCurrentItem(2, true);
        unitText.setText(unit);
        unitText.setTypeface(null, Typeface.NORMAL);
        ingredientText.setTypeface(null, Typeface.BOLD);
    }

    private class IngredientAdapter extends FragmentPagerAdapter {
        public IngredientAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ChooseAmountFragment();
                case 1:
                    return new ChooseUnitFragment();
                case 2:
                    return new ChooseIngredientFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public interface IngredientDialogListener {
        void onDialogPositiveClick(DialogFragment d);
        void onDialogNegativeClick(DialogFragment d);
    }
}
