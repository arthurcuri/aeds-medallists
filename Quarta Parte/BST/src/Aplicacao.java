import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Classe Medalhista: representa um medalhista olímpico e sua coleção de
 * medalhas
 * nas Olimpíadas de Paris 2024
 */
class Medalhista implements Comparable<Medalhista> {
    /** Para criar o vetor com no máximo 8 medalhas */
    private static final int MAX_MEDALHAS = 8;
    /** Nome do medalhista */
    private String name;
    /** Gênero do medalhista */
    private String gender;
    /** Data de nascimento do medalhista */
    private LocalDate birthDate;
    /** País do medalhista */
    private String country;
    /** Coleção de medalhas do medalhista */
    private Medalha[] medals;
    /** Contador de medalhas e índice para controlar o vetor de medalhas */
    private int medalCount;

    /**
     * Cria um medalhista olímpico. Nenhum dado precisa ser validado.
     * 
     * @param nome       Nome do medalhista no formato "SOBRENOME nome"
     * @param genero     Gênero do medalhista
     * @param nascimento Data de nascimento do medalhista
     * @param pais       País do medalhista (conforme dados originais, em inglês)
     */
    public Medalhista(String nome, String genero, LocalDate nascimento, String pais) {
        this.name = nome;
        this.gender = genero;
        this.birthDate = nascimento;
        this.country = pais;
        this.medals = new Medalha[MAX_MEDALHAS];
        this.medalCount = 0;
    }

    /**
     * Inclui uma medalha na coleção do medalhista. Retorna a quantidade atual de
     * medalhas do atleta.
     * 
     * @param medalha A medalha a ser armazenada.
     * @return A quantidade total de medalhas do atleta após a inclusão.
     */
    public int incluirMedalha(Medalha medalha) {
        if (medalCount < MAX_MEDALHAS) {
            medals[medalCount] = medalha;
            medalCount++;
        }
        return medalCount;
    }

    /**
     * Total de medalhas do atleta. É um número maior ou igual a 0.
     * 
     * @return Inteiro com o total de medalhas do atleta (>=0)
     */
    public int totalMedalhas() {
        return medalCount;
    }

    /**
     * Retorna um relatório das medalhas do atleta conforme o tipo solicitado pelo
     * parâmetro. Veja no enunciado da atividade o formato correto deste relatório.
     * Em caso de não possuir medalhas deste tipo, a resposta deve ser "Nao possui
     * medalha de TIPO".
     * 
     * @param tipo Tipo da medalha para o relatório
     * @return Uma string, multilinhas, com o relatório de medalhas daquele tipo.
     */
    public String relatorioDeMedalhas(TipoMedalha tipo) {
        StringBuilder relatorio = new StringBuilder();
        boolean possuiMedalha = false;

        for (int i = 0; i < medalCount; i++) {
            if (medals[i].getTipo() == tipo) {
                relatorio.append(medals[i].toString()).append("\n");
                possuiMedalha = true;
            }
        }

        if (!possuiMedalha) {
            return "Nao possui medalha de " + tipo;
        }

        return relatorio.toString().trim();
    }

    /**
     * Retorna o nome do país do medalhista (conforme arquivo original em inglês.)
     * 
     * @return String contendo o nome do país do medalhista (conforme arquivo
     *         original em inglês, iniciais em maiúsculas.)
     */
    public String getPais() {
        return country;
    }

    /**
     * Retorna uma cópia da data de nascimento do medalhista.
     * 
     * @return LocalDate com a data de nascimento do medalhista.
     */
    public LocalDate getNascimento() {
        return LocalDate.from(birthDate);
    }

