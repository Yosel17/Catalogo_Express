package yosel.dev.catalogoexpress.screen.list.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yosel.dev.catalogoexpress.core.api.CatalogService
import yosel.dev.catalogoexpress.core.model.ProductModel
import yosel.dev.catalogoexpress.core.utils.toModel
import yosel.dev.catalogoexpress.screen.list.domain.ProductListRepository
import javax.inject.Inject

class ProductListRepositoryImpl @Inject constructor(
    private val catalogoService: CatalogService
): ProductListRepository {

    override suspend fun getListProducts(): Result<List<ProductModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = catalogoService.getProducts()
                if (response.isSuccessful && response.body() != null) {
                    val dtoList = response.body()?.products ?: emptyList()

                    val domainList = dtoList.map { it.toModel() }
                        .sortedByDescending { it.score }

                    Result.success(domainList)
                } else {
                    Result.failure(Exception("Error del servidor: ${response.code()}"))
                }
            } catch (e: java.io.IOException) {
                Result.failure(Exception("Error de conexión. Verifica tu red."))
            } catch (e: Exception) {
                Result.failure(Exception("Ocurrió un error inesperado: ${e.localizedMessage}"))
            }
        }
    }
}