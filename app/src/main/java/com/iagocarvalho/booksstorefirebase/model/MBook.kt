package com.iagocarvalho.booksstorefirebase.model

data class MBook (
   val id: String,
   val title: String,
  val  authors: String,
   val notes: String,


) {
    fun toMap(): MutableMap<String, Any>{
        return mutableMapOf(
            "book-id" to this.id,
            "display_Tittle" to this.title,
            "autor" to this.authors,
            "notes" to this.notes,
        )
    }
}
