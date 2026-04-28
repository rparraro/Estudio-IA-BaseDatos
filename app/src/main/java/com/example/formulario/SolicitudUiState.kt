sealed class SolicitudUiState {
    object Idle : SolicitudUiState()
    object Loading : SolicitudUiState()
    object Success : SolicitudUiState()
    data class Error(val message: String) : SolicitudUiState()
}