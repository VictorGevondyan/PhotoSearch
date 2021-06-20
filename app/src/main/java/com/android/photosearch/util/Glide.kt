package com.android.photosearch.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

@GlideModule
class CompanionGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.setLogLevel(Log.ERROR)
    }
}

open class GlideRequestListener : RequestListener<Drawable>, GlideRequestListenerInterface {

    override fun onLoadFailed(
        error: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        Log.d("Loading failed", "Model: $model")
        onError()
        return false
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        onSuccess()
        return false
    }

    override fun onError() {}

    override fun onSuccess() {}
}

interface GlideRequestListenerInterface {

    fun onError() {}

    fun onSuccess() {}

}
