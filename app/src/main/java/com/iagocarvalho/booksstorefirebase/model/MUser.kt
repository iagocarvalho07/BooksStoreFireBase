package com.iagocarvalho.booksstorefirebase.model

data class MUser(
    val id: String?,
    val userID: String,
    val displayName: String,
    val avatarURL: String,
    val quote: String,
    val profession: String,


    ){
    fun toMap(): MutableMap<String, Any>{
        return mutableMapOf(
            "user-id" to this.userID,
            "display_name" to this.displayName,
            "avatar_Url" to this.avatarURL,
            "quote" to this.quote,
            "profession" to this.profession,
        )
    }
}
