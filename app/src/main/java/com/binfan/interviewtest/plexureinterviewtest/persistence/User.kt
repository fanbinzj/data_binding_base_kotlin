package com.binfan.interviewtest.plexureinterviewtest.persistence

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "users")
data class User(@PrimaryKey
                @ColumnInfo(name = "username")
                val userName: String)
