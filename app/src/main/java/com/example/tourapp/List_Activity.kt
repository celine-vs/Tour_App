package com.example.tourapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class ListActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_CONTENT_TYPE = "content_type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val contentType = intent.getStringExtra(EXTRA_CONTENT_TYPE) ?: "default"

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = when(contentType) {
                "restaurants" -> "Restaurants"
                "attractions" -> "Attractions"
                "events" -> "Events"
                "public" -> "Public Places"
                else -> "Explore"
            }}


        // Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView3)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Load different data based on content type
        val items = when (contentType) {
            "restaurants" -> getRestaurantItems()
            "attractions" -> getAttractionItems()
            "events" -> getEventItems()
            "public" -> getPublicItems()
            else -> getDefaultItems()
        }

        val adapter = ListItemAdapter(this, items)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : ListItemAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val selectedItem = items[position]

                // Create intent to launch HiddenPlaceActivity
                val intent = Intent(this@ListActivity, HiddenPlaceActivity::class.java).apply {
                    // Pass the item data as extras
                    putExtra("ITEM_TITLE", selectedItem.title)
                    putExtra("ITEM_IMAGE_RES", selectedItem.imageResId)
                    putExtra(ListActivity.EXTRA_CONTENT_TYPE, selectedItem.contentType)
                }

                startActivity(intent)

            }
        })
    }

    private fun getRestaurantItems(): List<ListItem> {
        return listOf(
            ListItem(R.drawable.cattapa1, "Catappa Restaurant", "Catappa Restaurant offers a selection of traditional, oriental and international dishes and abundant self service buffet are available daily.","cattapa"),
            ListItem(R.drawable.sanasini1, "Sana Sini Restaurant", "Drawing inspiration from the Indonesian phrase 'Sana -Sini', which means 'here and there', Pullman Jakarta Indonesia offers you an unrivaled culinary excursion into the different corners of Europe and Asia through 4 individually designed spacious counters. ","sana")
        )
    }

    private fun getAttractionItems(): List<ListItem> {
        return listOf(
            ListItem(R.drawable.safari_akuarium1, "Jakarta Aquarium & Safari", "A marine and freshwater aquarium located within a retail and leisure complex Neo Soho in Jakarta, Indonesia.","safari"),
            ListItem(R.drawable.ragunan_zoo1, "Ragunan Zoo", "The largest zoo in Jakarta.","ragunan")
        )
    }

    private fun getEventItems(): List<ListItem> {
        return listOf(
            ListItem(R.drawable.lebaranfair1, "Jakarta Lebaran Fair 2025", "Celebrating #TheMagicOfEid, this year's Jakarta Lebaran Fair promises exciting exhibitions, mouthwatering food, and spectacular entertainment!","lebaran"),
            ListItem(R.drawable.cascade1, "CASCADE: A Thematic Weekend Bazaar by Market & Museum", "The latest weekend bazaar from Market & Museum.","cascade")
        )
    }

    private fun getPublicItems(): List<ListItem> {
        return listOf(
            ListItem(R.drawable.freedom_library1, "Freedom Library", "A small library focused on politics and social economy.","freedom"),
            ListItem(R.drawable.candra1, "Candra Naya Building", "Local history museum with Dutch colonial architecture.","candra")
        )
    }

    private fun getDefaultItems(): List<ListItem> {
        return listOf(
            ListItem(R.drawable.main_photo, "Item 1", "Default description","default")
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}