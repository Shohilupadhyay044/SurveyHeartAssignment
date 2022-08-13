package com.example.surveyheartassignment.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.surveyheartassignment.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.items.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

            username_detail.text = "UserName : ${intent.getStringExtra("name")}"
            email_detail.text = "Email : ${intent.getStringExtra("email")}"
            address_detail.text = "Address : ${intent.getStringExtra("address")}"

        try {
            var image = intent.getStringExtra("image")
            Log.d("Image",image.toString())
            Picasso.get()
                .load(image)
                .into(circleImageView_detail)
        }catch (e : Exception){
            e.printStackTrace()
        }
    }
}