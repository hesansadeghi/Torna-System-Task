package com.example.tornasystemiso8583task.di

import android.content.Context
import androidx.room.Room
import com.example.tornasystemiso8583task.db.ISO8583DataBase
import com.example.tornasystemiso8583task.utils.Constant.DB_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            ISO8583DataBase::class.java,
            DB_DATABASE_NAME
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration(false)
            .build()


    @Provides
    @Singleton
    fun provideDao(db: ISO8583DataBase) = db.dao()


}