package yosel.dev.catalogoexpress.screen.list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yosel.dev.catalogoexpress.screen.list.domain.ProductListRepository
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productListRepository: ProductListRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductListState())
    val state: StateFlow<ProductListState> = _state

    init {
        getListProducts()
    }

    fun onAction(action: ProductListAction) {
        when (action) {
            ProductListAction.HideDialogError -> hideDialogError()
        }
    }

    private fun getListProducts() {
        viewModelScope.launch {
            productListRepository.getListProducts()
                .onSuccess { productList ->
                    _state.update {
                        it.copy(
                            productList = productList,
                            isLoadingGetListProduct = false
                        )
                    }
                }.onFailure { error ->
                    _state.update {
                        it.copy(
                            errorMessage = error.localizedMessage ?: "Error desconocido",
                            isError = true,
                            isLoadingGetListProduct = false
                        )
                    }
                }
        }
    }

    private fun hideDialogError() {
        _state.update { currentState ->
            currentState.copy(
                isError = false,
                errorMessage = ""
            )
        }
    }
}