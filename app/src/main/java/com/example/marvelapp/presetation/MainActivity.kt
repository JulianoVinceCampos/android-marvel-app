package com.example.marvelapp.presetation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.marvelapp.databinding.ActivityMainBinding
import com.example.marvelapp.presetation.teste.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    private lateinit var navController: NavController
//    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bind(binding)

//        val navHostFragment = supportFragmentManager
//            .findFragmentById(R.id.nav_host_container) as NavHostFragment
//
//        navController = navHostFragment.navController
//
//        binding.bottomNavMain.setupWithNavController(navController)
//
//        appBarConfiguration = AppBarConfiguration(
//            setOf()
//        )
        binding.textView.text = "0"
        setObserver(binding)

    }

    fun setObserver(binding: ActivityMainBinding) {
       val myObserver = Observer<Int> {
            binding.textView.text = it.toString()
        }

        viewModel.myLiveData.observe(this, myObserver)
    }

    private fun bind(binding: ActivityMainBinding){

        binding.soma.setOnClickListener {
            viewModel.soma(binding.textView.text.toString().toInt())

        }

        binding.subtrai.setOnClickListener{
            viewModel.subtrai(binding.textView.text.toString().toInt())
        }

    }
}