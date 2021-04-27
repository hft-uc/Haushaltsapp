package com.example.haushaltsapp.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haushaltsapp.R;
import com.google.android.material.tabs.TabLayout;


public class ChatDetailFragment extends Fragment {
    private ChatDetailViewModel chatDetailViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatDetailViewModel =
                new ViewModelProvider(this).get(ChatDetailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_chat_detail, container, false);

        TabLayout tabs = (TabLayout) root.findViewById(R.id.tabs);
        final ViewPager viewPager = (ViewPager) root.findViewById(R.id.view_pager);
        ChatDetailPageAdapter chatDetailPageAdapter = new ChatDetailPageAdapter(getChildFragmentManager());

        viewPager.setAdapter(chatDetailPageAdapter);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

        final TextView textView = root.findViewById(R.id.title);
        chatDetailViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }


}