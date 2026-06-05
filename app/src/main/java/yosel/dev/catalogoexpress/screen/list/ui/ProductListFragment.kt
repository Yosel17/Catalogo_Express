package yosel.dev.catalogoexpress.screen.list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
class ProductListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                CatalogoExpressTheme {
                    val viewModel = hiltViewModel<ProductListViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()

                    ProductListScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background),
                        state = state,
                        onAction = viewModel::onAction,
                        onNavigateToDetail = { productId ->
                            val action = ProductListFragmentDirections
                                .actionProductListToProductDetail(productId)
                            findNavController().navigate(action)
                        }
                    )
                }
            }
        }
    }
}