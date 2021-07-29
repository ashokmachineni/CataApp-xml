package com.example.cats.presentation.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.cats.BuildConfig
import com.example.cats.R
import com.example.cats.utility.showDialog
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import retrofit2.HttpException
import javax.net.ssl.HttpsURLConnection
import kotlin.coroutines.CoroutineContext

/**
 * This is BaseActivity where we add commons things which we will used in every activity
 */
abstract class BaseActivity : AppCompatActivity(), CoroutineScope {

    companion object {
        var dialogShowing = false
    }

    lateinit var job: Job
    private var progress: CustomProgressDialog? = null
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job


    abstract fun getBaseViewModel(): BaseViewModel?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()

        progress = CustomProgressDialog(this)

        attachBaseObserver()
    }

    private fun attachBaseObserver() {
        getBaseViewModel()?.exceptionLiveData?.observe(this, Observer {
            hideProgress()
            it?.apply {
                when (this) {
                    is HttpException -> {
                        when (this.code()) {
                            HttpsURLConnection.HTTP_UNAUTHORIZED -> showErrorDialog(getString(R.string.exception_error_unauthorized))
                            HttpsURLConnection.HTTP_FORBIDDEN -> showErrorDialog(getString(R.string.exception_error_forbidden))
                            HttpsURLConnection.HTTP_INTERNAL_ERROR -> showErrorDialog(getString(R.string.exception_error_server))
                            HttpsURLConnection.HTTP_BAD_REQUEST -> showErrorDialog(getString(R.string.exception_error_bad_request))
                            else -> this.localizedMessage
                        }
                    }
                    is JsonSyntaxException -> {
                        showErrorDialog(getString(R.string.exception_error_unparceble))
                    }
                    else -> {
                        if (BuildConfig.DEBUG) {
                            showErrorDialog(this.message!!)
                        } else {
                            showErrorDialog(getString(R.string.something_went_wrong))
                        }
                    }
                }
            }
        })
    }

    fun showErrorDialog(message: String) {
        showDialog(
            message,
            getString(R.string.ok), { dialog, _ ->
                dialog.dismiss()
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun showProgress() {
        if (!this.isFinishing) {
            progress?.show()
        }
    }

    fun hideProgress() {
        if (!this.isFinishing && progress?.isShowing == true) {
            progress?.dismiss()
        }
    }

}