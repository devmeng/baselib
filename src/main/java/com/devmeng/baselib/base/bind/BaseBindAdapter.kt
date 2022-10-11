package com.devmeng.baselib.base.bind

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Richard
 * Version : 1
 * Description :
 */
abstract class BaseBindAdapter<T : Any> :
    RecyclerView.Adapter<BaseBindViewHolder>() {

    abstract fun bind(holder: BaseBindViewHolder, itemData: T, position: Int)
    abstract fun getItemViewBindingRoot(parent: ViewGroup): View

    private var mList: MutableList<T>? = arrayListOf()

    var onItemClickListener: BaseBindViewHolder.OnItemClickListener<T>? = null
    var onItemViewClickListener: BaseBindViewHolder.OnItemViewClickListener<T>? = null
    var onItemLongClickListener: BaseBindViewHolder.OnItemLongClickListener<T>? = null
    var onItemViewLongClickListener:
            BaseBindViewHolder.OnItemViewLongClickListener<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindViewHolder {
        return BaseBindViewHolder(getItemViewBindingRoot(parent))
    }

    override fun onBindViewHolder(holder: BaseBindViewHolder, position: Int) {
        val itemData = mList!![position]
        setOnItemClickListener(holder, itemData, position)
        bind(holder, itemData, position)
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    fun refreshAdapter(list: MutableList<T>) {
        refreshAdapter(list, true, list.size)
    }

    fun refreshAdapter(list: MutableList<T>, first: Boolean, pageSize: Int) {
        with(mList!!) {
            when {
                //第一次添加
                first -> {
                    clear()
                    addAll(list)
                    notifyDataSetChanged()
                }
                else -> {
                    var preSize = size
                    addAll(list)
                    notifyItemRangeInserted(preSize, pageSize)
                }
            }
        }
    }

    open fun reverseData(list: MutableList<T>) {
        list.reverse()
        with(mList!!) {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    open fun addItem(itemData: T, isReverse: Boolean) {
        with(mList!!) {
            when (isReverse) {
                isReverse -> {
                    add(0, itemData)
                    notifyItemRangeInserted(0, 1)
                }
                else -> {
                    val preSize = size
                    add(itemData)
                    notifyItemRangeInserted(preSize, 1)
                }
            }
        }
        notifyDataSetChanged()
    }

    open fun removeItem(position: Int) {
        mList!!.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    open fun clearAdapter() {
        mList!!.clear()
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(holder: BaseBindViewHolder, itemData: T, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClickListener!!.onItemClick(itemData, position)
        }
    }

    fun setOnItemLongClickListener(holder: BaseBindViewHolder, itemData: T, position: Int) {
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener!!.onLongClick(itemData, position)
            true
        }
    }

    fun setOnItemViewClickListener(view: View, itemData: T, position: Int) {
        view.setOnClickListener {
            onItemViewClickListener!!.onViewClick(view, itemData, position)
        }
    }

    fun setOnItemViewLongClickListener(view: View, itemData: T, position: Int) {
        view.setOnLongClickListener {
            onItemViewLongClickListener!!.onViewLongClick(view, itemData, position)
            true
        }
    }

}