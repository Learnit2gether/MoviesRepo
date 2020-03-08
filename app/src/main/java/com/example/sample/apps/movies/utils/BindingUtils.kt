package com.example.sample.apps.movies.utils

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.sample.apps.movies.BuildConfig


@BindingAdapter("android:src")
fun addImage(iv: ImageView, url: String?) {
    if (url.isNullOrEmpty().not()) {
        Glide.with(iv.context).load("${BuildConfig.IMAGEHOST + url}").into(iv)
    }
}

fun errorDialog(context: Context,message: String){
    val dialog = AlertDialog.Builder(context)
        .setMessage(message)
        .setPositiveButton("OK",object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
            }
        })
        .create()

    dialog.show()
}