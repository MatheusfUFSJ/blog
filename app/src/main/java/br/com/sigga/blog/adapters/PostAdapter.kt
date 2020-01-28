package br.com.sigga.blog.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.sigga.blog.databinding.ItemPostBinding
import br.com.sigga.blog.ui.listeners.OnRecyclerViewClickListener
import br.com.sigga.blog.viewmodels.items.PostItemViewModel
import org.jetbrains.anko.layoutInflater

class PostAdapter(
    private val clickListener: OnRecyclerViewClickListener<PostItemViewModel>
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private val items = ArrayList<PostItemViewModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPostBinding.inflate(parent.context.layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], position)

    fun updateItems(data: List<PostItemViewModel>) {
        items.clear()
        items.addAll(data)

        notifyDataSetChanged()
    }

    fun update(newItems: List<PostItemViewModel>, clearData: Boolean = false) {
        if (clearData) {
            items.clear()
        }
        items.addAll(newItems)
        notifyDataSetChanged()
    }


    //region ~~~~ ViewHolder ~~~~
    inner class ViewHolder(val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostItemViewModel, position: Int) {
            binding.item = item
            binding.viewContainerItem.setOnClickListener {
                clickListener.onItemSelected(item, position)
            }
            binding.executePendingBindings()
        }
    }
    //endregion

}
