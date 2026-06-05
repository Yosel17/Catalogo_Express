package yosel.dev.catalogoexpress.screen.list.ui

sealed interface ProductListAction {

    data object HideDialogError: ProductListAction

}