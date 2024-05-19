package com.example.tasarmprojesi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _commentAdded = MutableLiveData<Pair<String, Long>>()
    val commentAdded: LiveData<Pair<String, Long>> get() = _commentAdded

    fun notifyCommentAdded(postId: String, newCommentCount: Long) {
        _commentAdded.value = Pair(postId, newCommentCount)
    }
}
