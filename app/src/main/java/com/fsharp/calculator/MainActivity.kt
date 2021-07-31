package com.fsharp.calculator

import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random
import android.media.AudioManager
import kotlinx.android.synthetic.main.view_scarculator_output.*

class MainActivity : AppCompatActivity() {

    private val images = arrayOf("scary_one", "scary_two", "scary_three", "scary_four")
    private lateinit var popupWindow: PopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set audio to max
        maxVolume()

        key_0.setOnClickListener { output_layout.addItem("0") }
        key_1.setOnClickListener { output_layout.addItem("1") }
        key_2.setOnClickListener { output_layout.addItem("2") }
        key_3.setOnClickListener { output_layout.addItem("3") }
        key_4.setOnClickListener { output_layout.addItem("4") }
        key_5.setOnClickListener { output_layout.addItem("5") }
        key_6.setOnClickListener { output_layout.addItem("6") }
        key_7.setOnClickListener { output_layout.addItem("7") }
        key_8.setOnClickListener { output_layout.addItem("8") }
        key_9.setOnClickListener { output_layout.addItem("9") }

        key_comma.setOnClickListener { output_layout.addItem(".") }

        key_clear.setOnClickListener { output_layout.clear() }
        key_remove.setOnClickListener { output_layout.removeItem() }
        key_equal.setOnClickListener {
            output_layout.solve()
            maxVolume()
            // Delay and Popup scary image
            delayImage()
        }

        key_divide.setOnClickListener { output_layout.addItem("/") }
        key_multiply.setOnClickListener { output_layout.addItem("*") }
        key_add.setOnClickListener { output_layout.addItem("+") }
        key_minus.setOnClickListener { output_layout.addItem("-") }
        key_percent.setOnClickListener { output_layout.addItem("%") }

        // Add listener to info button
        imageButton.setOnClickListener { output_layout.popupInfoView() }
    }

    private fun maxVolume(){
        val audioManager = this.getSystemService(AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
            0
        )
    }

    private fun delayImage() {
        val mediaPlayer: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.scary_sound)
        val initDelay = Random.nextLong(200, 600)

        Handler(Looper.getMainLooper()).postDelayed({
            this@MainActivity.runOnUiThread {
                mediaPlayer.start()
            }
        }, initDelay)

        Handler(Looper.getMainLooper()).postDelayed({
            this@MainActivity.runOnUiThread {
                popupScaryImage()
            }
        }, initDelay+100)

        Handler(Looper.getMainLooper()).postDelayed({
            this@MainActivity.runOnUiThread {
                popupWindow.dismiss()
                mediaPlayer.stop()
            }
        }, initDelay + 2000)
    }

    private fun popupScaryImage() {
        // Initialize a new layout inflater instance
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Inflate a custom view using layout inflater
        val view = inflater.inflate(R.layout.view_scary_image, null)

        val image = view.findViewById<ImageView>(R.id.image)
        image.setImageResource(resources.getIdentifier(images[Random.nextInt(0, 3)],"drawable", packageName))

        // Initialize a new instance of popup window
        popupWindow = PopupWindow(
            view, // Custom view to show in popup window
            LinearLayout.LayoutParams.MATCH_PARENT, // Width of popup window
            LinearLayout.LayoutParams.MATCH_PARENT // Window height
        )

        // Set an elevation for the popup window
        popupWindow.elevation = 10.0F

        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

        val viewGroup = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup

        // Finally, show the popup window on app
        TransitionManager.beginDelayedTransition(viewGroup as ViewGroup?)
        popupWindow.showAtLocation(
            viewGroup, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0  // Y offset
        )

    }

}