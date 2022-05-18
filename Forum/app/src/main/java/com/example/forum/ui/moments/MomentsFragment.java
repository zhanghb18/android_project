package com.example.forum.ui.moments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.forum.databinding.FragmentMomentsBinding;
import com.google.android.material.tabs.TabLayout;

import com.example.forum.R;

public class MomentsFragment extends Fragment {

    private FragmentMomentsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MomentsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(MomentsViewModel.class);

        binding = FragmentMomentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textMoments;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        TabLayout tabLayout=binding.tabLayout;
        tabLayout.addTab(tabLayout.newTab().setText(R.string.moments_tablayout_all));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.moments_tablayout_concerned));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager=binding.pager;
        final PagerAdapter adapter= new PagerAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                    }
                });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}