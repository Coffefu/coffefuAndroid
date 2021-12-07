package com.example.coffefu.fragments

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffefu.R
import com.example.coffefu.adapters.ProductsRecyclerAdapter
import com.example.coffefu.database.DatabaseControl
import com.example.coffefu.entities.ProductPosition
import com.example.coffefu.utils.ToastAlert
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


interface ProductRecyclerListener {
    fun updateRecycleView()
}

data class Order(val coffee_house_id: String, val order_content: String, val time: String)


class Basket : Fragment(), ProductRecyclerListener {
    private lateinit var productsRecyclerAdapter: ProductsRecyclerAdapter
    private lateinit var productsList: List<ProductPosition>
    private lateinit var coffeePositions: RecyclerView
    private lateinit var orderPrice: TextView
    private lateinit var orderButton: Button
    private lateinit var timeButton: Button
    private lateinit var userName: EditText
    private lateinit var corpusD: Button
    private lateinit var corpusE: Button
    private var hour: Int = 0
    private var minute: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        coffeePositions = view.findViewById(R.id.coffeePositions)
        orderPrice = view.findViewById(R.id.order_price)
        orderButton = view.findViewById(R.id.order_btn)
        timeButton = view.findViewById(R.id.time_btn)
        userName = view.findViewById(R.id.username)

        corpusD = view.findViewById(R.id.corpus_d)
        corpusE = view.findViewById(R.id.corpus_e)
        var activeCorpus = corpusD

        fun changeActiveCorpus(newActive: Button) {
            corpusD.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.rounded_border)
            corpusE.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.rounded_border)
            activeCorpus = newActive
            newActive.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.active_size)
        }

        corpusD.setOnClickListener { changeActiveCorpus(corpusD) }
        corpusE.setOnClickListener { changeActiveCorpus(corpusE) }

        var picked = false

        val cal = Calendar.getInstance()
        timeButton.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                timeButton.text = SimpleDateFormat("HH:mm").format(cal.time)
                picked = true
            }
            TimePickerDialog(
                context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true,
            ).show()
        }

        orderButton.setOnClickListener {

            if (userName.text.toString() == "") {
                ToastAlert(
                    requireContext(),
                    "Не введено имя.", Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (picked) {
                val client = HttpClient() {
                    install(JsonFeature) {
                        serializer = GsonSerializer() {
                            setPrettyPrinting()
                            disableHtmlEscaping()
                        }
                    }
                }
                val response: HttpResponse
                val date = LocalDateTime.of(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE)
                )
                val plus5minutes = LocalDateTime.of(
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH) + 1,
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                    (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + (Calendar.getInstance()
                        .get(Calendar.MINUTE) + 5) / 60) % 23,
                    (Calendar.getInstance().get(Calendar.MINUTE) + 5) % 60
                )
                if (LocalDateTime.now() > date) {
                    Handler(Looper.getMainLooper()).post {
                        ToastAlert(
                            requireContext(),
                            "Неверное время заказа.", Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    if (plus5minutes > date) {
                        Handler(Looper.getMainLooper()).post {
                            ToastAlert(
                                requireContext(),
                                "Минимальное время выполнения заказа 5 минут.", Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        runBlocking {
                            withContext(Dispatchers.IO) {
                                val orderContent =
                                    DatabaseControl().getProductsTask(requireContext())
                                var orderContentString = ""

                                for (product in orderContent) {
                                    orderContentString += "${product.getName()} ${product.getCount()}x ${product.getSize()} \n"
                                }
                                if (orderContentString == "") {
                                    Handler(Looper.getMainLooper()).post {
                                        ToastAlert(
                                            requireContext(),
                                            "Ваша корзина пуста.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    try {
                                        orderContentString += "Имя заказчика - ${userName.text}\n"
                                        orderContentString += "Кофейня - ${activeCorpus.text}"
                                        response = client.post("https://coffefubot.herokuapp.com") {
                                            contentType(ContentType.Application.Json)
                                            body = Order("1234", orderContentString, date.toString())
                                        }
                                        DatabaseControl().deleteProductsTask(requireContext())

                                    } catch (e: ClientRequestException) {
                                    }
                                    Handler(Looper.getMainLooper()).post {
                                        ToastAlert(
                                            requireContext(),
                                            "Заказ выполнен.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                        this.updateRecycleView()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Пожалуйста, выберите время", Toast.LENGTH_SHORT)
                    .show()
            }
        }


        updateRecycleView()
    }

    override fun updateRecycleView() {
        runBlocking {
            withContext(Dispatchers.IO) {
                productsList = DatabaseControl().getSumCounts(requireContext())
            }
        }
        var sum = 0
        for (product in productsList) {
            sum += product.getPrice().toInt() * product.getCount()
        }
        orderPrice.text = "Итого: " + sum.toString() + " руб."
        productsRecyclerAdapter = ProductsRecyclerAdapter(
            productsList,
            context,
            "Basket",
            this,
        )
        coffeePositions.layoutManager = LinearLayoutManager(context)
        coffeePositions.adapter = productsRecyclerAdapter
    }
}