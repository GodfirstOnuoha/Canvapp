package com.godfirst.canvapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val canv = Canv(this)
        canv.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
        canv.contentDescription = "Canvap is an app that shows two different inset"
        setContentView(canv)
    }
}