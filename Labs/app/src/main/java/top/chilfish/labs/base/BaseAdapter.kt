package top.chilfish.labs.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Diffable, VB : ViewDataBinding>(
    private val itemLayout: Int
) : RecyclerView.Adapter<BaseAdapter.ViewHolder<VB>>() {

    private var items: MutableList<T> = mutableListOf()
    protected lateinit var binding: VB
    protected lateinit var context: Context

    private var onItemClickListener: OnItemClickListener<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<VB> {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, itemLayout, parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<VB>, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item)
        }
    }

    override fun getItemCount() = items.size
    protected fun getItem(position: Int) = items[position]
    fun items() = items

    fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItems(newItems: MutableList<T>) {
        val oldSize = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(oldSize, newItems.size)
    }

    fun rmItem(item: T) {
        notifyItemRemoved(items.indexOf(item))
        items.remove(item)
    }

    fun updateItem(old: T, new: T) {
        try {
            val index = items.indexOf(old)
            items[index] = new
            notifyItemChanged(index)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateItems(newItems: MutableList<T>) {
        Differ(items, newItems).dispatchUpdatesTo(this)
        items = newItems
    }

    override fun getItemId(position: Int): Long = getItem(position).itemId()

    interface OnItemClickListener<T> {
        fun onItemClick(item: T)
    }

    fun setOnItemClickListener(listener: OnItemClickListener<T>) {
        onItemClickListener = listener
    }

    class ViewHolder<VB : ViewDataBinding>(binding: VB) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.executePendingBindings()
        }
    }
}

interface Diffable {
    fun itemId(): Long
    fun sameContent(other: Diffable): Boolean
}

class Differ<T : Diffable>(
    private val oldList: MutableList<T>,
    private val newList: MutableList<T>
) {
    private val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].itemId() == newList[newItemPosition].itemId()

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].sameContent(newList[newItemPosition])
    })

    fun dispatchUpdatesTo(adapter: RecyclerView.Adapter<*>) {
        diffResult.dispatchUpdatesTo(adapter)
    }
}