    public String getGenero() {
        return gender;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Medalhista other = (Medalhista) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    /**
     * Deve retornar os dados pessoais do medalhista, sem as medalhas, conforme
     * especificado no enunciado da atividade.
     * 
     * @return String de uma linha, com os dados do medalhista, sem dados da
     *         medalha.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return name + ", " + gender + ". Nascimento: " + birthDate.format(formatter) + ". Pais: " + country;
    }

    @Override
    public int compareTo(Medalhista o) {
        return this.getName().compareTo(o.getName());
    }
}

/** Enumerador para medalhas de ouro, prata e bronze */
enum TipoMedalha {
    OURO,
    PRATA,
    BRONZE
}

/**
 * Representa uma medalha obtida nos Jogos Olímpicos de Paris em 2024.
 */
class Medalha {
    /** Tipo/cor da medalha conforme o enumerador */
    private TipoMedalha metalType;
    /** Data de obtenção da medalha */
    private LocalDate medalDate;
    /** Disciplina da medalha, conforme arquivo de dados */
    private String discipline;
    /** Evento da medalha, conforme arquivo de dados */
    private String event;

    /** Cria uma medalha com os dados do parâmetro. Nenhum dado é validado */
    public Medalha(TipoMedalha tipo, LocalDate data, String disciplina, String evento) {
        this.metalType = tipo;
        this.medalDate = data;
        this.discipline = disciplina;
        this.event = evento;
    }

    /**
     * Retorna o tipo de medalha, conforme o enumerador
     * 
     * @return TipoMedalha (enumerador) com o tipo/cor desta medalha
     */
    public TipoMedalha getTipo() {
        return metalType;
    }

    /**
     * Retorna uma string com o "relatório" da medalha de acordo com o especificado
     * no enunciado do problema.
     * Contém uma linha que já formata a data da medalha no formato brasileiro. O
     * restante deve ser implementado.
     */
    public String toString() {
        String dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(medalDate); // formata a data em
                                                                                            // DD/MM/AAAA
        return metalType + " medalha, " + /* discipline + */", " + event + ", " + dataFormatada;
    }
}

class Lista<E>  implements Iterable<E>  {

    private Celula<E> primeiro;
    private Celula<E> ultimo;
    private int quantidade;

    Lista() {
        Celula<E> sentinela = new Celula<E>();
        primeiro = ultimo = sentinela;
        quantidade = 0;
    }

    public boolean isEmpty() {
        return (primeiro == ultimo);
    }

    public void inserirInicio(E item) {
        Celula<E> novaCelula = new Celula<>(item);
        novaCelula.setProximo(this.primeiro.getProximo());
        this.primeiro.setProximo(novaCelula);
        if (isEmpty())
            this.ultimo = novaCelula;
        this.quantidade++;
    }

    public void inserirFinal(E item) {
        Celula<E> novaCelula;
        novaCelula = new Celula<>(item);
        ultimo.setProximo(novaCelula);
        ultimo = novaCelula;
        quantidade++;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListaIterator();
    }

    private class ListaIterator implements Iterator<E> {
        private Celula<E> current = primeiro;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E item = current.getItem();
            current = current.getProximo();
            return item;
        }
    }
}

class Celula<T> {

    private final T item;
    private Celula<T> proximo;

    public Celula() {
        this.item = null;
        setProximo(null);
    }

    public Celula(T item) {
        this.item = item;
        setProximo(null);
    }

    public Celula(T item, Celula<T> proximo) {
        this.item = item;
        this.proximo = proximo;
    }

    public T getItem() {
        return item;
    }

    public Celula<T> getProximo() {
        return proximo;
    }

    public void setProximo(Celula<T> proximo) {
        this.proximo = proximo;
    }
}

class Node<E extends Comparable<E>> {
    private Node<E> esquerda;
    private Node<E> direita;
    private E item;

    public Node() {
        this.setItem(null);
        this.setEsquerda(null);
        this.setDireita(null);
    }

    public Node(E item) {
        this.setItem(item);
        this.setEsquerda(null);
        this.setDireita(null);
    }

    public Node(E item, Node<E> esquerda, Node<E> direita) {
        this.setItem(null);
        this.setEsquerda(esquerda);
        this.setDireita(direita);
    }

    public Node<E> getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(Node<E> esquerda) {
        this.esquerda = esquerda;
    }

    public Node<E> getDireita() {
        return direita;
    }

    public void setDireita(Node<E> direita) {
        this.direita = direita;
    }

    public E getItem() {
        return item;
    }

    public void setItem(E item) {
        this.item = item;
    }
}

class BST<E extends Comparable<E>> {
    private Node<E> raiz;

    public BST() {
        this.raiz = null;
    }

    public boolean isEmpty() {
        return this.raiz == null;
    }

    public E localizar(E item) {
        return localizar(raiz, item);
    }

    private E localizar(Node<E> raizArvore, E item) {
        int comparacao;
        if (raizArvore == null) {
            throw new NoSuchElementException("Elemento não encontrado na árvore.");
        }
        comparacao = item.compareTo(raizArvore.getItem());
        if (comparacao == 0) {
            return raizArvore.getItem();
        } else if (comparacao < 0) {
            return localizar(raizArvore.getEsquerda(), item);
        } else {
            return localizar(raizArvore.getDireita(), item);
        }
    }

    public void add(E item) {
        this.raiz = add(raiz, item);
    }

    private Node<E> add(Node<E> raizArvore, E item) {
        int comparacao;
        if (raizArvore == null) {
            raizArvore = new Node<E>(item);
        } else {
            comparacao = item.compareTo(raizArvore.getItem());
            if (comparacao == 0) {
                throw new IllegalArgumentException("Elemento duplicado não permitido na árvore.");
            } else if (comparacao < 0) {
                raizArvore.setEsquerda(add(raizArvore.getEsquerda(), item));
            } else {
                raizArvore.setDireita(add(raizArvore.getDireita(), item));
            }
        }
        return raizArvore;
    }

