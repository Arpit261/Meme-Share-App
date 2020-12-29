package com.arpit.memeapp.Activities

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.arpit.memeapp.R
import com.arpit.memeapp.util.Memes
import com.arpit.memeapp.util.memeApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var currentUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        memesCall()
        nextMeme()


    }

    fun memesCall() {
        progressbar.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, memeApi, null,
            Response.Listener {


                currentUrl = it.getString("url")
                Glide.with(this).load(currentUrl).into(MemeImage)
                progressbar.visibility = View.INVISIBLE
                share()


            },
            Response.ErrorListener {
                Toast.makeText(this, "Volley Error Occured", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonObjectRequest)
    }

    fun nextMeme() {
        swipeRight.setOnClickListener {
            memesCall()
        }

    }

    fun share() {
        Share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type="text/plain"
            intent.putExtra(Intent.EXTRA_TEXT ,"Checkout This MEME ,$currentUrl")
            val chooser = Intent.createChooser(intent ,"MEMES")
            startActivity(chooser)
        }
    }
}


