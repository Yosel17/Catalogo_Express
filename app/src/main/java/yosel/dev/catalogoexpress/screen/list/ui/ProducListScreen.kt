@file:OptIn(ExperimentalMaterial3Api::class)

package yosel.dev.catalogoexpress.screen.list.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import yosel.dev.catalogoexpress.core.components.DialogError

@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    state: ProductListState,
    onAction: (ProductListAction) -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Lista de Productos")}
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
                .imePadding()
        ) {
            when {
                state.isLoadingGetListProduct -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                else -> {
                    BodyListProduct(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp),
                        state = state,
                        onNavigateToDetail = onNavigateToDetail
                    )
                }
            }
        }
    }

    if (state.isError) {
        DialogError(
            message = state.errorMessage,
            onDismiss = {
                onAction(ProductListAction.HideDialogError)
            }
        )
    }
}