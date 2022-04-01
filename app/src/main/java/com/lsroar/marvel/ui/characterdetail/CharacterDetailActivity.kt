package com.lsroar.marvel.ui.characterdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.lsroar.marvel.R
import com.lsroar.marvel.databinding.ActivityCharcterDetailBinding
import com.lsroar.marvel.ui.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailActivity : AppCompatActivity() {

    private val viewModel: CharacterDetailViewModel by viewModel<CharacterDetailViewModelImpl>()
    private lateinit var binding: ActivityCharcterDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivityCharcterDetailBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        viewModel.characterDetail().observe(this, { characterDetailVO ->
            this.binding.textviewItemViewCharacterDetailName.text = characterDetailVO.name
            Glide.with(this)
                .load(characterDetailVO.imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .apply(RequestOptions().centerInside())
                .into(this.binding.imageviewCharacterDetail)
        })

        viewModel.error().observe(this, {
            Snackbar.make(binding.parentViewCharacterDetail, getString(it), Snackbar.LENGTH_INDEFINITE)
                .setAction(
                    R.string.character_detail_exit,
                ) {
                    viewModel.onExit()
                }
                .show()
        })

        viewModel.navigation().observe(this, {
            when (it.value) {
                CharacterDetailNavigation.Home.value -> {
                    HomeActivity.launchAndClearTask(this)
                }
            }
        })

        viewModel.isLoading().observe(this, { isLoading ->
            if (isLoading) {
                binding.progressbarCharacterDetail.visibility = View.VISIBLE
                binding.constraintlayoutContent.visibility = View.INVISIBLE
            } else {
                binding.progressbarCharacterDetail.visibility = View.INVISIBLE
                binding.constraintlayoutContent.visibility = View.VISIBLE
            }
        })

        intent.extras?.getInt(EXTRA_ID)?.let { id ->
            viewModel.onExtraValue(id)
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"

        fun launch(id: Int, context: Context) {
            val intent = Intent(context, CharacterDetailActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}
