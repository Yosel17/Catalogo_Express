package yosel.dev.catalogoexpress.screen.details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import yosel.dev.catalogoexpress.ui.theme.CatalogoExpressTheme

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Recuperación manual y segura desde los argumentos del Fragment
        val productId = arguments?.getInt("productId") ?: -1

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                CatalogoExpressTheme {
                    val viewModel = hiltViewModel<ProductDetailViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()

                    // Se ejecuta de manera segura al entrar en la composición del fragmento
                    LaunchedEffect(productId) {
                        if (productId != -1) {
                            viewModel.loadProductDetail(productId)
                        }
                    }

                    ProductDetailScreen(
                        modifier = Modifier.fillMaxSize(),
                        state = state,
                        onAction = viewModel::onAction,
                        onNavigateBack = {
                            findNavController().navigateUp()
                        }
                    )
                }
            }
        }
    }
}