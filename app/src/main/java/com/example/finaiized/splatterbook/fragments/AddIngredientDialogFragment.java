package com.example.finaiized.splatterbook.fragments;

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

import com.example.finaiized.splatterbook.R;

public class AddIngredientDialogFragment extends DialogFragment {

    private ViewPager pager;
    private PagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alert_dialog, container);
        pager = (ViewPager) v.findViewById(R.id.ingredient_pager);

        adapter = new IngredientAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);

        return v;
    }

    public void setPagerItem(int i) {
        pager.setCurrentItem(i, true);
    }

    private class IngredientAdapter extends FragmentPagerAdapter {
        public IngredientAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new IngredientsFragment();

        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
