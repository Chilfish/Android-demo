package top.chilfish.labs.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import top.chilfish.labs.gpt.data.GPTDao
import top.chilfish.labs.gpt.data.GPTDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideGPTDao(db: GPTDatabase): GPTDao = db.dao()

    @Provides
    @Singleton
    fun provideGPTDatabase(
        @ApplicationContext context: Context,
    ): GPTDatabase = GPTDatabase.getDatabase(context)
}