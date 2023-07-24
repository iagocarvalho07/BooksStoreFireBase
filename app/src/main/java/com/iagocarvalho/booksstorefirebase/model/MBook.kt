package com.iagocarvalho.booksstorefirebase.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import java.sql.Timestamp

data class MBook (

    @Exclude var id: String? = null,

    @get:PropertyName("title")
    @set:PropertyName("title")
    var title: String? = null,

    @get:PropertyName("authors")
    @set:PropertyName("authors")
    var  authors: String? = null,

    @get:PropertyName("notes")
    @set:PropertyName("notes")
    var notes: String? = null,

    @get:PropertyName("photo_url")
    @set:PropertyName("photo_url")
    var photoURl: String? = null,

    @get:PropertyName("category")
    @set:PropertyName("category")
    var category: String? = null,

    @get:PropertyName("publishe_data")
    @set:PropertyName("publishe_data")
    var publisheData: String? = null,

    @get:PropertyName("reating")
    @set:PropertyName("reating")
    var rating: Double? = null,

    @get:PropertyName("descripiton")
    @set:PropertyName("descripiton")
    var description: String? = null,

    @get:PropertyName("page_count")
    @set:PropertyName("page_count")
    var pageCount: String? = null,

    @get:PropertyName("started_raiding")
    @set:PropertyName("started_raiding")
    var startedRaiding: Timestamp? = null,

    @get:PropertyName("fineshe_reading")
    @set:PropertyName("fineshe_reading")
    var finisheReading: Timestamp? = null,

    @get:PropertyName("user_id")
    @set:PropertyName("user_id")
    var userId: String? = null,

    @get:PropertyName("google_book_id")
    @set:PropertyName("google_book_id")
    var googleBookId: String? = null,
)
