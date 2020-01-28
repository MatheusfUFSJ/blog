package br.com.sigga.blog.ui.listeners

interface OnRecyclerViewClickListener<T> {
    fun onItemSelected(item: T, position: Int)
}
