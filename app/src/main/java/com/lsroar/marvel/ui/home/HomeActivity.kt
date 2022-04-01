package com.lsroar.marvel.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.lsroar.marvel.R
import com.lsroar.marvel.databinding.ActivityHomeBinding
import com.lsroar.marvel.ui.characterdetail.CharacterDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    companion object {
        fun launchAndClearTask(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
    }

    private val viewModel: HomeViewModel by viewModel<HomeViewModelImpl>()
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        viewModel.characterList().observe(this, {
            binding.recyclerviewHome.adapter = HomeAdapter(it, viewModel, this)
        })

        viewModel.isLoading().observe(this, { isLoading ->
            if (isLoading) {
                binding.progressbarHome.visibility = View.VISIBLE
                binding.recyclerviewHome.visibility = View.INVISIBLE
            } else {
                binding.progressbarHome.visibility = View.INVISIBLE
                binding.recyclerviewHome.visibility = View.VISIBLE
            }
        })

        viewModel.error().observe(this, {
            Snackbar.make(binding.parentViewHome, getString(it), Snackbar.LENGTH_INDEFINITE)
                .setAction(
                    R.string.home_action_retry,
                ) {
                    viewModel.onRetry()
                }
                .show()
        })

        viewModel.itemToLoad().observe(this, { id ->
            CharacterDetailActivity.launch(id, this)
        })

        viewModel.onScreenLoaded()
    }
}
