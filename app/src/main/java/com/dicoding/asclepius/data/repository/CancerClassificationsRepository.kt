package com.dicoding.asclepius.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.asclepius.data.local.entity.CancerClassificationsEntity
import com.dicoding.asclepius.data.local.room.CancerClassificationsDao
import com.dicoding.asclepius.data.local.room.CancerClassificationsRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CancerClassificationsRepository(application: Application) {
    private val cancerClassificationsDao: CancerClassificationsDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = CancerClassificationsRoomDatabase.getDatabase(application)
        cancerClassificationsDao = db.favoriteUserDao()
    }

    fun getAllCancerClassifications(): LiveData<List<CancerClassificationsEntity>> = cancerClassificationsDao.getAllCancerClassifications()

    fun insert(cancerClassificationsEntity: CancerClassificationsEntity) {
        executorService.execute { cancerClassificationsDao.insert(cancerClassificationsEntity) }
    }

    fun delete(cancerClassificationsEntity: CancerClassificationsEntity) {
        executorService.execute { cancerClassificationsDao.delete(cancerClassificationsEntity) }
    }
}