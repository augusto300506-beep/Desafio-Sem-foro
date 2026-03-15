import java.util.Random

fun main() {

    //Fila para salvar os carros que chegarem no sinal vermelho.
    val fila = ArrayDeque<Int>()
    var totalNaFila = 0

    // Variavel de controle do semáforo
    var semaforoAberto = false

    // Thread do semáforo

    val threadSemaforo = Thread {
        do {
            // Semáforo fechado por 10 segundos
            semaforoAberto = false
            println("SEMÁFORO FECHADO")
            Thread.sleep(10000)

            // Semáforo aberto por 5 segundos
            semaforoAberto = true
            println("SEMÁFORO ABERTO")

            // Carros que estavam aguardando na fila passam
            while (totalNaFila > 0) {
                val carroQueSaiu = fila.removeFirst()
                totalNaFila = totalNaFila - 1
                println("O carro $carroQueSaiu que estava esperando na fila passou!")
                Thread.sleep(500)
            }

            // Espera o resto do tempo aberto
            Thread.sleep(5000)

        } while (true)
    }

    // Thread dos carros

    val threadCarros = Thread {
        var numeroDoCarro = 1

        val gerador = Random()

        do {
            val tempoDeChegada = gerador.nextInt(2000) + 1000

            Thread.sleep(tempoDeChegada.toLong()) // Aguarda o tempo gerado

            println("O carro $numeroDoCarro chegou no semáforo.")

            // Para verificar se o sinal está aberto ou fechado
            if (semaforoAberto == true) {
                println("O carro $numeroDoCarro passou direto pois o semáforo está aberto.")
            } else {
                fila.addLast(numeroDoCarro)
                totalNaFila = totalNaFila + 1
                println("O semáforo está fechado. O carro $numeroDoCarro parou na fila.")
            }

            numeroDoCarro = numeroDoCarro + 1
        } while (true)
    }

    threadSemaforo.start()
    threadCarros.start()
}