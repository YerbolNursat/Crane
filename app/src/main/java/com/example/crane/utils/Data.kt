package com.example.crane.utils

import android.content.Context
import com.example.crane.entities.*
import com.google.gson.Gson


fun getCraneInfoResponseFromAssetFile(context: Context): CraneInfoResponse {
    val gson = Gson()
    val assetManager = context.assets
    val inputStream = assetManager.open("CraneInfo")
    val data = inputStream.bufferedReader().use { it.readText() }
    inputStream.close()
    return gson.fromJson(data, CraneInfoResponse::class.java)
}

fun getCraneMechInfoResponseFromAssetFile(context: Context, id: Int): CraneMechInfo? {
    val gson = Gson()
    val assetManager = context.assets
    val inputStream = assetManager.open("CraneMechPartInfo")
    val data = inputStream.bufferedReader().use { it.readText() }
    inputStream.close()
    gson.fromJson(data, CraneMechInfoResponse::class.java).list.forEach {
        if (it.id == id)
            return it
    }
    return null
}

fun getCraneElInfoResponseFromAssetFile(context: Context, id: Int): CraneElInfo? {
    val gson = Gson()
    val assetManager = context.assets
    val inputStream = assetManager.open("CraneElPartInfo")
    val data = inputStream.bufferedReader().use { it.readText() }
    inputStream.close()
    gson.fromJson(data, CraneElInfoResponse::class.java).list.forEach {
        if (it.id == id)
            return it
    }
    return null
}