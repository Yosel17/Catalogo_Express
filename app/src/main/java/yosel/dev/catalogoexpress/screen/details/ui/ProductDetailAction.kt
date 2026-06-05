package yosel.dev.catalogoexpress.screen.details.ui

sealed interface ProductDetailAction {
    data object HideDialogError : ProductDetailAction
}