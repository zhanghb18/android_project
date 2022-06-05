package com.example.forum.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.forum.R;
import com.example.forum.ui.BlockPage.BlockActivity;
import com.example.forum.ui.ConcernPage.ConcernActivity;
import com.example.forum.ui.NoticePage.NoticeActivity;
import com.example.forum.ui.Person.PersonInfoPage;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.forum.databinding.FragmentHomeBinding;
import com.example.forum.ui.PersonalPage.PersonHomeActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.person).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PersonInfoPage.class);
                getContext().startActivity(intent);
            }
        });
        view.findViewById(R.id.person_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PersonHomeActivity.class);
                getContext().startActivity(intent);
            }
        });
        view.findViewById(R.id.favourite_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ConcernActivity.class);
                getContext().startActivity(intent);
            }
        });
        view.findViewById(R.id.block_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BlockActivity.class);
                getContext().startActivity(intent);
            }
        });
        view.findViewById(R.id.message_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NoticeActivity.class);
                getContext().startActivity(intent);
            }
        });
    }
}