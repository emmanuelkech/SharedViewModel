package com.example.sharedviewmodel.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 5.00
private const val SAME_DAY_PICKUP = 2.00

class OrderViewModel : ViewModel() {

    val dateOptions = getPickUp()

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _price = MutableLiveData<Double>()
    val price: LiveData<Double> = _price

    init {
        reset()
    }

    fun setQuantity(numberCakes: Int) {
        _quantity.value = numberCakes
        updatePrice()
    }

    fun setFlavor(flavor: String) {
        _flavor.value = flavor
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    private fun getPickUp(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

    private fun updatePrice() {
        var totalPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE
//        _price.value = (quantity.value ?: 0) * PRICE_PER_CUPCAKE
        if (dateOptions[0] == _date.value) {
            totalPrice += SAME_DAY_PICKUP
        }
        _price.value = totalPrice
    }

    private fun reset() {
        _quantity.value = 0
        _flavor.value = ""
        _price.value = 0.0
        _date.value = dateOptions[0]
    }
}