    public void remove(E item) {
        this.raiz = remove(raiz, item);
    }

    private Node<E> remove(Node<E> raizArvore, E item) {
        int comparacao;
        if (raizArvore == null) {
            throw new IllegalStateException("Elemento não encontrado ou árvore vazia.");
        }
        comparacao = item.compareTo(raizArvore.getItem());
        if (comparacao == 0) {
            if (raizArvore.getEsquerda() == null) {
                raizArvore = raizArvore.getDireita();
            } else if (raizArvore.getDireita() == null) {
                raizArvore = raizArvore.getEsquerda();
            } else {
                raizArvore.setEsquerda(removeNoAntecessor(raizArvore, raizArvore.getEsquerda()));
            }
        } else if (comparacao < 0) {
            raizArvore.setEsquerda(remove(raizArvore.getEsquerda(), item));
        } else {
            raizArvore.setDireita(remove(raizArvore.getDireita(), item));
        }
        return raizArvore;
    }

    private Node<E> removeNoAntecessor(Node<E> removido, Node<E> raizArvore) {
        if (raizArvore.getDireita() != null) {
            raizArvore.setDireita(removeNoAntecessor(removido, raizArvore.getDireita()));
        } else {
            removido.setItem(raizArvore.getItem());
            raizArvore = raizArvore.getEsquerda();
        }
        return raizArvore;
    }

    public void caminhamentoEmOrdem() {
        if (isEmpty()) {
            throw new IllegalStateException("Árvore vazia.");
        } else {
            caminhamentoEmOrdem(this.raiz);
        }
    }

    private void caminhamentoEmOrdem(Node<E> raizArvore) {
        if (raizArvore != null) {
            caminhamentoEmOrdem(raizArvore.getEsquerda());
            System.out.println(raizArvore.getItem().toString());
            caminhamentoEmOrdem(raizArvore.getDireita());
        }
    }

    public Lista<E> recortar(E deOnde, E ateOnde) {
        Lista<E> resultado = new Lista<>();
        recortarRecursive(raiz, deOnde, ateOnde, resultado);
        return resultado;
    }
    
    private void recortarRecursive(Node<E> raizArvore, E deOnde, E ateOnde, Lista<E> resultado) {
        if (raizArvore == null) {
            return;
        }
        if (raizArvore.getItem().compareTo(deOnde) >= 0) {
            recortarRecursive(raizArvore.getEsquerda(), deOnde, ateOnde, resultado);
        }

        if (raizArvore.getItem().compareTo(deOnde) >= 0 && raizArvore.getItem().compareTo(ateOnde) <= 0) {
            resultado.inserirFinal(raizArvore.getItem());
        }
        if (raizArvore.getItem().compareTo(ateOnde) <= 0) {
            recortarRecursive(raizArvore.getDireita(), deOnde, ateOnde, resultado);
        }
    }    
}

public class AplicacaoRecortar {
     public static void main(String[] args) throws IOException {
        BST<Medalhista> arvore = new BST<>();
        String csvPath = "C:\\Users\\arthu\\OneDrive\\Desktop\\All\\my\\software_engineering\\Atividades\\3° Periodo - 2024\\AEDS II - Medallists\\Quarta Parte\\BST\\src\\tmp\\medallists.csv";

        try (BufferedReader br = Files.newBufferedReader(Paths.get(csvPath))) {
            String linha;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                String nome = campos[0];
                String genero = campos[1];
                LocalDate nascimento = LocalDate.parse(campos[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String pais = campos[3];
                Medalhista medalhista = new Medalhista(nome, genero, nascimento, pais);
                arvore.add(medalhista);
            }
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String linha = scanner.nextLine();
            if (linha.equals("FIM")) {
                break;
            }

            String[] partes = linha.split(" - ");
            String deOndeNome = partes[0];
            String ateOndeNome = partes[1];
            TipoMedalha tipoMedalha = TipoMedalha.valueOf(partes[2]);

            System.out.println(deOndeNome + " - " + ateOndeNome + " - " + tipoMedalha);

            Medalhista deOnde = new Medalhista(deOndeNome, "", null, "");
            Medalhista ateOnde = new Medalhista(ateOndeNome, "", null, "");

            Lista<Medalhista> medalhistas = arvore.recortar(deOnde, ateOnde);
            for (Medalhista medalhista : medalhistas) {
                String relatorio = medalhista.relatorioDeMedalhas(tipoMedalha);
                if (!relatorio.startsWith("Nao possui")) {
                    System.out.println(medalhista);
                    System.out.println("Quantidade de medalhas de " + tipoMedalha + ": " + medalhista.totalMedalhas());
                    System.out.println(relatorio);
                }
            }
        }
        scanner.close();
    }
}
