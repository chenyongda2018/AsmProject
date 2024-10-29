package com.example.myapplication.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myapplication.databinding.LayoutFragIntentServiceBinding;

/**
 * @author: cyd
 * @date: 2024/10/28
 * @since:
 */
public class IntentServiceFragment extends Fragment {

    public static final String ACTION_SERVICE = "action_service";
    public static final String ACTION_THREAD = "action_thread";

    private LayoutFragIntentServiceBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = LayoutFragIntentServiceBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        IntentFilter filter = new IntentFilter(ACTION_SERVICE);
        filter.addAction(ACTION_THREAD);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new MyBroadCastReceiver(), filter);

        mBinding.startService.setOnClickListener((v) -> {
            Intent startIntent = new Intent(getActivity(),MyIntentService.class);
            getActivity().startService(startIntent);
        });

        mBinding.stopService.setOnClickListener((v) -> {
            Intent stopIntent = new Intent(getActivity(),MyIntentService.class);
            getActivity().stopService(stopIntent);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent stopIntent = new Intent(getActivity(),MyIntentService.class);
        getActivity().stopService(stopIntent);
    }

    /**
     * @author: cyd
     * @date: 2024/10/28
     * @since:
     */
    public class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case IntentServiceFragment.ACTION_SERVICE:
                    String status = intent.getStringExtra("status");
                    mBinding.serviceStatus.setText(status);
                    break;
                case IntentServiceFragment.ACTION_THREAD:
                    String threadStatus = intent.getStringExtra("status");
                    int progress = intent.getIntExtra("progress", 0);
                    mBinding.threadProgress.setText(progress + "%");
                    mBinding.threadStatus.setText(threadStatus);
                    break;
                default:
                    break;
            }
        }
    }
}
