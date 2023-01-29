package com.ahk.inviousg.ui.detailed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.ahk.inviousg.R
import com.ahk.inviousg.databinding.FragmentDetailedBinding
import com.ahk.inviousg.ui.detailed.adapter.GenreAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Detailed : Fragment() {

    val viewModel: DetailedViewModel by viewModels()
    lateinit var binding: FragmentDetailedBinding
    val args: DetailedArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailedBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        loadImage(binding.poster, args.detailedInfo.poster ?: "")
        binding.title.text = args.detailedInfo.title
        binding.time.text = String.format("%s minutes", args.detailedInfo.runtime)
        binding.imdbPoint.text = String.format("%s/10", args.detailedInfo.imdbRating)
        binding.releasedDate.text = args.detailedInfo.released
        binding.description.text = args.detailedInfo.plot
        val genres = args.detailedInfo.genre?.split(",")
        binding.genreList.layoutManager = GridLayoutManager(context, 2)
        binding.genreList.adapter = GenreAdapter(genres ?: emptyList())
        return binding.root
    }

    private fun loadImage(view: ShapeableImageView, url: String) {
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
}
