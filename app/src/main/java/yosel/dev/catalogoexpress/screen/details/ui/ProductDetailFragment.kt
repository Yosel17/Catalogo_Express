package yosel.dev.catalogoexpress.screen.details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
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
                    Text("Producto $productId")
//                    ProductDetailScreen(
//                        productId = productId,
//                        modifier = Modifier.fillMaxSize()
//                    )
                }
            }
        }
    }
}