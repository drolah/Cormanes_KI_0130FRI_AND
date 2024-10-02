package com.example.newsactivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ContentFragment : Fragment() {

    companion object {
        private const val ARG_HEADLINE = "headline"
        private const val ARG_CONTENT = "content"

        fun newInstance(headline: String, content: String): ContentFragment {
            val fragment = ContentFragment()
            val args = Bundle()
            args.putString(ARG_HEADLINE, headline)
            args.putString(ARG_CONTENT, content)
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_content, container, false)

        val headlineTextView = view.findViewById<TextView>(R.id.headline_text)
        val contentTextView = view.findViewById<TextView>(R.id.content_text)

        val headline = arguments?.getString(ARG_HEADLINE)
        val content = arguments?.getString(ARG_CONTENT)

        headlineTextView.text = headline
        contentTextView.text = content

        return view
    }
}