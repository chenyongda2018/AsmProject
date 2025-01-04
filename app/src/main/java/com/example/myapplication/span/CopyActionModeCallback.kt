package com.example.myapplication.span

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast

/**
 *
 * @author: cyd
 * @date: 2025/1/4
 * @since: 实现覆盖粘贴板的功能
 */
class CopyActionModeCallback (
    private val originalText: String,
    private val textToCopy: String,
    private val context: Context
) : ActionMode.Callback {

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        // 保留默认的复制菜单
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.copy) {
            // 获取剪贴板服务
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // 创建 ClipData，使用自定义的复制文本
            val clip = ClipData.newPlainText("text", textToCopy)
            // 写入剪贴板
            clipboard.setPrimaryClip(clip)
            // 显示提示
            Toast.makeText(context, "已复制到剪贴板", Toast.LENGTH_SHORT).show()
            // 关闭操作模式
            mode?.finish()
            return true
        }
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        // 清理工作
    }
}

// 为 TextView 设置自定义的复制行为
fun TextView.setCopyBehavior(textToCopy: String) {
    this.customSelectionActionModeCallback = CopyActionModeCallback(
        this.text.toString(),
        textToCopy,
        context
    )
}