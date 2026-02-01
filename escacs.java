import java.util.ArrayList;
import java.util.Scanner;

public class escacs {

    public static void main(String[] args) {
        escacs p = new escacs();
        p.principal();
    }

    Scanner sc = new Scanner(System.in);
    boolean fiPartida = false;
    char torn;

    public void principal() {
        System.out.println("Benvingut al joc d'escacs!");

        char[][] tauler = new char[8][8];

        ArrayList<String> moviments = new ArrayList<String>();

        ArrayList<Character> eliminadesB = new ArrayList<Character>();
        ArrayList<Character> eliminadesN = new ArrayList<Character>();

        demanarJugadors();
        prepararPartida(tauler, moviments);
        jugar(tauler, moviments);
    }

    public void demanarJugadors() {

        String jugadorBlanques;
        String jugadorNegres;

        System.out.println("Introdueix el nom del jugador 1 (blanques): ");
        jugadorBlanques = sc.nextLine();

        System.out.println("Introdueix el nom del jugador 2 (negres): ");
        jugadorNegres = sc.nextLine();

        System.out.println("Els jugadors són:");
        System.out.println("Jugador 1 (blanques): " + jugadorBlanques);
        System.out.println("Jugador 2 (negres): " + jugadorNegres);

    }

    public void prepararPartida(char[][] tauler, ArrayList<String> moviments) {


        inicialitzarTauler(tauler);

        netejarMoviments(tauler, moviments);

        torn = 'B';

    }

    public void inicialitzarTauler(char[][] tauler) {

    for (int f = 0; f < 8; f++) {
        for (int c = 0; c < 8; c++) {
            tauler[f][c] = ' ';
        }
    }

    posarBlanques(tauler);
    posarNegres(tauler);
}

    public void posarBlanques(char[][] tauler) {


        for (int i = 0; i < 8; i++) {
            tauler[1][i] = 'P';
        }

        tauler[0][0] = 'T';
        tauler[0][7] = 'T';
        tauler[0][1] = 'C';
        tauler[0][6] = 'C';
        tauler[0][2] = 'A';
        tauler[0][5] = 'A';
        tauler[0][3] = 'Q';
        tauler[0][4] = 'K';
    }
    
    public void posarNegres(char[][] tauler) {
        
        for (int i = 0; i < 8; i++) {
            tauler[6][i] = 'p';
        }

        tauler[7][0] = 't';
        tauler[7][7] = 't';
        tauler[7][1] = 'c';
        tauler[7][6] = 'c';
        tauler[7][2] = 'a';
        tauler[7][5] = 'a';
        tauler[7][3] = 'q';
        tauler[7][4] = 'k';

    }

    public void netejarMoviments(char[][] tauler, ArrayList<String> moviments) {
        moviments.clear();
    }

    public void jugar(char[][] tauler, ArrayList<String> moviments) {
        
        while (!fiPartida) {
            mostrarTauler(tauler);
            mostrarTorn();
            String mov = llegirMoviment();

            if (mov.equalsIgnoreCase("Abandonar")) {
                fiPartida = true;
            }
            else if (validarMoviment(mov, tauler)) {

                String[] parts = separarMoviment(mov);
                int[] origen = convertirCoordenada(parts[0]);
                int[] desti = convertirCoordenada(parts[1]);

                aplicarMoviment(origen, desti, tauler);
                moviments.add(mov);
                canviarTorn();
            }
        }
    }
    
    public void mostrarTauler(char[][] tauler) {

        System.out.println("    a b c d e f g h");

        for (int f = 0; f < 8; f++) {
            System.out.print((f + 1) + " | ");

            for (int c = 0; c < 8; c++) {
                char peca = tauler[f][c];
                if (peca == ' ') {
                    System.out.print(". ");
                } else {
                    System.out.print(peca + " ");
                }
            }
            System.out.println();
        }
    } 

    public void mostrarTorn() {
        
        if (torn == 'B' ) { 
            System.out.println("Torn de les peces blanques");
        }
        else {
            System.out.println("Torn de les peces negres");
        }
    }

    public void canviarTorn() {
        if (torn == 'B') {
            torn = 'N';
        } else {
            torn = 'B';
        }
    }
    public String llegirMoviment () {

        System.out.println("Introdueix moviment ex: (casella inici) e2  (casella final) e4 o 'Abandonar' si vols rendir-te :");
        return sc.nextLine();
    }

    public boolean validarMoviment(String mov, char[][] tauler) {

    if (!formatCorrecte(mov)) {
        System.out.println("Format incorrecte");
        return false;
    }

    String[] parts = separarMoviment(mov);

    int[] origen = convertirCoordenada(parts[0]);
    int[] desti = convertirCoordenada(parts[1]);

    if (!hiHaPeca(origen, tauler)) {
        System.out.println("No hi ha cap peça a l'origen");
        return false;
    }

    if (!pecaDelTorn(origen, tauler)) {
        System.out.println("Aquesta peça no és del teu torn");
        return false;
    }

    char peca = tauler[origen[0]][origen[1]];

    if (peca == 'P' || peca == 'p') {
        if (!movimentPeo(origen, desti, tauler)) {
            System.out.println("Moviment de peó no vàlid");
            return false;
        }
    }
    return true;
    }

    public void aplicarMoviment(int[] o, int[] d, char[][] tauler) {

        tauler[d[0]][d[1]] = tauler[o[0]][o[1]];
        tauler[o[0]][o[1]] = ' ';
    }

    public boolean formatCorrecte(String mov) {
        return mov.matches("[a-h][1-8] [a-h][1-8]");
    }

    public String[] separarMoviment(String mov) {
        return mov.split(" ");
    }

    public int[] convertirCoordenada(String coord) {
        int col = coord.charAt(0) - 'a';
        int fila = coord.charAt(1) - '1';
        return new int[]{fila, col};
    }

    public boolean hiHaPeca(int[] pos, char[][] tauler) {
        return tauler[pos[0]][pos[1]] != ' ';
    }

    public boolean pecaDelTorn(int[] pos, char[][] tauler) {
        char p = tauler[pos[0]][pos[1]];

        if (torn == 'B') {
            return Character.isUpperCase(p);
        } else {
            return Character.isLowerCase(p);
        }
    }

    public boolean movimentPeo(int[] o, int[] d, char[][] tauler) {

    char peca = tauler[o[0]][o[1]];

    int dir;
    int filaInicial;

    if (Character.isUpperCase(peca)) { // Blanques
        dir = 1;
        filaInicial = 1;
    } else { // Negres
        dir = -1;
        filaInicial = 6;
    }

    // Avança 1 casella
    if (d[1] == o[1] &&
        d[0] == o[0] + dir &&
        tauler[d[0]][d[1]] == ' ') {
        return true;
    }
    // Avança 2 casellas solament desde posicio inicial
    if (d[1] == o[1] &&
        o[0] == filaInicial &&
        d[0] == o[0] + 2 * dir &&
        tauler[o[0] + dir][o[1]] == ' ' &&
        tauler[d[0]][d[1]] == ' ') {
        return true;
    }

    // Diagonal solament si mata
    if (Math.abs(d[1] - o[1]) == 1 &&
        d[0] == o[0] + dir &&
        tauler[d[0]][d[1]] != ' ' &&
        Character.isUpperCase(tauler[d[0]][d[1]]) != Character.isUpperCase(peca)) {
        return true;
    }

    return false;
}
}