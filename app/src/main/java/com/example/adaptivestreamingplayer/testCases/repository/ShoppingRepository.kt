package com.example.adaptivestreamingplayer.testCases.repository

import androidx.lifecycle.LiveData
import com.example.adaptivestreamingplayer.room.ShoppingItem
import com.example.adaptivestreamingplayer.testCases.modal.ImageResponse
import com.example.adaptivestreamingplayer.utils.Resource

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}