package com.example.lesson41.ext

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: Uri?) {
    Glide.with(this).load(url).into(this)
}

fun Fragment.showToast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
}

fun Context.alertDialog(
    title: String,
    negativeButtonTitle: String,
    positiveButtonTitle: String,
    onPositiveClick: () -> Unit
) {
    val d = AlertDialog.Builder(this)
    d.setTitle(title)
    d.setNegativeButton(negativeButtonTitle) { dialog, p1 ->
        dialog.cancel()
    }
    d.setPositiveButton(positiveButtonTitle) { dialog, p1 ->
       onPositiveClick
        dialog.cancel()
    }
    d.create().show()
}