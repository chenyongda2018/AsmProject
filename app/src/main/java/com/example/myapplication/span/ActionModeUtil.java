package com.example.myapplication.span;

import android.widget.TextView;

/**
 * @author: cyd
 * @date: 2025/1/4
 * @since:
 */
public class ActionModeUtil {
    public static void setCopyActionModel(TextView textView,String textToCopy) {
        textView.setCustomSelectionActionModeCallback(new CopyActionModeCallback(
                "",
                textToCopy,
                textView.getContext()
        ));
    }
}
