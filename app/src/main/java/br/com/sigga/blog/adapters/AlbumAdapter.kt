package br.com.sigga.blog.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.sigga.blog.databinding.ItemPhotoBinding
import br.com.sigga.blog.viewmodels.items.AlbumItemViewModel
import org.jetbrains.anko.layoutInflater

class AlbumAdapter(
    private val context: Context
) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    private val items = ArrayList<AlbumItemViewModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhotoBinding.inflate(parent.context.layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], position)

    fun update(newItems: List<AlbumItemViewModel>, clearData: Boolean = false) {
        if (clearData) {
            items.clear()
        }
        items.addAll(newItems)
        notifyDataSetChanged()
    }


    //region ~~~~ ViewHolder ~~~~
    inner class ViewHolder(val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AlbumItemViewModel, position: Int) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
    //endregion

}
