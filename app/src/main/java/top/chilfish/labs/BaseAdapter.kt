package top.chilfish.labs

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VB : ViewDataBinding> :
    RecyclerView.Adapter<BaseAdapter.ViewHolder<VB>>() {

    private var items: MutableList<T> = mutableListOf()
    protected lateinit var binding: VB

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<VB> {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, itemId, parent, false)
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

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: MutableList<T>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        items.add(item)
        updateItems(items)
    }

    fun rmItem(item: T) {
        items.remove(item)
        updateItems(items)
    }

    protected abstract val itemId: Int

    open fun onItemClicked(item: T, view: View) {}

    class ViewHolder<VB : ViewDataBinding>(binding: VB) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.executePendingBindings()
        }
    }
}
