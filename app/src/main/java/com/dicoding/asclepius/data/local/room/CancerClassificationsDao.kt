package com.dicoding.asclepius.data.local.room


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.asclepius.data.local.entity.CancerClassificationsEntity

@Dao
interface CancerClassificationsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: CancerClassificationsEntity)

    @Delete
    fun delete(note: CancerClassificationsEntity)

    @Query("SELECT * from cancer_classifications ORDER BY id ASC")
    fun getAllCancerClassifications(): LiveData<List<CancerClassificationsEntity>>
}