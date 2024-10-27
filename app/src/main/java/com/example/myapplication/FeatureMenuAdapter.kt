package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author: cyd
 * @date: 2024/10/27
 * @since:
 */
class FeatureMenuAdapter(private val menuList: List<MenuItemData>) :
    RecyclerView.Adapter<FeatureMenuAdapter.MenuItemViewHolder>() {

    class MenuItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var textView: TextView

        init {
            textView = itemView.findViewById(R.id.tv)
        }

        fun bindData(itemData: MenuItemData) {
            textView.text = itemData.title
            itemView.setOnClickListener {
                it.context.startActivity(itemData.intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MenuItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MenuItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(vh: MenuItemViewHolder, position: Int) {
        vh.bindData(menuList[position])
    }
}

data class MenuItemData(
    val title: String, val intent: Intent
)