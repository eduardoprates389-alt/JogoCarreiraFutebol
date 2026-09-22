package com.jogocarreira.futebol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jogocarreira.futebol.data.DataRepository
import com.jogocarreira.futebol.game.MatchEngine
import com.jogocarreira.futebol.model.Player

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { CarreiraApp() }
    }
}

@Composable
fun CarreiraApp() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val repo = remember { DataRepository(context) }
    var tela by remember { mutableStateOf("inicio") }
    var player by remember { mutableStateOf<Player?>(null) }
    var temporada by remember { mutableStateOf(2026) }
    var mensagem by remember { mutableStateOf("") }

    when (tela) {
        "inicio" -> TelaInicial { tela = "criar" }
        "criar" -> TelaCriar { nome, pais, posicao ->
            val clube = repo.clubs.firstOrNull { it.country.equals(pais, true) } ?: repo.clubs.first()
            player = Player(nome, pais, posicao, clube.name, 18, 1000, 60, 0, 0, 0)
            tela = "menu"
        }
        "menu" -> Menu(player!!, temporada, mensagem,
            { val r=MatchEngine.jogar(player!!); player=r.first; mensagem=r.second; tela="jogo" },
            { tela="ligas" }, { tela="perfil" },
            { tela="transferencias" }, { tela="atributos" },
            { temporada++; player=player!!.copy(age=player!!.age+1); mensagem="Temporada $temporada iniciada." })
        "jogo" -> TelaJogo(player!!, mensagem) { tela="menu" }
        "ligas" -> TelaLigas(repo) { tela="menu" }
        "perfil" -> TelaPerfil(player!!) { tela="menu" }
        "transferencias" -> TelaTransfer(repo, player!!) { club ->
    =club.name, marketValue=club.strength*100000L)
            mensagem="Transferência concluída para ${club.name}."
            tela="menu"
        }
        "atributos" -> TelaAtributos(player!!) {
            player=player!!.copy(overall=(player!!.overall+1).coerceAtMost(99))
            mensagem="Treino concluído! Overall: ${player!!.overall}"
            tela="menu"
        }
    }
}

@Composable fun TelaInicial(onStart:()->Unit)=Center("⚽ JOGO CARREIRA FUTEBOL","Base mundial • Temporada 2026/27",onStart)
@Composable fun Center(t:String,s:String,on:()->Unit){Box(Modifier.fillMaxSize().padding(24.dp),contentAlignment=Alignment.Center){Column(horizontalAlignment=Alignment.CenterHorizontally){Text(t,fontSize=27.sp);Spacer(Modifier.height(12.dp));Text(s);Spacer(Modifier.height(24.dp));Button(on){Text("COMEÇAR CARREIRA")}}}}
@Composable fun TelaCriar(on:(String,String,String)->Unit){var n by remember{mutableStateOf("")};var p by remember{mutableStateOf("Brasil")};var pos by remember{mutableStateOf("Atacante")};Column(Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState())){Text("CRIAR JOGADOR",fontSize=26.sp);OutlinedTextField(n,{n=it},label={Text("Nome")});OutlinedTextField(p,{p=it},label={Text("Nacionalidade")});OutlinedTextField(pos,{pos=it},label={Text("Posição")});Spacer(Modifier.height(16.dp));Button({if(n.isNotBlank())on(n,p,pos)}){Text("CRIAR JOGADOR")}}}
@Composable fun Menu(p:Player,s:Int,m:String,game:()->Unit,leagues:()->Unit,profile:()->Unit,trans:()->Unit,attrs:()->Unit,next:()->Unit){Column(Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState())){Text("CARREIRA",fontSize=28.sp);Text("${p.name} • ${p.club} • Temporada $s");if(m.isNotBlank())Text(m);Spacer(Modifier.height(12.dp));Button(game){Text("JOGAR PARTIDA")};Button(attrs){Text("TREINAR")};Button(trans){Text("MERCADO DE TRANSFERÊNCIAS")};Button(leagues){Text("LIGAS E CLUBES")};Button(profile){Text("PERFIL E ESTATÍSTICAS")};Button(next){Text("AVANÇAR TEMPORADA")}}}
@Composable fun TelaJogo(p:Player,m:String,back:()->Unit)=Center("⚽ ${p.club}","Partida simulada • $m",back)
@Composable fun TelaLigas(r:DataRepository,back:()->Unit){Column(Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState())){Text("LIGAS E COMPETIÇÕES",fontSize=25.sp);r.competitions.forEach{Text("🏆 ${it.name} — ${it.country}")};Spacer(Modifier.height(16.dp));Button(back){Text("VOLTAR")}}}
@Composable fun TelaPerfil(p:Player,back:()->Unit)=Center("👤 ${p.name}","${p.club}\n${p.position} • ${p.nationality}\nOverall ${p.overall} • Valor €${p.marketValue}",back)
@Composable fun TelaTransfer(r:DataRepository,p:Player,on:(com.jogocarreira.futebol.model.Club)->Unit){Column(Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState())){Text("MERCADO",fontSize=25.sp);r.clubs.take(80).filter{it.name!=p.club}.forEach{c->Button({on(c)},Modifier.fillMaxWidth()){Text("${c.name} • ${c.country} • OVR ${c.strength}")}}}}
@Composable fun TelaAtributos(p:Player,on:()->Unit)=Center("🏋️ TREINAMENTO","Overall atual: ${p.overall}",on)
