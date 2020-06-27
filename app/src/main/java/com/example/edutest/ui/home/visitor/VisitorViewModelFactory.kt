package com.example.edutest.ui.home.visitor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.edutest.data.repositories.VisitorRepository


@Suppress("UNCHECKED_CAST")
class VisitorViewModelFactory(
    private val repository: VisitorRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VisitorViewModel(repository) as T
    }
}