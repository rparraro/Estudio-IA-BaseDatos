import kotlinx.serialization.Serializable

@Serializable
data class Solicitud(
    val id: Int? = null,
    val titulo: String,
    val descripcion: String,
    val categoria: String,
    val prioridad: Int,
    val email: String,
    val created_at: String? = null
)