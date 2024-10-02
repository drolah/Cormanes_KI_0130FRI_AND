package com.example.newsactivity

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HeadlinesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HeadlinesAdapter

    private val newsList = listOf(
        News("Scientists Discover a New Planet in the Habitable Zone", "In a groundbreaking discovery, astronomers at the Galactic Observatory announced the detection of a new exoplanet named \"Kepler-442c\" located within the habitable zone of its star. This Earth-sized planet may possess the right conditions for liquid water, sparking excitement among scientists. Researchers are now planning future missions to explore its atmosphere and potential for supporting life"),
        News("Local Hero Saves Family from House Fire", "In a remarkable act of bravery, 27-year-old Jason Martinez saved a family of four from a devastating house fire in downtown Springfield. The fire broke out late at night while the family was asleep. Jason, who was passing by, noticed the flames and immediately rushed to the house. He broke down the door and alerted the family, helping them escape just moments before the roof collapsed. The community is rallying to honor Jason’s heroism with a town ceremony next week."),
        News("Fictional Tale: The Lost Kingdom of Eldoria", "In a dazzling showcase, Tech Innovations Inc. revealed their latest creation, an AI assistant named \"Astra.\" Designed to seamlessly integrate into daily life, Astra can manage schedules, control smart home devices, and even hold conversations. The CEO announced that Astra is equipped with advanced learning capabilities, allowing it to adapt to user preferences over time. With privacy as a priority, the company promises robust security features, making it a game-changer in the world of technology.")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_headlines, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        adapter = HeadlinesAdapter(newsList) { selectedNews ->
            onHeadlineClick(selectedNews)
        }

        recyclerView.adapter = adapter
        return view
    }

    private fun onHeadlineClick(selectedNews: News) {
        val fragmentManager = parentFragmentManager
        val contentFragment = ContentFragment.newInstance(selectedNews.headline, selectedNews.content)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentManager.beginTransaction()
                .replace(R.id.contentFragment, contentFragment)
                .commit()
        } else {
            fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, contentFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}