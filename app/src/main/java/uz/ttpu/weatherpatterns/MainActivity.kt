package uz.ttpu.weatherpatterns

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uz.ttpu.weatherpatterns.mvc.view.MvcActivity
import uz.ttpu.weatherpatterns.mvp.view.MvpActivity
import uz.ttpu.weatherpatterns.mvvm.view.MvvmActivity
import uz.ttpu.weatherpatterns.mvi.view.MviActivity
class MainActivity : ComponentActivity() {
 override fun onCreate(savedInstanceState: Bundle?) {
 super.onCreate(savedInstanceState)
 enableEdgeToEdge()
 setContent { MaterialTheme { Launcher { startActivity(Intent(this, it)) } } }
 }
}
@Composable
private fun Launcher(onOpen: (Class<*>) -> Unit) {
 Column(Modifier.fillMaxSize().safeDrawingPadding().padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
 Text("Weather: four patterns", style = MaterialTheme.typography.headlineSmall)
 listOf("MVC" to MvcActivity::class.java, "MVP" to MvpActivity::class.java,
 "MVVM" to MvvmActivity::class.java, "MVI" to MviActivity::class.java).forEach { (name, target) ->
 Button(onClick = { onOpen(target) }) { Text(name) }
 }
 }
}
