package com.simplemobiletools.commons.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.simplemobiletools.commons.R
import com.simplemobiletools.commons.extensions.baseConfig
import com.simplemobiletools.commons.extensions.formatSize
import com.simplemobiletools.commons.extensions.getColoredDrawableWithColor
import com.simplemobiletools.commons.models.FileDirItem
import kotlinx.android.synthetic.main.filepicker_list_item.view.*

class FilepickerItemsAdapter(val context: Context, private val mItems: List<FileDirItem>, val itemClick: (FileDirItem) -> Unit) :
        RecyclerView.Adapter<FilepickerItemsAdapter.ViewHolder>() {
    var textColor = 0

    companion object {
        lateinit var folderDrawable: Drawable
        lateinit var fileDrawable: Drawable
    }

    init {
        textColor = context.baseConfig.textColor
        folderDrawable = context.resources.getColoredDrawableWithColor(R.drawable.ic_folder, textColor)
        folderDrawable.alpha = 140
        fileDrawable = context.resources.getColoredDrawableWithColor(R.drawable.ic_file, textColor)
        fileDrawable.alpha = 140
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.filepicker_list_item, parent, false)
        return ViewHolder(context, textColor, view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(mItems[position])
    }

    override fun getItemCount() = mItems.size

    class ViewHolder(val context: Context, val textColor: Int, view: View, val itemClick: (FileDirItem) -> (Unit)) : RecyclerView.ViewHolder(view) {
        fun bindView(fileDirItem: FileDirItem) {
            itemView.apply {
                list_item_name.text = fileDirItem.name
                list_item_name.setTextColor(textColor)

                if (fileDirItem.isDirectory) {
                    list_item_icon.setImageDrawable(folderDrawable)
                    list_item_details.text = getChildrenCnt(fileDirItem)
                } else {
                    list_item_icon.setImageDrawable(fileDrawable)
                    list_item_details.text = fileDirItem.size.formatSize()
                }

                list_item_details.setTextColor(textColor)
                setOnClickListener { itemClick(fileDirItem) }
            }
        }

        private fun getChildrenCnt(item: FileDirItem): String {
            val children = item.children
            return context.resources.getQuantityString(R.plurals.items, children, children)
        }
    }
}
