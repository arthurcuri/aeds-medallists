import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
* Classe Medalhista: representa um medalhista olímpico e sua coleção de medalhas
nas Olimpíadas de Paris 2024
*/
class Medalhista {
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
* @param nome Nome do medalhista no formato "SOBRENOME nome"
* @param genero Gênero do medalhista
* @param nascimento Data de nascimento do medalhista
* @param pais País do medalhista (conforme dados originais, em inglês)
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
* @return Inteiro com o total de medalhas do atleta (>=0)
*/
public int totalMedalhas() {
    return medalCount;
}

public int totalDeMedalhas(TipoMedalha tipo) {
    int total = 0;
    for (int i = 0; i < medalCount; i++) {
        if (medals[i].getTipo() == tipo) {
            total++;
        }
    }
    return total;
}


/**
* Retorna um relatório das medalhas do atleta conforme o tipo solicitado pelo
* parâmetro. Veja no enunciado da atividade o formato correto deste relatório.
* Em caso de não possuir medalhas deste tipo, a resposta deve ser "Nao possui medalha de TIPO".
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
* @return String contendo o nome do país do medalhista (conforme arquivo
* original em inglês, iniciais em maiúsculas.)
*/
public String getPais() {
    return country;
}

/**
* Retorna uma cópia da data de nascimento do medalhista.
* @return LocalDate com a data de nascimento do medalhista.
*/
public LocalDate getNascimento() {
    return LocalDate.from(birthDate);
}


public int compareTo(Medalhista outro) {
    return this.birthDate.compareTo(outro.birthDate);
}



/**
* Deve retornar os dados pessoais do medalhista, sem as medalhas, conforme
* especificado no enunciado da atividade.
* @return String de uma linha, com os dados do medalhista, sem dados da
* medalha.
*/
public String toString() {
    return name + " (" + gender + "), " + birthDate + ", " + country;
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
    String dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(medalDate); // formata a data em DD/MM/AAAA
    return metalType + " medalha, " + discipline + ", " + event + ", " + dataFormatada;
}
}

class Pais {
    private String nome;
    private Medalhista[] medalhistas;

    public Pais(String nome) {
        this.nome = nome;
        this.medalhistas = new Medalhista[100]; 
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void incluirMedalha(Medalhista medalhista) {
        for (int i = 0; i < medalhistas.length; i++) {
            if (medalhistas[i] == null) {
                medalhistas[i] = medalhista;
                return;
            }
        }
        System.out.println("Limite de medalhistas atingido para o país: " + nome);
    }

    public int totalDeMedalhas() {
        int totalMedalhas = 0;
        for (Medalhista medalhista : medalhistas) {
            if (medalhista != null) {
                totalMedalhas += medalhista.totalMedalhas();
            }
        }
        return totalMedalhas;
    }

    public int totalDeMedalhas(TipoMedalha tipo) {
        int total = 0;
        for (Medalhista medalhista : medalhistas) {
            if (medalhista != null) {
                total += Arrays.stream(medalhista.relatorioDeMedalhas(tipo).split("\n")).count();
            }
        }
        return total;
    }

    public int compareTo(Pais outro) {
        return Integer.compare(outro.totalDeMedalhas(), this.totalDeMedalhas());
    }

    public String relatorioMedalhistas() {
        StringBuilder relatorio = new StringBuilder("Medalhistas do país: " + nome + "\n");
        for (Medalhista medalhista : medalhistas) {
            if (medalhista != null) {
                relatorio.append(medalhista.toString()).append("\n");
            }
        }
        return relatorio.toString().trim();
    }

    @Override
    public String toString() {
        return "Pais [nome=" + nome + ", total de medalhistas=" + (int) Arrays.stream(medalhistas).filter(m -> m != null).count() + "]";
    }
}


class Evento {
    private String event;
    private String discipline;
    private int quantMedalhistas;
    private Medalhista[] medallists;

    private static final int MAX_MEDALHISTAS = 100;

    public Evento(String event, String discipline) {
        this.event = event;
        this.discipline = discipline;
        this.medallists = new Medalhista[MAX_MEDALHISTAS];
        this.quantMedalhistas = 0;
    }

    public void incluirMedalhista(Medalhista medalhista) {
        if (quantMedalhistas < MAX_MEDALHISTAS) {
            medallists[quantMedalhistas] = medalhista;
            quantMedalhistas++;
        } else {
            System.out.println("Limite de medalhistas atingido para o evento: " + event);
        }
    }

    public int compareTo(Evento outroEvento) {
        return Integer.compare(outroEvento.quantMedalhistas, this.quantMedalhistas);
    }

    public String relatorioMedalhista() {
        StringBuilder relatorio = new StringBuilder("Relatório de Medalhistas no evento: " + event + "\n");
        for (int i = 0; i < quantMedalhistas; i++) {
            if (medallists[i] != null) {
                relatorio.append(medallists[i].toString()).append("\n");
            }
        }
        return relatorio.toString().trim();
    }

    @Override
    public String toString() {
        return "Evento [event=" + event + ", discipline=" + discipline + ", quantMedalhistas=" + quantMedalhistas
                + ", medallists=" + Arrays.toString(medallists) + "]";
    }
}


public class Aplicacao {
    public static void main(String[] args) {
        Map<String, Medalhista> medalhistas;
        medalhistas = carregarMedalhistas("C:\\Users\\arthu\\OneDrive\\Desktop\\AEDS II - Medallists\\src\\tmp\\medallists.csv");
        Scanner leitura = new Scanner(System.in);
        String entrada = "";
        while (true) {
            entrada = leitura.nextLine();
            if (entrada.equalsIgnoreCase("FIM")) {
                break;
            }
            String[] dadosEntrada = entrada.split(",");
            String nomeMedalhista = dadosEntrada[0].trim();
            String tipoMedalha = dadosEntrada[1].trim();
            Medalhista medalhista = medalhistas.get(nomeMedalhista);
            if (medalhista != null) {
                System.out.println(medalhista.relatorioDeMedalhas(TipoMedalha.valueOf(tipoMedalha)));
            } else {
                System.out.println("Medalhista não encontrado.");
            }
        }
        leitura.close();
    }

    private static Map<String, Medalhista> carregarMedalhistas(String csvFile) {
        Map<String, Medalhista> medalhistas = new HashMap<>();
        String line;
        String csvSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] dados = line.split(csvSplitBy);
                String nome = dados[0];
                String genero = dados[3];
                LocalDate nascimento = LocalDate.parse(dados[4]);
                String pais = dados[5];
                TipoMedalha tipoMedalha = TipoMedalha.valueOf(dados[1]);
                LocalDate dataMedalha = LocalDate.parse(dados[2]);
                String disciplina = dados[6];
                String evento = dados[7];
                Medalhista medalhista = medalhistas.getOrDefault(nome, new Medalhista(nome, genero, nascimento, pais));
                Medalha medalha = new Medalha(tipoMedalha, dataMedalha, disciplina, evento);
                medalhista.incluirMedalha(medalha);
                medalhistas.put(nome, medalhista);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medalhistas;
    }
}