package com.example.marvelapp.presetation.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentDetailBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()

    private val args by navArgs<DetailFragmentArgs>()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDetailBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    @Suppress("MagicNumber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailViewArgs = args.detailViewArg

        binding.imageCharacter.run {
            transitionName = detailViewArgs.name
            imageLoader.load(
                this,
                imageUrl = detailViewArgs.imageUrl,
                R.drawable.ic_img_loading_error
            )
        }

        setSharedElementTransationOnEnter()

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            val logResult = when (uiState) {
                DetailViewModel.UiState.Loading -> "Loading Comics.."
                is DetailViewModel.UiState.Sucess -> uiState.comics.toString()
                is DetailViewModel.UiState.Error -> "Error when loading comics"
            }
            Log.d(DetailFragment::class.simpleName, logResult)
        }

        viewModel.getComics(detailViewArgs.characterId)
    }

    //Define a animaçào da transiçào como "move"
    private fun setSharedElementTransationOnEnter() {
        TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
            .apply {
                sharedElementEnterTransition = this
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}