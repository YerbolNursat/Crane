package com.example.crane.utils

import android.app.Activity
import android.content.Context
import com.example.crane.entities.CraneInfoResponse
import com.google.gson.Gson
import timber.log.Timber


fun getCraneInfoResponseFromAssetFile(context: Context): CraneInfoResponse {
    val gson = Gson()
    val assetManager = context.assets
    val inputStream = assetManager.open("CranInfo")
    val data = inputStream.bufferedReader().use { it.readText() }
    inputStream.close()
    return gson.fromJson(data, CraneInfoResponse::class.java)
}

