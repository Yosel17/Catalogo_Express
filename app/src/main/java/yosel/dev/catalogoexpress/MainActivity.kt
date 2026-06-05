package yosel.dev.catalogoexpress

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Creamos de forma dinámica el contenedor donde se van a intercambiar los fragmentos
        val rootContainer = FragmentContainerView(this).apply {
            id = R.id.nav_graph // Usamos un ID de recurso válido
        }
        setContentView(rootContainer)

        // Inicializamos el Grafo de fragmentos de forma segura
        if (savedInstanceState == null) {
            // Pasamos el ID de tu grafo XML (nav_graph.xml)
            val navHostFragment = NavHostFragment.create(R.navigation.nav_graph)

            supportFragmentManager.beginTransaction()
                .replace(rootContainer.id, navHostFragment)
                .setPrimaryNavigationFragment(navHostFragment)
                .commit()
        }
    }
}