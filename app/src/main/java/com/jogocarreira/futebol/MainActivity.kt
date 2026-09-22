 package com.jogocarreira.futebol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JogoCarreira()
        }
    }
}

@Composable
fun JogoCarreira() {

    var iniciouCarreira by remember { mutableStateOf(false) }

    if (!iniciouCarreira) {

        TelaInicial(
            onComecar = {
                iniciouCarreira = true
            }
        )

    } else {

        TelaCriarCarreira()
    }
}

@Composable
fun TelaInicial(
    onComecar: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "JOGO CARREIRA FUTEBOL",
            fontSize = 24.sp
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Button(
            onClick = onComecar
        ) {

            Text(
                text = "COMEÇAR CARREIRA"
            )
        }
    }
}

@Composable
fun TelaCriarCarreira() {

    var nomeJogador by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "CRIAR CARREIRA",
            fontSize = 26.sp
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        OutlinedTextField(
            value = nomeJogador,
            onValueChange = {
                nomeJogador = it
            },
            label = {
                Text("Nome do jogador")
            }
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        Button(
            onClick = {
                // Próxima etapa da carreira
            }
        ) {

            Text(
                text = "CRIAR JOGADOR"
            )
        }
    }
}
