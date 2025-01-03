package br.edu.uea.ecopoints.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.viewModelScope
import br.edu.uea.ecopoints.R
import br.edu.uea.ecopoints.databinding.ActivityMainBinding
import br.edu.uea.ecopoints.screens.viewmodel.MainViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var edtEmail: TextInputEditText
    private lateinit var edtPassword: TextInputEditText
    private lateinit var textCreateAccount: TextView
    private lateinit var btnLogin: MaterialButton
    private val loginViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        setupListeners()
    }

    private fun setupView() {
        edtEmail = binding.edtLogin
        edtPassword = binding.edtPassword
        btnLogin = binding.btLogin
        loginViewModel.state.observe(this){
            state ->
                binding.pbCls.isVisible = state.isProgressVisible
                if(state.isErrorMessageVisible){
                    Log.i("ECO",state.errorMessage ?: "Erro nao identificado")
                    Toast.makeText(this,state.errorMessage,Toast.LENGTH_LONG).show()
                }
                if(state.auth!=null){
                    Log.i("ECO","${state.auth}")
                    startActivity(Intent(this,ChefActivity::class.java))
                }
        }
    }

    private fun setupListeners() {
        btnLogin.setOnClickListener {
            loginViewModel.authenticate(
                edtEmail.text.toString(),
                edtPassword.text.toString()
            )
        }
    }
}