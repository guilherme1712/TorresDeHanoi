package Final;
import java.util.*;
import java.util.function.Consumer;

class Main {

    public static void main(String[] args) {
        boolean primeiraJogada = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bem-vindo ao jogo Torre de Hanói com números!");
        System.out.print("Informe o número de discos (1 a 10): ");
        int numeroDeDiscos = scanner.nextInt();

        System.out.print("Deseja ordenar em ordem crescente (2) ou decrescente (1)? ");
        int ordem = scanner.nextInt();

        if (ordem != 1 && ordem != 2) {
            System.out.println("Opção inválida. O jogo será encerrado.");
            scanner.close();
            return;
        }

        PilhaEncadeada torre1 = new PilhaEncadeada();
        PilhaEncadeada torre2 = new PilhaEncadeada();
        PilhaEncadeada torre3 = new PilhaEncadeada();

        int[] numerosAleatorios = gerarNumerosAleatorios(numeroDeDiscos, ordem);
        for (int i = numeroDeDiscos - 1; i >= 0; i--) {
            torre1.push(numerosAleatorios[i]);
        }

        int movimentos = 0;
        boolean ordenacaoConcluida = false;

        while (!ordenacaoConcluida) {
            imprimirTorres(torre1, torre2, torre3);

            System.out.println("Menu de Opções:");
            System.out.println("0 - Sair do Jogo");
            System.out.println("1 - Mover disco");

            if (!primeiraJogada) {
                System.out.println("2 - Solução automática (disponível após a primeira jogada)");
            }

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            if (opcao == 0) {
                System.out.println("Obrigado por jogar!");
                break;
            } else if (opcao == 1) {
                System.out.print("Informe a torre de origem (1, 2 ou 3): ");
                int origem = scanner.nextInt();
                System.out.print("Informe a torre de destino (1, 2 ou 3): ");
                int destino = scanner.nextInt();

                PilhaEncadeada torreOrigem = null;
                PilhaEncadeada torreDestino = null;

                if (origem == 1) {
                    torreOrigem = torre1;
                } else if (origem == 2) {
                    torreOrigem = torre2;
                } else if (origem == 3) {
                    torreOrigem = torre3;
                }

                if (destino == 1) {
                    torreDestino = torre1;
                } else if (destino == 2) {
                    torreDestino = torre2;
                } else if (destino == 3) {
                    torreDestino = torre3;
                }

                if (torreOrigem == null || torreOrigem.isEmpty()) {
                    System.out.println("Movimento inválido. Tente novamente.");
                    continue;
                }

                if (torreDestino.isEmpty() || torreOrigem.peek() < torreDestino.peek()) {
                    moverDisco(torreOrigem, torreDestino);
                    movimentos++;
                } else {
                    System.out.println("Movimento inválido. Tente novamente.");
                }

                if (torre3.size() == numeroDeDiscos) {
                    ordenacaoConcluida = true;
                    System.out.println("Parabéns! Você completou o jogo em " + movimentos + " movimentos.");
                    break;
                }

                primeiraJogada = true;

            } else if (opcao == 2 && !primeiraJogada) {
                PilhaEncadeada origem = new PilhaEncadeada();
                PilhaEncadeada destino = new PilhaEncadeada();
                PilhaEncadeada auxiliar = new PilhaEncadeada();

                for (int i = numeroDeDiscos - 1; i >= 0; i--) {
                    origem.push(torre1.pop());
                }

                int numMovimentos = solucaoAutomatica(numeroDeDiscos, origem, destino, auxiliar, ordem);
                System.out.println("Solução automática concluída em " + numMovimentos + " movimentos.");
                ordenacaoConcluida = true;
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }

    // Função para imprimir o estado atual das torres
    private static void imprimirTorres(PilhaEncadeada torre1, PilhaEncadeada torre2, PilhaEncadeada torre3) {
        System.out.println("Estado atual das torres:");
        System.out.println("Torre 1: " + Arrays.toString(torre1.toArray()));
        System.out.println("Torre 2: " + Arrays.toString(torre2.toArray()));
        System.out.println("Torre 3: " + Arrays.toString(torre3.toArray()));
    }

    // Função para gerar números aleatórios para os discos
    private static int[] gerarNumerosAleatorios(int numeroDeDiscos, int ordem) {
        int[] numeros = new int[numeroDeDiscos];
        Random random = new Random();

        for (int i = 0; i < numeroDeDiscos; i++) {
            int numero = random.nextInt(100) + 1;
            numeros[i] = numero;
        }

        if (ordem == 2) {
            Arrays.sort(numeros); // Ordenar em ordem crescente e, em seguida, inverter
            int[] numerosDecrescentes = new int[numeroDeDiscos];
            for (int i = 0; i < numeroDeDiscos; i++) {
                numerosDecrescentes[i] = numeros[numeroDeDiscos - 1 - i];
            }
            return numerosDecrescentes;
        }

        shuffleArray(numeros); // Embaralhar os números aleatórios
        return numeros;
    }

    // Função para embaralhar um array
    private static void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    // Função para a solução automática da Torre de Hanoi
    private static int solucaoAutomatica(int n, PilhaEncadeada origem, PilhaEncadeada destino, PilhaEncadeada auxiliar, int ordem) {
        int movimentos = 0;

        // Algoritmo de resolução
        while (destino.size() < 255) {
            
            int topoOrigem = origem.peek();
            int topoDestino = destino.peek();

            if (topoOrigem == 1) {
                origem.pop();
                origem.push(0);
            }

            if (topoDestino == 0 || topoOrigem < topoDestino) {
                origem.pop();
                destino.push(topoOrigem);
                imprimirTorres(origem, auxiliar, destino);

                movimentos++;
            } else {
                while (topoDestino < topoOrigem) {
                    destino.pop();
                    auxiliar.push(topoDestino);
                   imprimirTorres(origem, auxiliar, destino);

                    movimentos++;

                    if (origem.isEmpty()) {
                        destino.push(auxiliar.pop());
                        imprimirTorres(origem, auxiliar, destino);

                        movimentos++;
                        break;
                    }

                    topoDestino = destino.peek();
                }

                if (topoDestino != 0 && topoOrigem < topoDestino) {
                    origem.pop();
                    destino.push(topoOrigem);
                     imprimirTorres(origem, auxiliar, destino);

                    movimentos++;
                }
            }
        }

        return movimentos;
    }

    private static void moverDisco(PilhaEncadeada origem, PilhaEncadeada destino) {
        if (origem != null && destino != null && !origem.isEmpty()) {
            int discoOrigem = origem.peek();

            if (destino.isEmpty() || discoOrigem < destino.peek()) {
                destino.push(origem.pop());
            } else {
                System.out.println("Movimento inválido. O disco não pode ser colocado sobre um menor.");
            }

            // Movimento do disco bem-sucedido
            System.out.println("Mover disco " + discoOrigem + " da origem para o destino.");
        } else {
            System.out.println("Movimento inválido. Tente novamente.");
        }
    }
}