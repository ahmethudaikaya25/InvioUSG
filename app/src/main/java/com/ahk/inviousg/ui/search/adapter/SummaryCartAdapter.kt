package com.ahk.inviousg.ui.search.adapter

import androidx.databinding.BindingAdapter
import com.ahk.inviousg.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("imageUrl")
fun loadImage(view: ShapeableImageView, url: String) {
    if (url.isEmpty()) {
        view.setImageResource(R.drawable.default_poster)
        return
    }
    val theImage = GlideUrl(
        url,
        LazyHeaders.Builder()
            .addHeader("User-Agent", "5")
            .build()
    )

    theImage.let {
        Glide.with(view.context)
            .load(theImage)
            .apply(
                RequestOptions()
                    .error(R.drawable.default_poster)

            )
            .into(view)
            .onLoadFailed(view.context.getDrawable(R.drawable.default_poster))
    }
}
