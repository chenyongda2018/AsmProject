package com.example.myapplication.behavior_scrollview.view;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityBahavoirLayoutBinding;
import com.example.myapplication.extension.RvEx;
import com.example.simplekit.utils.NumEx;
import com.funnywolf.hollowkit.view.scroll.behavior.BehavioralScrollListener;
import com.funnywolf.hollowkit.view.scroll.behavior.BehavioralScrollView;
import com.funnywolf.hollowkit.view.scroll.behavior.BottomSheetLayout;

import kotlin.jvm.functions.Function0;

public class BahaviorLayoutActivity extends AppCompatActivity {

    private ActivityBahavoirLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityBahavoirLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RvEx.buildLayoutManager(binding.rvLinkageTop,this);
        RvEx.buildMockAdapter(binding.rvLinkageTop);

        RvEx.buildLayoutManager(binding.rvLinkageBottom,this);
        RvEx.buildMockAdapter(binding.rvLinkageBottom);

        binding.linkageScroll.setTopScrollTarget(new Function0<View>() {
            @Override
            public View invoke() {
                return binding.rvLinkageTop;
            }
        });

        binding.linkageScroll.getListeners().add(new BehavioralScrollListener() {
            @Override
            public void onScrollChanged(@NonNull BehavioralScrollView v, int from, int to) {
                updateFloatState();
            }

            @Override
            public void onStateChanged(@NonNull BehavioralScrollView v, int from, int to) {
            }
        });

        binding.bottomSheet.setup(BottomSheetLayout.POSITION_MID, floatingHeight);
        updateFloatState();
    }


    private final int floatingHeight = NumEx.getDp(100);


    private void updateFloatState() {
        if(binding.bottomSheet.indexOfChild(binding.bottomScrollView) >= 0) {
            if (binding.linkageScroll.getScrollY() >= floatingHeight) {
                binding.bottomSheet.setVisibility(View.GONE);
                binding.bottomSheet.removeView(binding.bottomScrollView);
                if (binding.layoutBottom.indexOfChild(binding.bottomScrollView) < 0) {
                    binding.layoutBottom.addView(binding.bottomScrollView);
                }
                binding.linkageScroll.setBottomScrollTarget(new Function0<View>() {
                    @Override
                    public View invoke() {
                        return binding.bottomScrollView;
                    }
                });
            }
        } else {
            if (binding.linkageScroll.getScrollY() < floatingHeight) {
                binding.linkageScroll.setBottomScrollTarget(null);
                if (binding.layoutBottom.indexOfChild(binding.bottomScrollView) >= 0) {
                    binding.layoutBottom.removeView(binding.bottomScrollView);
                }
                if (binding.bottomSheet.indexOfChild(binding.bottomScrollView) < 0) {

                    binding.bottomSheet.addView(binding.bottomScrollView);
                }
                binding.bottomSheet.setVisibility(View.VISIBLE);
            }
        }
    }
}