package top.chilfish.chatapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VB : ViewDataBinding> :
    RecyclerView.Adapter<BaseAdapter.ViewHolder<VB>>() {

    protected var items: List<T> = listOf()
    private var onItemClickListener: ((T) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<VB> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<VB>(inflater, getLayoutId(), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<VB>, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClicked(item, holder.itemView)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<T>) {
        items = newItems
        notifyDataSetChanged()
    }

    abstract fun getLayoutId(): Int

    open fun onItemClicked(item: T, view: View) {
        onItemClickListener?.invoke(item)
    }

    class ViewHolder<VB : ViewDataBinding>(val binding: VB) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Any?) {
//            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }
    }
}