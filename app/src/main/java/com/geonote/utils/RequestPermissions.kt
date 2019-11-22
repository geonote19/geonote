package com.geonote.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onCancel
import com.geonote.App
import com.geonote.R
import java.lang.ref.WeakReference

class RequestPermissions(
    activity: Activity,
    val listPermissions: Array<String>
) {

    private val activityRef = WeakReference(activity)

    @RequiresApi(Build.VERSION_CODES.M)
    fun showCustomDialog(shouldShowRequestPermissionRationale: Boolean, message: Int) {
        val activity = getActivity() ?: return
        val dialog = MaterialDialog(activity)
            .title(R.string.title_request_permissions)

            .message(message)
        dialog.show {
            cancelable(true)
            cancelOnTouchOutside(false)
            cornerRadius(10f)
            positiveButton(R.string.positive_button_permissions) {

                if (shouldShowRequestPermissionRationale) {
                    openSettings()
                } else {
                    requestMultiplePermissions()
                }
            }
        }
        dialog.onCancel { activity.finish() }
    }

    fun openSettings() {
        val activity = getActivity() ?: return
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", App.INSTANCE.packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }

    fun requestMultiplePermissions() {
        val activity = getActivity() ?: return
        ActivityCompat.requestPermissions(
            activity,
            listPermissions,
            TO_REQUEST_PERMISSIONS_CODE
        )
    }

    private fun getActivity(): Activity? {
        val activity = activityRef.get()
        return if (activity?.isFinishing == false) activity else null
    }

    fun ifHasPermissions(): Boolean {
        val activity = getActivity() ?: return false
        var startLoadData = false
        if (!hasPermissions(activity, listPermissions)) requestMultiplePermissions()
        else startLoadData = true
        return startLoadData
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onRequestPermissionsResult(grantResults: IntArray, text: Int): Boolean {
        val activity = getActivity() ?: return false
        var startLoadData = false

        if (checkGrantResults(grantResults)) {
            showCustomDialog(
                !shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                text
            )
        } else startLoadData = true
        return startLoadData
    }

    fun checkGrantResults(grantResults: IntArray): Boolean {
        var checkResults = false
        for (i in grantResults) {
            if (i == PackageManager.PERMISSION_DENIED)
                checkResults = true
        }
        return checkResults
    }

    fun hasPermission(context: Context, permission: String): Boolean {

        val res = context.checkCallingOrSelfPermission(permission)

        return res == PackageManager.PERMISSION_GRANTED
    }

    fun hasPermissions(context: Context, permissions: Array<String>): Boolean {

        for (permission in permissions) {
            if (!hasPermission(context, permission)) {
                return false
            }
        }
        return true
    }
}