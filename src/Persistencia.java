
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Persistencia {

    static Scanner input;

    static RandomAccessFile arquivo;

    public static void main(String[] args) throws IOException {
        String nomeDoArquivo = "arquivo";
        File ar = new File(nomeDoArquivo);
        ar.delete();
        try {
            arquivo = new RandomAccessFile(ar, "rw");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        System.out.println("Tamanho do Arquivo Atualmente RandowAcessFile: " + arquivo.length());
        input = new Scanner(System.in);
        int aux = 0;
        do {

            System.out.println("############### Cadastro de Carro ###############");
            System.out.println("1 - Cadastrar Carro;");
            System.out.println("2 - Buscar Carro");
            System.out.println("3 - Apagar Carro");
            System.out.println("4 - Modificar Registro");
            System.out.println("5 - Imprimir Todos Os Registros");
            System.out.println("6 - Sair");
            System.out.println("Digite o Numero Equivalente as Operações a Cima: ");
            aux = input.nextInt();

            switch (aux) {
                case 1:
                    System.out.println("#################################################");
                    System.out.println("############### Cadastro de Carro ###############");
                    System.out.println("#################################################");

                    System.out.println("Informe o Chassi");
                    String chassi = "";
                    while (chassi.length() == 0) {
                        chassi = input.nextLine();
                    }

                    System.out.println("Informe o Nome");
                    String nome = "";
                    while (nome.length() == 0) {
                        nome = input.nextLine();
                    }

                    System.out.println("Informe o Valor");
                    double valor = -1;
                    while (valor == -1) {
                        valor = input.nextDouble();
                    }

                    System.out.println("Informe o Quantidade");
                    int quantidade = -1;
                    while (quantidade == -1) {
                        quantidade = input.nextInt();
                    }

                    System.out.println("Informe o Cor");
                    String cor = "";
                    while (cor.length() == 0) {
                        cor = input.nextLine();
                    }

                    if (cadastrarCarro(chassi.toUpperCase(), nome, valor, quantidade, cor)) {
                        System.out.println("######################################################");
                        System.out.println("############### Cadastrado Com Sucesso ###############");
                        System.out.println("######################################################");
                    } else {
                        System.err.println("#################################################");
                        System.err.println("############### Erro Ao Cadastrar ###############");
                        System.err.println("#################################################");
                    }
                    break;

                case 2:
                    System.out.println("#################################################");
                    System.out.println("################ Realizar Busca #################");
                    System.out.println("#################################################");

                    if (arquivo.length() == 0) {
                        System.err.println("Arquivo Vaziu");
                        break;
                    }
                    System.out.println("Por?");
                    System.out.println("1 - Pelo Chassi");
                    System.out.println("2 - Pelo Nome");

                    int n = input.nextInt();

                    if (n == 1) {
                        chassi = "";
                        System.out.println("Informe o Chassi");
                        do {
                            chassi = input.nextLine();
                        } while (chassi.length() == 0);
                        System.err.println("Resultado da Busca: " + buscarCarroC(chassi.toUpperCase(), 0l));

                    } else if (n == 2) {
                        nome = "";
                        System.out.println("Informe o Nome");
                        do {
                            nome = input.nextLine();
                        } while (nome.length() == 0);

                        System.err.println("Resultado da Busca: " + buscarCarroN(nome));
                    } else {
                        System.out.println("Opçao Invalida");
                    }
                    break;

                case 3:
                    System.out.println("#################################################");
                    System.out.println("################# Apagar Carro ##################");
                    System.out.println("#################################################");
                    if (arquivo.length() == 0) {
                        System.err.println("Arquivo Vaziu");
                        break;
                    }

                    System.out.println("Infome o Chassi");
                    do {
                        chassi = input.nextLine();
                    } while (chassi.length() == 0);

                    long res = buscarCarroC(chassi.toUpperCase(), 0l);//busca pelo registro

                    if (res != -1) {//se foi achado faz a modificação
                        arquivo.seek(res + 60);
                        arquivo.writeBoolean(false);
                    } else {
                        throw new IllegalArgumentException("Carro Não Registrado");
                    }

                    break;

                case 4:
                    System.out.println("##################################################");
                    System.out.println("############### Modificar Registro ###############");
                    System.out.println("##################################################");
                    if (arquivo.length() == 0) {
                        System.err.println("Arquivo Vaziu");
                        break;
                    }

                    System.out.println("Informa o Chassi do Veiculo");
                    chassi = "";
                    while (chassi.length() == 0) {
                        chassi = input.nextLine();
                    }
                    res = buscarCarroC(chassi, 0l); //busca pelo registro

                    if (res != -1) {//se foi achado faz o cadastro
                        System.out.println("Informe novo Nome");
                        nome = "";
                        while (nome.length() == 0) {
                            nome = input.nextLine();
                        }

                        System.out.println("Informe novo Valor");
                        valor = -1;
                        while (valor == -1) {
                            valor = input.nextDouble();
                        }

                        System.out.println("Informe novo Quantidade");
                        quantidade = -1;
                        while (quantidade == -1) {
                            quantidade = input.nextInt();
                        }

                        System.out.println("Informe novo Cor");
                        cor = "";
                        while (cor.length() == 0) {
                            cor = input.nextLine();
                        }

                        if (setCarro(chassi.toUpperCase(), nome, valor, quantidade, cor, res)) {
                            System.out.println("######################################################");
                            System.out.println("########### Registro Alterado Com Sucesso ############");
                            System.out.println("######################################################");
                        } else {
                            System.err.println("######################################################");
                            System.err.println("############## Erro Ao Alterar Registro ##############");
                            System.err.println("######################################################");
                        }
                        break;

                    } else {
                        System.err.println("Carro Não Registrado");
                        return;
                    }

                case 5:
                    System.out.println("##################################################");
                    System.out.println("############### Imprimir Registros ###############");
                    System.out.println("##################################################");
                    if (arquivo.length() == 0) {
                        System.err.println("Arquivo Vaziu");
                        break;
                    }
                    System.out.printf("   Ponteiro    |         Chassi         |                    Nome                    |  Quantidade  |     Valor     |        Cor        |  Eliminado?  |        LeftSon       |        RinghtSon       |");
                    imprimir(0l, 0, "<Registro Raiz>");
                    break;

                case 6:
                    break;

                default:
                    System.err.println("Operação Não Informada Corretamente");
                    break;
            }

        } while (aux
                != 6);
        input.close();

        try {
            arquivo.close();
        } catch (IOException ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static boolean cadastrarCarro(String chassi, String nome, double valor, int quantidade, String cor) throws IOException {
        for (int i = 0; i < arquivo.length(); i += 249) {
            if (getChassi(i).equals(chassi)) {
                if (isLogicallyON(i)) {
                    return false;
                }
            }
        }
        /**
         * n == -1 sem pai n >= 0 && múltiplo de 249 ->> pai localizado
         */
        long n = -1l;

        if (arquivo.length() != 0) {//se não ha nada então não ha porque haver busca pelo pai
            n = setPai(0l, chassi);

            if (n == -1) {
                return false;
            }

            if (!isLogicallyON(n) || !isLogicallyON(n) && getChassi(n).compareToIgnoreCase(chassi) == 0) {// Achou um registro apagado!  
                arquivo.seek(n);
                arquivo.writeChars(String.format("%1$30s", chassi));
                arquivo.writeBoolean(true);
                arquivo.writeChars(String.format("%1$45s", nome));
                arquivo.writeDouble(valor);
                arquivo.writeInt(quantidade);
                arquivo.writeChars(String.format("%1$35s", cor));
            } else {//Achou o pai
                int compareTO = chassi.compareToIgnoreCase(getChassi(n));
                if (compareTO > 0) {//Definindo de qual este vai ser filho
                    setLeftSon(n, arquivo.length());
                } else if (compareTO < 0) {
                    setRightSon(n, arquivo.length());
                }
                arquivo.seek(arquivo.length());
                arquivo.writeChars(String.format("%1$30s", chassi));
                arquivo.writeBoolean(true);
                arquivo.writeChars(String.format("%1$45s", nome));
                arquivo.writeDouble(valor);
                arquivo.writeInt(quantidade);
                arquivo.writeChars(String.format("%1$35s", cor));
                arquivo.writeLong(-1);//left
                arquivo.writeLong(-1);//right
            }

        } else { // O Arquivo Ta Vaziu
            arquivo.seek(arquivo.length());
            arquivo.writeChars(String.format("%1$30s", chassi));
            arquivo.writeBoolean(true);
            arquivo.writeChars(String.format("%1$45s", nome));
            arquivo.writeDouble(valor);
            arquivo.writeInt(quantidade);
            arquivo.writeChars(String.format("%1$35s", cor));
            arquivo.writeLong(-1);//left
            arquivo.writeLong(-1);//right
        }

        return true;
    }

    /**
     * Este metodo tem como objetivo buscar o pai do elemento ou possição onde
     * este ira ficar
     *
     * @param ponteiro // onde a busca deve acontecer
     * @param chassi // o chassi buscado
     * @return -1 erro, pode ser um ponteiro de um arquivo apagado, ou o pai do
     * atual arquivo a registrar!
     * @throws IOException
     */
    public static long setPai(long ponteiro, String chassi) throws IOException {

        String left = "";
        if (getLeftSon(ponteiro) != -1) {
            left = getChassi(getLeftSon(ponteiro));
        }
        String right = "";

        if (getRightSon(ponteiro) != -1) {
            right = getChassi(getRightSon(ponteiro));
        }

        if (!isLogicallyON(ponteiro)) {
            if (getChassi(ponteiro).compareToIgnoreCase(chassi) == 0 //Igual e Apagado
                    || left.compareToIgnoreCase(chassi) > 0 && right.compareToIgnoreCase(chassi) < 0 || left.equals("") && right.equals("")) { //Filhos aceitos e apagado
                return ponteiro; // Retorna o ponteiro de um arquivo apagado!
            }

        } else { //Não esta apagado
            if (getChassi(ponteiro).compareToIgnoreCase(chassi) == 0) {// Ja registrado Chassi iguais!
                System.err.println("Arquivo Ja Registrado");
                return -1;
            } else { // Não é semelhante
                int compareTo = chassi.compareToIgnoreCase(getChassi(ponteiro)); // CompareTo
                if (compareTo > 0) { //Left
                    if (getLeftSon(ponteiro) != -1) { //Se tem filho esquerdo!
                        ponteiro = setPai(getLeftSon(ponteiro), chassi); //Se tem chama recusivamente o metodo
                    } else {// Se não este entao é o pai
                        return ponteiro;// Se este entao é o pai
                    }
                } else if (compareTo < 0) {//Right
                    if (getRightSon(ponteiro) != -1) {//se tem filho direito
                        ponteiro = setPai(getRightSon(ponteiro), chassi);//Se tem chama recusivamente o metodo
                    } else {
                        return ponteiro;// Se este entao é o pai
                    }
                }

            }
        }
        return ponteiro;
    }

    private static long buscarCarroC(String chassi, long ponteiro) throws IOException {
        long ponteiroMaster = -1;
        if (ponteiro < arquivo.length() && ponteiro != -1) {//validação do ponteiro
            arquivo.seek(ponteiro);//set o ponteiro

            if (getChassi(ponteiro).toUpperCase().equals(chassi.toUpperCase())) {//comparação
                if (isLogicallyON(ponteiro)) {//Verifica se o registro não esta logicamente apagado
                    return (ponteiro); // retorna o ponteiro no inicio do registro
                } else {
                    System.err.println("Elemento Apagando");
                    return -1;
                }
            }

            int in = chassi.compareToIgnoreCase(getChassi(ponteiro));

            if (in > 0) {
                ponteiroMaster = buscarCarroC(chassi, getLeftSon(ponteiro));
            } else if (in < 0) {
                ponteiroMaster = buscarCarroC(chassi, getRightSon(ponteiro));
            }
        }
        return ponteiroMaster;
    }

    private static long buscarCarroN(String nome) throws IOException {
        long ponteiro = 0l;
        arquivo.seek(ponteiro);

        while (arquivo.getFilePointer() < arquivo.length()) {
            if (isLogicallyON(ponteiro)) {
                if (getNome(ponteiro).toUpperCase().equals(nome.toUpperCase()));
                return ponteiro;
            }
            ponteiro += 249;
            if (ponteiro < arquivo.length()) {
                arquivo.seek(ponteiro);
            } else {
                break;
            }
        }
        return -1;
    }

    private static boolean setCarro(String chassi, String nome, double valor, int quantidade, String cor, long ponteiro) throws IOException {
        arquivo.seek(ponteiro);
        arquivo.writeChars(String.format("%1$30s", chassi));
        arquivo.writeBoolean(true);
        arquivo.writeChars(String.format("%1$45s", nome));
        arquivo.writeDouble(valor);
        arquivo.writeInt(quantidade);
        arquivo.writeChars(String.format("%1$35s", cor));
        return true;
    }

    private static void imprimir(long ponteiro, int a, String filho) throws IOException {
        if (isLogicallyON(ponteiro)) {
            StringBuilder sb = new StringBuilder("{");
            sb.append(String.format("%14s", ponteiro));
            sb.append(",");
            sb.append(String.format("%24s", getChassi(ponteiro)));
            sb.append(",");
            sb.append(String.format("%44s", getNome(ponteiro)));
            sb.append(",");
            sb.append(String.format("%14s", getQuantidade(ponteiro)));
            sb.append(",");
            sb.append(String.format("%15s", getValor(ponteiro)));
            sb.append(",");
            sb.append(String.format("%19s", getCor(ponteiro)));
            sb.append(",");
            sb.append(String.format("%14s", isLogicallyON(ponteiro)));
            sb.append(",");
            if (getLeftSon(ponteiro) != -1) {
                sb.append(String.format("%22s", getChassi(getLeftSon(ponteiro))));
            } else {
                sb.append(String.format("%22s", getLeftSon(ponteiro)));
            }
            sb.append(",");
            if (getRightSon(ponteiro) != -1) {
                sb.append(String.format("%24s", getChassi(getRightSon(ponteiro))));
            } else {
                sb.append(String.format("%24s", getRightSon(ponteiro)));
            }
            sb.append('}');
            for (int i = 0; i < a + 2; i++) {
                System.out.print(" ");
                if (i % 2 == 0 && i != 0) {
                    System.out.print("|");
                }
            }
            System.out.println("");
            for (int i = 0; i < a + 2; i++) {
                System.out.print(" ");
                if (i % 2 == 0 && i != 0) {
                    System.out.print("|");
                }
            }
            System.out.println(filho);
            for (int i = 0; i < a; i++) {
                System.out.print(" ");
                if (i % 2 == 0 && i != 0) {
                    System.out.print("|");
                }
            }

            if (ponteiro != 0) {
                System.out.println(" |--->" + sb.toString());
            } else if (ponteiro == 0) {
                System.out.println("  ->" + sb.toString());
            }
        } else {
            for (int i = 0; i < a + 2; i++) {
                System.out.print(" ");
                if (i % 2 == 0 && i != 0) {
                    System.out.print("|");
                }
            }
            System.out.println("");
            for (int i = 0; i < a; i++) {
                System.out.print(" ");
                if (i % 2 == 0 && i != 0) {
                    System.out.print("|");
                }
            }
            System.out.println(" |->{~}");
        }

        if (ponteiro == 0);
        a++;
        a++;
        if (getLeftSon(ponteiro) != -1) {
            imprimir(getLeftSon(ponteiro), a, "<Registro Esquerdo>");
        }
        if (getRightSon(ponteiro) != -1) {
            if (getLeftSon(ponteiro) != -1);
            imprimir(getRightSon(ponteiro), a, "<Registro Direito>");
        }
    }

    protected static String getChassi(long ponteiro) throws IOException {
        String aux = "";
        arquivo.seek(ponteiro);
        for (int i = 0; i < 30; i++) {
            aux += arquivo.readChar();
        }
        return aux.trim();
    }

    protected static Boolean isLogicallyON(long ponteiro) throws IOException {
        arquivo.seek(ponteiro + 60);
        return arquivo.readBoolean();
    }

    protected static String getNome(long ponteiro) throws IOException {
        arquivo.seek(ponteiro + 61);
        String aux = "";
        for (int i = 0; i < 45; i++) {
            aux += arquivo.readChar();
        }
        return aux.trim();
    }

    protected static double getValor(long ponteiro) throws IOException {
        arquivo.seek(ponteiro + 151);
        return arquivo.readDouble();
    }

    protected static int getQuantidade(long ponteiro) throws IOException {
        arquivo.seek(ponteiro + 159);
        return arquivo.readInt();
    }

    protected static String getCor(long ponteiro) throws IOException {
        arquivo.seek(ponteiro + 163);
        String aux = "";
        for (int i = 0; i < 35; i++) {
            aux += arquivo.readChar();
        }
        return aux.trim();
    }

    protected static long getLeftSon(long ponteiro) throws IOException {
        arquivo.seek(ponteiro + 233);
        return arquivo.readLong();
    }

    protected static boolean setLeftSon(long ponteiro, long leftSon) {
        try {
            arquivo.seek(ponteiro + 233);
            arquivo.writeLong(leftSon);
        } catch (IOException ex) {
            System.err.println("Falha Ao Alterar o Filho Esquerdo Do Elemento: " + ponteiro);
            try {
                System.err.println("Cujo o Chassi é: " + getChassi(ponteiro));
            } catch (IOException ex1) {
            }
            return false;
        }
        return true;
    }

    protected static long getRightSon(long ponteiro) throws IOException {
        arquivo.seek(ponteiro + 241);
        return arquivo.readLong();
    }

    protected static boolean setRightSon(long ponteiro, long rigthSon) {
        try {
            arquivo.seek(ponteiro + 241);
            arquivo.writeLong(rigthSon);
        } catch (IOException ex) {
            System.err.println("Falha Ao Alterar o Filho Direito Do Elemento: " + ponteiro);
            try {
                System.err.println("Cujo o Chassi é: " + getChassi(ponteiro));
            } catch (IOException ex1) {
            }
            return false;
        }
        return true;
    }

}
