import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun FormularioScreen(viewModel: SolicitudViewModel) {

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var prioridad by remember { mutableStateOf(1f) }
    var email by remember { mutableStateOf("") }


    val tituloValido = titulo.length in 5..60
    val descripcionValida = descripcion.length in 20..500
    val emailValido = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val formularioValido = tituloValido && descripcionValida && emailValido && categoria.isNotEmpty()

    val uiState = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Formulario de Solicitud",
            style = MaterialTheme.typography.headlineSmall
        )


        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Titulo (5-60 caracteres)") },
            modifier = Modifier.fillMaxWidth(),
            isError = titulo.isNotEmpty() && !tituloValido
        )


        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripcion (20-500 caracteres)") },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            isError = descripcion.isNotEmpty() && !descripcionValida
        )


        OutlinedTextField(
            value = categoria,
            onValueChange = { categoria = it },
            label = { Text("Categoria") },
            modifier = Modifier.fillMaxWidth()
        )


        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Text("Prioridad: ${prioridad.toInt()}")
            Slider(
                value = prioridad,
                onValueChange = { prioridad = it },
                valueRange = 1f..5f,
                steps = 3
            )
        }


        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email valido") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = email.isNotEmpty() && !emailValido
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                val datos = Solicitud(
                    titulo = titulo,
                    descripcion = descripcion,
                    categoria = categoria,
                    prioridad = prioridad.toInt(),
                    email = email
                )
                viewModel.enviarFormulario(datos)
            },
            modifier = Modifier.fillMaxWidth(),

            enabled = formularioValido && uiState !is SolicitudUiState.Loading
        ) {
            if (uiState is SolicitudUiState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("Enviando...")
            } else {
                Text("Enviar")
            }
        }


        when (uiState) {
            is SolicitudUiState.Success -> {
                Text(
                    text = "Registro insertado correctamente",
                    color = Color(0xFF2E7D32),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            is SolicitudUiState.Error -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Error: ${uiState.message}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Button(
                        onClick = { /* El reintento se maneja volviendo a pulsar Enviar */ },
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text("Reintento manual")
                    }
                }
            }
            else -> {}
        }
    }
}