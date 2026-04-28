import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class SolicitudViewModel(private val supabaseClient: SupabaseClient) : ViewModel() {

    var uiState by mutableStateOf<SolicitudUiState>(SolicitudUiState.Idle)


    fun enviarFormulario(solicitud: Solicitud) {
        viewModelScope.launch {
            uiState = SolicitudUiState.Loading
            try {

                supabaseClient.from("solicitudes").insert(solicitud)
                uiState = SolicitudUiState.Success
            } catch (e: Exception) {
                uiState = SolicitudUiState.Error("Error de red: ${e.message}")
            }
        }
    }
}