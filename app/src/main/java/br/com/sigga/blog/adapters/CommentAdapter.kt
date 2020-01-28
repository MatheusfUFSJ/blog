package br.com.sigga.blog.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.sigga.blog.databinding.ItemCommentBinding
import br.com.sigga.blog.ui.listeners.OnRecyclerViewClickListener
import br.com.sigga.blog.viewmodels.items.CommentItemViewModel
import org.jetbrains.anko.layoutInflater

class CommentAdapter(
    private val clickListener: OnRecyclerViewClickListener<CommentItemViewModel>
) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    private val items = ArrayList<CommentItemViewModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCommentBinding.inflate(parent.context.layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], position)

    fun updateItems(data: List<CommentItemViewModel>) {
        items.clear()
        items.addAll(data)

        notifyDataSetChanged()
    }

    fun update(newItems: List<CommentItemViewModel>, clearData: Boolean = false) {
        if (clearData) {
            items.clear()
        }
        items.addAll(newItems)
        notifyDataSetChanged()
    }


    //region ~~~~ ViewHolder ~~~~
    inner class ViewHolder(val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommentItemViewModel, position: Int) {
            binding.item = item
            binding.viewContainerItem.setOnClickListener {
                clickListener.onItemSelected(item, position)
            }
            binding.executePendingBindings()
        }
    }
    //endregion

}
