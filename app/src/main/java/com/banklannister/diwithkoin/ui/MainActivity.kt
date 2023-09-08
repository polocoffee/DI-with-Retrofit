package com.banklannister.diwithkoin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.banklannister.diwithkoin.adapter.PhotoAdapter
import com.banklannister.diwithkoin.databinding.ActivityMainBinding
import com.banklannister.diwithkoin.utils.DataStatus
import com.banklannister.diwithkoin.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding
    private val mainViewModel: MainViewModel by inject()
    private val photoAdapter by lazy { PhotoAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        setupRecyclerView()

        lifecycleScope.launch {
            mainViewModel.getPhoto("cat")
            mainViewModel.photoList.observe(this@MainActivity) {
                when (it.status) {
                    DataStatus.Status.LOADING -> {
                        showProgressBar(false)
                    }

                    DataStatus.Status.SUCCESS -> {
                        showProgressBar(true)
                        photoAdapter.differ.submitList(it.data)
                    }

                    DataStatus.Status.ERROR -> {
                        showProgressBar(false)
                        Toast.makeText(
                            this@MainActivity, "Error is something wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }


    private fun setupRecyclerView() {
        binding!!.rvPhoto.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = photoAdapter
        }
    }

    private fun showProgressBar(isShow: Boolean) {
        binding!!.apply {
            if (isShow) {
                rvPhoto.visibility = View.VISIBLE
                pBarLoading.visibility = View.GONE
            } else {
                rvPhoto.visibility = View.GONE
                pBarLoading.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}