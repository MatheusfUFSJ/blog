package br.com.sigga.blog.viewmodels.items

import br.com.sigga.data.model.Album

class AlbumItemViewModel(
    val id: String = "",
    var title: String = ""
) {

    companion object {
        fun fromAlbum(album: Album): AlbumItemViewModel =
            AlbumItemViewModel(album.id.toString(), album.title)
    }

    override operator fun equals(other: Any?): Boolean {
        if (other == null || other !is PostItemViewModel) {
            return false
        }

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}