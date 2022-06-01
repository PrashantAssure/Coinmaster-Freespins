package com.azzuresolutionscmrewards.azzuresolutionsc.constants

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView


class Adsmanager {
    companion object {
        var mInterstitialAd: InterstitialAd? = null
        var nativeAd: NativeAd? = null
        interface AdShowAndDisplayedListener {
            fun onAdDisplayed()
        }


        private fun initInterstitialAds(context: Context) {
            val adRequest = AdRequest.Builder().build()
            InterstitialAd.load(context, context.getString(com.azzuresolutionscmrewards.azzuresolutionsc.R.string.interstitial_id_admob),
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        super.onAdLoaded(interstitialAd)
                        mInterstitialAd = interstitialAd
                    }

                    override fun onAdFailedToLoad(p0: LoadAdError) {
                        super.onAdFailedToLoad(p0)
                    }
                })
        }

        private fun manageFullScreenEvent(
            mInterstitialAd: InterstitialAd,
            onAdDisplayedListener: AdShowAndDisplayedListener
        ) {
            mInterstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    onAdDisplayedListener.onAdDisplayed()
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    onAdDisplayedListener.onAdDisplayed()
                }
            }

        }


        fun showInterstitial(context: Activity, onAdDisplayedListener: AdShowAndDisplayedListener) {
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(context)
                manageFullScreenEvent(mInterstitialAd!!, onAdDisplayedListener)
            } else {
                onAdDisplayedListener.onAdDisplayed()
            }
            initInterstitialAds(context)
        }



        fun requestBannerAds(context: Context, linearLayout: LinearLayout) {

            val bannerAdView = AdView(context)
            bannerAdView.adUnitId = context.getString(com.azzuresolutionscmrewards.azzuresolutionsc.R.string.admob_banner_ad)
            bannerAdView.adSize = AdSize.MEDIUM_RECTANGLE
            val adRequest = AdRequest.Builder().build()
            bannerAdView.loadAd(adRequest)
            bannerAdView.adListener = object : AdListener() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    Log.d("banner", "Failed")
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    linearLayout.removeAllViews()
                    linearLayout.addView(bannerAdView)
                    Log.d("banner", "Loaded")
                }
            }
        }

        private fun populateUnifiedNativeAdView(nativeAd: NativeAd,adView: NativeAdView) {
            adView.headlineView = adView.findViewById(com.azzuresolutionscmrewards.azzuresolutionsc.R.id.ad_headline)
            adView.bodyView = adView.findViewById(com.azzuresolutionscmrewards.azzuresolutionsc.R.id.ad_body)
            adView.callToActionView = adView.findViewById(com.azzuresolutionscmrewards.azzuresolutionsc.R.id.ad_call_to_action)
            adView.iconView = adView.findViewById(com.azzuresolutionscmrewards.azzuresolutionsc.R.id.ad_app_icon)
            (adView.headlineView as TextView).text = nativeAd.headline
            if (nativeAd.body == null) {
                adView.bodyView?.visibility = View.INVISIBLE
            } else {
                adView.bodyView?.visibility = View.VISIBLE
                (adView.bodyView as TextView).text = nativeAd.body
            }
            if (nativeAd.callToAction == null) {
                adView.callToActionView?.visibility = View.INVISIBLE
            } else {
                adView.callToActionView?.visibility = View.VISIBLE
                (adView.callToActionView as Button).text = nativeAd.callToAction
            }
            if (nativeAd.icon == null) {
                adView.iconView?.visibility = View.GONE
            } else {
                (adView.iconView as ImageView).setImageDrawable(
                    nativeAd.icon?.drawable
                )
                adView.iconView?.visibility = View.VISIBLE
            }

            if (nativeAd.advertiser == null) {
                adView.advertiserView?.visibility = View.INVISIBLE
            } else {
                (adView.advertiserView as TextView).text = nativeAd.advertiser
                adView.advertiserView?.visibility = View.VISIBLE
            }
            adView.setNativeAd(nativeAd)
        }

        fun loadNativeAd(activity: Activity, nativeAdPlaceHolder: LinearLayout) {
            val builder: AdLoader.Builder =
                AdLoader.Builder(
                    activity,
                    activity.resources.getString(com.azzuresolutionscmrewards.azzuresolutionsc.R.string.native_id_admob)
                )
            builder.forNativeAd { p0 ->
                nativeAd?.destroy()
                nativeAd = p0
                @SuppressLint("InflateParams") val adView: NativeAdView =
                    activity.layoutInflater
                        .inflate(com.azzuresolutionscmrewards.azzuresolutionsc.R.layout.ad_unified_new, null) as NativeAdView
                populateUnifiedNativeAdView(p0, adView)
                nativeAdPlaceHolder.removeAllViews()
                nativeAdPlaceHolder.addView(adView)
                nativeAdPlaceHolder.visibility = View.VISIBLE
            }
            val videoOptions: VideoOptions = VideoOptions.Builder()
                .setStartMuted(true)
                .build()
            val adOptions: NativeAdOptions = NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build()
            builder.withNativeAdOptions(adOptions)
            val adLoader: AdLoader = builder.withAdListener(object : AdListener() {
                fun onAdFailedToLoad(errorCode: Int) {
                    Log.d("failNativeAd", "Failed to load native ad:: ")
                }
            }).build()
            adLoader.loadAd(AdRequest.Builder().build())
        }
    }
}