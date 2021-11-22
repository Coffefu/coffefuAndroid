package com.example.coffefu.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.coffefu.R
import com.example.coffefu.database.DatabaseControl
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class Feedback : Fragment() {
    private lateinit var feedbackButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        feedbackButton = view.findViewById(R.id.feedback_btn)

        feedbackButton.setOnClickListener {

            val client = HttpClient()
            val url = "https://api.telegram.org/bot2107178811:AAGGW4yVxkC8ogtfvxiYwVhzPE8CjNLp4Jg/sendMessage?text=Привет с приложения coffefu&chat_id=-695809459"
            val response: HttpResponse
            runBlocking {
                withContext(Dispatchers.IO) {
                    response = client.get(url)
                }
            }
            client.close()

        }
    }
}