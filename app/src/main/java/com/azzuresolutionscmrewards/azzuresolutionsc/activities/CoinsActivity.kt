package com.azzuresolutionscmrewards.azzuresolutionsc.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azzuresolutionscmrewards.azzuresolutionsc.R
import com.azzuresolutionscmrewards.azzuresolutionsc.adapter.CoinsSpinsListAdapter
import com.azzuresolutionscmrewards.azzuresolutionsc.constants.Adsmanager
import com.azzuresolutionscmrewards.azzuresolutionsc.constants.Common
import com.azzuresolutionscmrewards.azzuresolutionsc.databinding.ActivityCoinsBinding
import com.azzuresolutionscmrewards.azzuresolutionsc.model.RewardResponse
import com.azzuresolutionscmrewards.azzuresolutionsc.viewmodel.RewardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class CoinsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinsBinding
    private val rewardViewModel: RewardViewModel by viewModel()
    private companion object{
        private const val TAG = "NATIVE_AD_TAG"
    }


    private lateinit var productRv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpWebView()
        val nativeAdFrameOne = findViewById<LinearLayout>(R.id.nativeAdFrameLayoutBack)
        Adsmanager.loadNativeAd(this@CoinsActivity, nativeAdFrameOne)
        nativeAdFrameOne.visibility

        productRv = findViewById(R.id.rvFreeCoins)

        rewardViewModel.getCoins().observe(this) { coins ->
            setupAdapter(coins)
        }
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
    }


    private fun setupAdapter(spins: ArrayList<RewardResponse>) {
        binding.rvFreeCoins.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = CoinsSpinsListAdapter(spins)
        binding.rvFreeCoins.adapter = adapter
        adapter.onClick = object : CoinsSpinsListAdapter.OnClick {
            override fun onItemClick(rewardResponse: RewardResponse) {
                Adsmanager.showInterstitial(this@CoinsActivity,
                    object : Adsmanager.Companion.AdShowAndDisplayedListener {
                        override fun onAdDisplayed() {
                            binding.webView.loadUrl(Common.COIN_MASTER_URL + rewardResponse.cm_token)
                        }

                    })
            }
        }
    }

    private fun setUpWebView() {
        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true
        webSettings.setGeolocationEnabled(true)
        webSettings.setSupportZoom(true)
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        binding.webView.webViewClient = Client(this)

    }

    class Client(context: CoinsActivity) : WebViewClient() {
        private val activity: CoinsActivity = context
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

            try {
                val launchIntentForPackage: Intent? =
                    activity.packageManager.getLaunchIntentForPackage("com.moonactive.coinmaster")
                if (launchIntentForPackage != null) {
                    launchIntentForPackage.action = "android.intent.action.VIEW"
                    launchIntentForPackage.data =
                        Uri.parse(url)
                    activity.startActivity(launchIntentForPackage)
                } else {
                    Common.showDialog(activity)
                }
            } catch (e: ActivityNotFoundException) {
                Common.showDialog(activity)
            }
            return true
        }
    }


}