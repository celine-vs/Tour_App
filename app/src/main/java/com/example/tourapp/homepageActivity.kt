package com.example.tourapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class HomepageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_homepage)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets }
        // Sample data
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false) // No back button on homepage
            title = "Tour App" // Your app name
        }

        val cardItems = listOf(
            CardItem(R.drawable.candra1, "Candra Naya Building", "Local history museum with Dutch colonial architecture.", "candra"),
            CardItem(R.drawable.freedom_library1, "Freedom Library", "A small library focused on politics and social economy.", "freedom"),
            CardItem(R.drawable.ragunan_zoo1, "Ragunan Zoo", "The largest zoo in Jakarta.", "ragunan")
        )

        val button = listOf(
            HomeButtonItem(R.drawable.baseline_restaurant_menu_24, "Restaurants", "restaurants"),
            HomeButtonItem(R.drawable.baseline_attractions_24, "Attractions", "attractions"),
            HomeButtonItem(R.drawable.event_icon, "Events", "events"),
            HomeButtonItem(R.drawable.baseline_deck_24, "Public places", "public")
        )

        // Create and set the adapter
        val cardAdapter = CardAdapter(this, cardItems)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = cardAdapter

        // Set the item click listener
//        cardAdapter.setOnItemClickListener(object : CardAdapter.OnItemClickListener {
//            override fun onItemClick(position: Int) {
//                // Ensure the title is a String (CharSequence)
//                val title = cardItems[position].title
//                Toast.makeText(this@HomepageActivity, title, Toast.LENGTH_SHORT).show()
//            }
//        })
        cardAdapter.setOnItemClickListener(object : CardAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val selectedItem = cardItems[position]

                // Create intent to launch HiddenPlaceActivity
                val intent = Intent(this@HomepageActivity, HiddenPlaceActivity::class.java).apply {
                    // Pass the item data as extras
                    putExtra("ITEM_TITLE", selectedItem.title)
                    putExtra("ITEM_IMAGE_RES", selectedItem.imageResId)
                    putExtra(ListActivity.EXTRA_CONTENT_TYPE, selectedItem.contentType)
                }

                startActivity(intent)

            }
        })

        // Create and set the adapter
        val homebuttonAdapter = HomeButtonAdapter(this, button)
        val recyclerView2 = findViewById<RecyclerView>(R.id.recyclerView2)
        recyclerView2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView2.isNestedScrollingEnabled = false
        recyclerView2.adapter = homebuttonAdapter

        // Set click listener for buttons
        homebuttonAdapter.setOnItemClickListener(object : HomeButtonAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("NAVIGATION", "Button clicked! Position: $position")
                Log.d("NAVIGATION", "Starting ListActivity with type: ${button[position].contentType}")
                val intent = Intent(this@HomepageActivity, ListActivity::class.java).apply {
                    putExtra(ListActivity.EXTRA_CONTENT_TYPE, button[position].contentType)
                }
                startActivity(intent)
            }
        })

        }
    }
