package Final;
import Final.Node;

public class PilhaEncadeada {

    private Node topo = null;

    public void push(int item) {
        Node novoNodo = new Node(item);
        if (topo == null) {
            topo = novoNodo;
        } else {
            novoNodo.proximo = topo;
            topo = novoNodo;
        }
    }

    public int pop() {
        if (isEmpty()) {
            return 0; // Ou qualquer outro valor padrão que você desejar
        }
        int item = topo.valor;
        topo = topo.proximo;

        if (topo == null) {
            topo = new Node(0); // Cria um novo Nodo com valor 0 se o topo for nulo
        }

        return item;
    }


    public int peek() {
        if (isEmpty()) {
            return 0; // Retorna 0 para representar um disco ausente
        }
        if (topo == null) {
            topo = new Node(0); // Cria um novo Nodo com valor 0 se o topo for nulo
        }
        return topo.valor;
    }


    public boolean isEmpty() {
        return topo == null;
    }

    public int size() {
        int tamanho = 0;
        Node atual = topo;
        while (atual != null) {
            tamanho++;
            atual = atual.proximo;
        }
        return tamanho;
    }

    public int[] toArray() {
        int[] array = new int[size()];
        Node atual = topo;
        int i = 0;
        while (atual != null) {
            array[i] = atual.valor;
            atual = atual.proximo;
            i++;
        }
        return array;
    }

    public int countOccurrences(int value) {
        int count = 0;
        Node current = topo;
        while (current != null) {
            if (current.valor == value) {
                count++;
            }
            current = current.proximo;
        }
        return count;
    }  
}