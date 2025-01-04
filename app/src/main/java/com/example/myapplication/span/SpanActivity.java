package com.example.myapplication.span;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

import java.util.regex.Pattern;

public class SpanActivity extends AppCompatActivity {

    String url1 = "migamecenter://openurl/https://migc-fe-staging.g.mi.com/why/newYear2025/index.html?curcv=13.13.0.124&versionCode=131300124&hideTitleBar=1#/home";
    String TAG = "SpanActivityTAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_span);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tv = findViewById(R.id.textView);
        tv.setText(parseText(url1));
        tv.setTextIsSelectable(true);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        ActionModeUtil.setCopyActionModel(tv,url1);
    }

    private SpannableStringBuilder parseText(String url) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(url);
        final String SCHEME_URL_REX_PATTERN = "migamecenter:\\/\\/[-A-Za-z0-9+&@#\\/%?=~_|!:,.;]*[-A-Za-z0-9+&@#\\/%=~_|]";
        Pattern pattern = Pattern.compile(SCHEME_URL_REX_PATTERN);
        Linkify.addLinks(ssb, pattern,"migamecenter:");
        URLSpan[] spans = ssb.getSpans(0, ssb.length(), URLSpan.class);
        for (URLSpan urlSpan : spans) {
            String spanItemUrl = urlSpan.getURL();
            Log.d(TAG,spanItemUrl);
            int start = ssb.getSpanStart(urlSpan);
            int end = ssb.getSpanEnd(urlSpan);
            ssb.removeSpan(urlSpan);
            if (url.startsWith("migamecenter:")) {
                String link = "好友送您一张福卡，注意查收！";
                ssb.replace(start, end, link);
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(spanItemUrl));
                        widget.getContext().startActivity(intent);
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                    }
                };
                ssb.setSpan(clickableSpan,start,link.length(),0);
            }
        }
        return ssb;
    }
}