package com.mango.test_tech_project.data.model

import com.mango.test_tech_project.data.db.entity.UserInfoEntity

data class UserProfileSend(
    val avatar: String,
    val avatars: Avatars,
    val birthday: String,
    val city: String,
    val completed_task: Int,
    val created: String,
    val id: Int,
    val instagram: String,
    val last: String,
    val name: String,
    val online: Boolean,
    val phone: String,
    val status: String,
    val username: String,
    val vk: String
) {

    fun toUserInfoEntity() = UserInfoEntity(
        id, username, name, last, avatar, birthday, city, completed_task, created, online, status, phone, instagram, vk
    )

}