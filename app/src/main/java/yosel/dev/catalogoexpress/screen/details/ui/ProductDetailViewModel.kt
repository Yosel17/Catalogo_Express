package yosel.dev.catalogoexpress.screen.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yosel.dev.catalogoexpress.screen.details.domain.ProductDetailRepository
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productDetailRepository: ProductDetailRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductDetailState())
    val state: StateFlow<ProductDetailState> = _state

    fun loadProductDetail(productId: Int) {
        if (_state.value.product?.id == productId) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            productDetailRepository.getProductDetail(productId)
                .onSuccess { product ->
                    _state.update {
                        it.copy(
                            product = product,
                            isLoading = false
                        )
                    }
                }.onFailure { error ->
                    _state.update {
                        it.copy(
                            errorMessage = error.localizedMessage ?: "Error desconocido",
                            isError = true,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onAction(action: ProductDetailAction) {
        when (action) {
            ProductDetailAction.HideDialogError -> hideDialogError()
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