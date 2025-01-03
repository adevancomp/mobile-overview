package br.edu.uea.ecopoints.screens

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import br.edu.uea.ecopoints.R
import br.edu.uea.ecopoints.data.network.api.EcoApi
import br.edu.uea.ecopoints.databinding.ActivityChefBinding
import br.edu.uea.ecopoints.domain.Chef
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class ChefActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChefBinding
    private lateinit var txtChefInfo: TextView
    private lateinit var btnGet: MaterialButton
    @Inject lateinit var api: EcoApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChefBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupListeners()
    }

    private fun setupView() {
        txtChefInfo = binding.tvChefInfo
        btnGet = binding.btGet
    }

    private fun setupListeners() {
        btnGet.setOnClickListener {
                lifecycleScope.launch {
                    val chef: Response<Chef> = withContext(Dispatchers.IO) { api.getChefById(1) }
                    if (chef.isSuccessful) {
                        Log.i("ECO", chef.body().toString())
                        with(Dispatchers.Main) {
                            txtChefInfo.text = chef.body().toString()
                        }
                    } else {
                        Log.i("ECO", "Erro no get chef ${chef.code()}")
                    }
                }
        }
    }
}