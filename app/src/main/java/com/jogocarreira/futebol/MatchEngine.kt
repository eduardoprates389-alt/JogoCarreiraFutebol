package com.jogocarreira.futebol.game
import com.jogocarreira.futebol.model.Player
import kotlin.random.Random
object MatchEngine {
 fun jogar(p:Player):Pair<Player,String>{
  val g=Random.nextInt(0,3); val a=Random.nextInt(0,3)
  val scored=if(g>=a) 1 else 0
  val np=p.copy(goals=p.goals+scored,overall=(p.overall+(if(scored==1)1 else 0)).coerceAtMost(99))
  return np to "Resultado: ${g} x $a • ${if(g>a)"Vitória!" else if(g==a)"Empate." else "Derrota."}"
 }
}
