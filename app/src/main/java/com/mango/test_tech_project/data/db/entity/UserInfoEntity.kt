package com.mango.test_tech_project.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user"
)
data class UserInfoEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val username: String?,
    val name: String?,
    val last: String?,
    val avatar: String?,
    val birthday: String?,
    val city: String?,
    val completed_task: Int?,
    val created: String?,
    val online: Boolean?,
    val status: String?,
    val phone: String?,
    val instagram: String?,
    val vk: String?
) {

}