package com.example.adaptivestreamingplayer.di

import android.content.Context
import androidx.room.Room
import com.example.adaptivestreamingplayer.ktor.HttpRoutes
import com.example.adaptivestreamingplayer.room.ShoppingDao
import com.example.adaptivestreamingplayer.room.ShoppingItemDatabase
import com.example.adaptivestreamingplayer.testCases.remote.PixabayAPI
import com.example.adaptivestreamingplayer.testCases.repository.DefaultShoppingRepository
import com.example.adaptivestreamingplayer.testCases.repository.ShoppingRepository
import com.example.adaptivestreamingplayer.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(shoppingDao:ShoppingDao, api:PixabayAPI) =
        DefaultShoppingRepository(shoppingDao = shoppingDao, pixabayAPI = api) as ShoppingRepository

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayAPI {
        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(HttpRoutes.PIXABAY_URL)
            .build()
            .create(PixabayAPI::class.java)
    }
}