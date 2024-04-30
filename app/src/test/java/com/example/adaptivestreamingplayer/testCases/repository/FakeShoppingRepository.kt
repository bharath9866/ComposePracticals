package com.example.adaptivestreamingplayer.testCases.repository

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.adaptivestreamingplayer.room.ShoppingItem
import com.example.adaptivestreamingplayer.testCases.modal.ImageResponse
import com.example.adaptivestreamingplayer.utils.Resource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class FakeShoppingRepository: ShoppingRepository {
    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)

    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false
    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun refreshLiveData() {
        GlobalScope.launch {
            observableShoppingItems.postValue(shoppingItems)
            observableTotalPrice.postValue(getTotalPrice())
        }
    }

    private fun getTotalPrice(): Float {
        return shoppingItems.sumOf { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if(shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {
            Resource.success(ImageResponse(0, 0, arrayListOf()))
        }
    }

}