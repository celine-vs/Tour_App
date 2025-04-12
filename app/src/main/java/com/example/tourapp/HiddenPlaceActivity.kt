package com.example.tourapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tourapp.databinding.ArticleActivityBinding
import com.google.android.material.appbar.MaterialToolbar

class HiddenPlaceActivity : AppCompatActivity() {
    private lateinit var binding: ArticleActivityBinding
    private var currentImageIndex = 0
    private lateinit var imageResources: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ArticleActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title =  "Details"
        }

        // Get data from intent
        val title = intent.getStringExtra("ITEM_TITLE") ?: "Hidden Place"
        val imageRes = intent.getIntExtra("ITEM_IMAGE_RES", R.drawable.main_photo)
        val contentType = intent.getStringExtra(ListActivity.EXTRA_CONTENT_TYPE) ?: "default"

        // Setup images based on content type
        imageResources = listOf(
            imageRes,  // First image is from intent
            when (contentType) {
                "candra" -> R.drawable.candra2
                "freedom" -> R.drawable.freedom_library2
                "ragunan" -> R.drawable.ragunan_zoo2
                "safari" -> R.drawable.safari_akuarium2
                "cattapa" -> R.drawable.cattapa2
                "sana" -> R.drawable.sanasini2
                "lebaran" -> R.drawable.lebaranfair2
                "cascade" -> R.drawable.cascade2
                else -> R.drawable.main_photo
            }
        )

        // Initialize UI
        binding.articleTitle.text = title
        binding.articleContent.text = getDescription(contentType)
        showImage(currentImageIndex)

        // Button listeners
        binding.FragmentButton1.setOnClickListener { showImage(0) }
        binding.FragmentButton2.setOnClickListener { showImage(1) }
        binding.articleActionButton.setOnClickListener {
            shareContent(title, getDescription(contentType))
        }
    }

    private fun showImage(index: Int) {
        if (index < imageResources.size) {
            currentImageIndex = index
            val fragment = ImageFragment().apply {
                arguments = Bundle().apply {
                    putInt("imageRes", imageResources[index])
                }
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()

            // Update button states
            binding.FragmentButton1.isSelected = index == 0
            binding.FragmentButton2.isSelected = index == 1

        }
    }

    private fun shareContent(title: String, description: String) {
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, "$title\n\n$description")
            startActivity(Intent.createChooser(this, "Share via"))
        }
    }

    private fun getDescription(contentType: String): String {
        return when (contentType) {
            "candra" -> "Located in: Green Central City\n \n" +
                    "Address: Green Central City, Jl. Gajah Mada No.RT.3 No.188, RT.2/RW.5, Glodok, Kec. Taman Sari, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11120 \n \n" +
                    "Province: Jakarta\n \n" +
                    "Phone: (021) 29365888 \n \n" +
                    "Open: 8:00-18:00 (Monday-Friday), 8:00-17:00 (Saturday-Sunday)\n \n" +
                    "Candra Naya (Hokkien: Sin Ming Hui) is an 18th-century historic building in Jakarta, Indonesia. It was home to the Khouw family of Tamboen, most notably its highest-ranking member: Khouw Kim An, the last Majoor der Chinezen ('Major of the Chinese') of Batavia (in office from 1910 until 1942). Although among the grandest colonial residences in the capital and protected by heritage laws, the compound was almost completely demolished by its new owners, the conglomerate Modern Group. The main halls have survived only thanks to vocal protests from heritage conservation groups."
            "freedom" -> "Located in: MCI Executive Search\n \n" +
                    "Address: Wisma Bakrie, Jl. H. R. Rasuna Said No.11 Kav, B-1, RT.5/RW.2, Kuningan, Karet Kuningan, Menteng, South Jakarta City, Jakarta 12920 \n \n" +
                    "Province: Jakarta\n \n" +
                    "Phone: (021) 31909226 \n \n" +
                    "Open: 9:00-17:00 \n \n" +
                    "Founded in 2001, Freedom Library moved to its current location in 2009. Scanning quickly through the library’s interior, one will see pictures of every Indonesian president, past to present. The quotations on the walls hint at its strong focus on politics and social economy, but Freedom Library also contains studies in history, literature, and art. Seventy percent of its collection is in English, and the rest are special gems by legendary Indonesian writers, such as Idrus and H.B Jassin.\n" +
                    "Freedom Library understands that an extensive book collection is needed in assisting students in their research or professionals on their current projects. However, the library does not stop with only providing information. It sets its environment to better aid all kinds of visitors. Easily accessible adapters and free wi-fi connection help you have a relaxing and productive time.\n" +
                    "Muted atmosphere is held in high esteem here. It is pleasing to see how all members adhere to proper library conduct and keep the volume low. Selfies and chatters are highly discouraged, obviously. Depending on your urgency, you can choose to read with less distraction in one of the cubicles, spill out your laptop and piles of books on a bigger table, or relax on the comfortable couches. Whatever your agenda, the subdued and calm environment in Freedom Library perfectly serves its noble purpose."
            "ragunan" -> "Address: Jl. Harsono Rm Dalam No.1, Ragunan, Ps. Minggu, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12550 \n \n" +
                    "Province: Jakarta\n \n" +
                    "Phone:  (021) 78847114 \n \n" +
                    "Open: 7:00-16:00 \n \n" +
                    "Ragunan Zoological Park, formerly and still commonly known as Ragunan Zoo, is a zoo located in the eponymous kelurahan (subdistrict) in Pasar Minggu, South Jakarta, Jakarta, Indonesia. The zoo has an area of 140-hectare (350-acre) and the largest park in Jakarta. The zoo has an aviary and a primate centre, and employs over 450 people. Many of the animals in the zoo are endangered and threatened from all parts of Indonesia and the rest of the world. There are a total of 2,288 animals inside the zoo. Laid out in a lush tropical habitat, rare animals such as crocodile, chimpanzee, gorilla, orangutan, tapir, anoa, sumatran tiger, babirusa, and peacocks are given ample room. The zoo is located in South Jakarta and is easily accessible through the Jakarta Outer Ring Road and TransJakarta Corridor 6 bus (green color)."
            "safari" -> "Located in: Neo Soho Mall\n \n" +
                    "Address: Letjen S. Parman St No.106, RT.3/RW.3, South Tanjung Duren, Grogol petamburan, West Jakarta City, Jakarta 11470 \n \n" +
                    "Province: Jakarta\n \n" +
                    "Phone:  (021) 27893435 \n \n" +
                    "Open: 10:00-19:00 \n \n" +
                    "Jakarta Aquarium and Safari is a marine and freshwater aquarium located within a retail and leisure complex Neo Soho in Jakarta, Indonesia. The aquarium is home to hundreds of mammal, reptile, insect and various types of Indonesian marine fish, is to introduce to the next generation the biodiversity of the archipelago starting from islands, forests, and mangroves.\n" +
                    "Other than Indonesian endemic animals, there are also animals from Africa and South America. The aquarium is a subsidiary of Taman Safari of Indonesia in collaboration with Aquaria KLCC of Malaysia. The Aquarium has received the accolade of Trip Advisor's Certificate of Excellence Destination 2018 and Indonesia Travel Tourism Awards (ITTA)."

            "cattapa" -> "Located in: Centra Jeep4WD\n \n" +
                    "Address: Jl H Benyamin Sueb Kav B6 Superblok Mega Kemayoran, RW.10, Gn. Sahari Sel., Kec. Kemayoran, JAKARTA, Daerah Khusus Ibukota Jakarta 10610 \n \n" +
                    "Province: Jakarta\n \n" +
                    "Phone:  0878-0414-1988 \n \n" +
                    "Open: 06:00-21:00 \n \n" +
                    "Catappa Restaurant stands out as a vibrant dining destination in Jakarta, offering a diverse buffet experience that caters to a variety of palates. The restaurant is visually appealing, featuring an inviting ambiance complemented by tasteful decorations, creating an enjoyable atmosphere for diners. The buffet layout, while praised for its variety, has received mixed feedback regarding its organization, with some guests suggesting improvements to the floor plan for a smoother dining experience. Highlights of the culinary offerings include a selection of fresh seafood, flavorful meats from the Brazilian grill, and a delightful array of desserts. The restaurant’s dessert section is particularly noteworthy, with guests raving about the blueberry crème mousse, chocolate fondue, and an impressive assortment of cakes that promise to satisfy any sweet tooth. Additionally, the staff is often recognized for their helpfulness and friendliness, enhancing the overall dining experience."
            "sana" -> "Located in: Pullman Jakarta Indonesia Thamrin CBD\n \n" +
                    "Address:  Pullman Jakarta Indonesia (Lobby Level, Jl. M.H. Thamrin No.59, RT.9/RW.5, Gondangdia, Kec. Menteng, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10350 \n \n" +
                    "Province: Jakarta\n \n" +
                    "Phone:  (021) 3906444 \n \n" +
                    "Open: 06:00-22:00 \n \n" +
                    "Drawing inspiration from the Indonesian phrase 'Sana -Sini', which means 'here and there', Pullman Jakarta Indonesia offers you an unrivaled culinary excursion into the different corners of Europe and Asia through 4 individually designed spacious counters. Casually eat your way through different continents and hop across live cooking stations, as the restaurant serves breakfast, lunch and dinner buffet as well as à la carte, surrounded by contemporary décor. Complement your dining experience with an extensive wine list from Vinoteca - a global Pullman concept and a seductive selection of sweets from the bakery."
            "lebaran" -> "Located in: JIExpo Kemayoran\n" +
                    "Address: Jl. Benyamin Sueb No.1, Jakarta Pusat 14410\n" +
                    "Province: Jakarta\n" +
                    "Phone: (021) 2660-1111\n" +
                    "Hours: 10:00-22:00 daily (March 19 - April 6, 2025)\n" +
                    "Theme: #KeajaibanLebaran (Magic of Eid)\n" +
                    "Features:\n" +
                    "• Cultural exhibitions\n" +
                    "• Food festival with local delicacies\n" +
                    "• Nightly iftar gatherings\n" +
                    "• Traditional performances\n" +
                    "Ticket Information:\n" +
                    "• Weekdays: Rp25,000\n" +
                    "• Weekends/Holidays: Rp35,000\n" +
                    "• Free admission for seniors (60+) and children under 1m";
            "cascade" -> "Located in: Lippo Mall Kemang\n" +
                    "Address: Jl. Pangeran Antasari No.36, Kemang, Jakarta Selatan 12730\n" +
                    "Province: Jakarta\n" +
                    "Phone: Contact venue for details\n" +
                    "Hours: 10:00-22:00 daily (March 6-9, 2025)\n" +
                    "Theme: Surreal art fantasy experience\n" +
                    "Features:\n" +
                    "• 200+ local fashion and homeware brands\n" +
                    "• Immersive art installations\n" +
                    "• Special Ramadan iftar dining\n" +
                    "• Live entertainment\n" +
                    "Admission: Free entry";
            else -> "Discover city secrets..."
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}