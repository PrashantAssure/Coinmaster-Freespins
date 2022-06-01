package com.azzuresolutionscmrewards.azzuresolutionsc.constants

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.azzuresolutionscmrewards.azzuresolutionsc.R


class Common {
    companion object {

        const val PRIVACY_POLICY = "https://calendarinfotech.com/privacy-policy.php"
        const val COIN_MASTER_URL =
            "https://static.moonactive.net/static/coinmaster/reward/reward2.html?c="

        fun setActivity(cntActivity: AppCompatActivity) {
            activity = cntActivity
        }

        private lateinit var activity: AppCompatActivity
        fun getActivity(): AppCompatActivity {
            return activity
        }


        fun showToast(context: Context, message: String, isLongMessage: Boolean = false) {
            getActivity().runOnUiThread {
                if (isLongMessage) {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        private var progressDialog: Dialog? = null
        fun displayProgress(context: Context) {
            var context1 = context as Activity
            if (context1.isFinishing) {
                context1 = getActivity()
            }
            try {
                if (progressDialog != null && progressDialog!!.isShowing) {
                    progressDialog!!.dismiss()
                }
                progressDialog = Dialog(context1)
                progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                if (progressDialog!!.window != null) {
                    progressDialog!!.window?.setBackgroundDrawableResource(android.R.color.transparent)
                }
//                progressDialog!!.setContentView(R.layout.circular_progressbar)
                progressDialog!!.setCancelable(true)
                progressDialog!!.setCanceledOnTouchOutside(false)
                progressDialog!!.show()
            } catch (ex: java.lang.Exception) {
                Log.d("ProgressBar", "" + ex.message)
            }
        }

        fun dismissProgress() {
            try {
                if (progressDialog != null && progressDialog!!.isShowing) {
                    progressDialog!!.dismiss()
                }
            } catch (ex: java.lang.Exception) {
            }
        }

        fun isOnline(context: Context, isShowMessage: Boolean): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            val isOnline = networkInfo != null && networkInfo.isConnected
            if (!isOnline && isShowMessage) {
//                showToast(context, context.getString(R.string.internet_error))
            }
            return isOnline
        }

        fun showDialog(context: Context) {
            AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.app_name))
//                .setMessage(context.getString(R.string.no_coin_master_installed))
                .setPositiveButton(
                    android.R.string.ok
                ) { dialog, which ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .show()

        }
    }
}