package com.example.formulario
import FormularioScreen
import SolicitudViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.from
import io.ktor.websocket.WebSocketDeflateExtension.Companion.install

class MainActivity : ComponentActivity() {


    private val supabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = "https://rwgbhkimkmfvhnibsldx.supabase.co/solicitudes",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJ3Z2Joa2lta21mdmhuaWJzbGR4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzY3MzQ3OTYsImV4cCI6MjA5MjMxMDc5Nn0.rCKvoCwhUqG-bBwgprOLAp1qYYYAOwbtx9S3IFeGBqE"
        ) {
            install(Postgrest)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val viewModel = SolicitudViewModel(supabaseClient)

        setContent {
            MaterialTheme {
                FormularioScreen(viewModel)
            }
        }
    }
}
