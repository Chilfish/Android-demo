package top.chilfish.labs

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VB : ViewDataBinding> :
    RecyclerView.Adapter<BaseAdapter.ViewHolder<VB>>() {

    protected var items: MutableList<T> = mutableListOf()
    protected lateinit var binding: VB
    protected lateinit var context: Context

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<VB> {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, itemLayout, parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<VB>, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(item, holder.itemView)
        }
    }

    override fun getItemCount() = items.size
    protected fun getItem(position: Int) = items[position]

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
        val index = items.indexOf(old)
        items[index] = new
        notifyItemChanged(index)
    }

    abstract fun updateItems(newItems: MutableList<T>)

    protected abstract val itemLayout: Int

    open fun onItemClicked(item: T, view: View) {}

    class ViewHolder<VB : ViewDataBinding>(binding: VB) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.executePendingBindings()
        }
    }
}

abstract class BaseDiffCallback<T>(
    private val oldList: MutableList<T>,
    private val newList: MutableList<T>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        areSameItems(oldList[oldItemPosition], newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        areSameContent(oldList[oldItemPosition], newList[newItemPosition])

    abstract fun areSameItems(oldItem: T, newItem: T): Boolean

    abstract fun areSameContent(oldItem: T, newItem: T): Boolean
}
