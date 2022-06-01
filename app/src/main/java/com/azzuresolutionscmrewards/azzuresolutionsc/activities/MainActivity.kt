package com.azzuresolutionscmrewards.azzuresolutionsc.activities

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.azzuresolutionscmrewards.azzuresolutionsc.R
import com.azzuresolutionscmrewards.azzuresolutionsc.constants.Adsmanager
import com.azzuresolutionscmrewards.azzuresolutionsc.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = Dialog(this)
        binding.btnCoins.setOnClickListener(this)
        binding.btnSpins.setOnClickListener(this)
        binding.share.setOnClickListener(this)
        binding.rateUs.setOnClickListener(this)
        binding.feedback.setOnClickListener(this)
        binding.pap.setOnClickListener(this)
        dialog = Dialog(this)
        val nativeAdFrameOne = findViewById<LinearLayout>(R.id.nativeAdFrameLayoutBack)
        Adsmanager.loadNativeAd(this@MainActivity, nativeAdFrameOne)
        nativeAdFrameOne.visibility

    }

    override fun onClick(clickedView: View) {
        when (clickedView.id) {
            R.id.btnCoins -> {
                startActivity(Intent(this, CoinsActivity::class.java))
            }

            R.id.btnSpins -> {
                startActivity(Intent(this, SpinsActivity::class.java))
            }
            R.id.share -> {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_SUBJECT, "Coin Master Reward")
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "https://play.google.com/store/apps/details?id="
                )
                intent.type = "text/plain"
                startActivity(intent)
            }
            R.id.rate_us->{
                val dialog = BottomSheetDialog(this@MainActivity, R.style.BottomSheetDialog)
                dialog.setContentView(R.layout.bottomsheetlayout)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
                dialog.findViewById<AppCompatButton>(R.id.cancel_button)
                    ?.setOnClickListener {
                        dialog.cancel()
                    }
                dialog.findViewById<AppCompatButton>(R.id.ok_button)
                ?.setOnClickListener { try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + this.packageName)
                        )
                    )
                } catch (e: ActivityNotFoundException) {
                    moveTaskToBack(true)
                } }

            }
            R.id.feedback-> {
                val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "azzuresolutionsinc@gmail.com", null))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")
                startActivity(Intent.createChooser(emailIntent, "Send email..."))
            }
            R.id.pap->{
                val uri: Uri = Uri.parse("https://www.freeprivacypolicy.com/blog/privacy-policy-url/")
                startActivity(Intent(Intent.ACTION_VIEW,uri))
            }
        }
    }

}