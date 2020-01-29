package br.com.sigga.service.utils

import android.content.Context
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class GlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDiskCache(
            ExternalPreferredCacheDiskCacheFactory(
                context
            )
        )
                .setDefaultRequestOptions(
                    RequestOptions().format(
                        DecodeFormat.PREFER_ARGB_8888
                    ))
                .setLogLevel(Log.VERBOSE)
    }
}
