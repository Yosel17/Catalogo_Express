package yosel.dev.catalogoexpress.screen.list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
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
            // Asegura que la composición se destruya de forma correcta con el ciclo de vida del fragment
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                CatalogoExpressTheme {
                    // Aquí mandas a llamar tu pantalla hecha puramente en Compose
                    Button(
                        onClick = {
                            val action = ProductListFragmentDirections
                                .actionProductListToProductDetail(1)
                            findNavController().navigate(action)
                        }
                    ) { }
                    Text("ProductList")
//                    ProductListScreen(
//                        modifier = Modifier.fillMaxSize(),
//                        onNavigateToDetail = { productId ->
//                            // Navegación nativa hacia el Fragment de Detalle
//                            val action = ProductListFragmentDirections
//                                .actionProductListToProductDetail(productId)
//                            findNavController().navigate(action)
//                        }
//                    )
                }
            }
        }
    }
}