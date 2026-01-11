package com.carollim.myrecycleapp.presentation.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carollim.myrecycleapp.domain.repository.CatalogItem
import com.carollim.myrecycleapp.domain.repository.CatalogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CatalogState(
    val isLoading: Boolean = false,
    val items: List<CatalogItem> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val repository: CatalogRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CatalogState())
    val state: StateFlow<CatalogState> = _state.asStateFlow()

    init {
        loadCatalog()
    }

    private fun loadCatalog() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            repository.getCatalogItems().collect { result ->
                result.fold(
                    onSuccess = { items ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            items = items,
                            error = null
                        )
                    },
                    onFailure = { error ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                )
            }
        }
    }
}
