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

        demanarJugadors();

        boolean seguirJugant = true;

        while (seguirJugant) {
            char[][] tauler = new char[8][8];
            ArrayList<String> moviments = new ArrayList<String>();
            ArrayList<Character> eliminadesB = new ArrayList<Character>();
            ArrayList<Character> eliminadesN = new ArrayList<Character>();

            fiPartida = false;  // <-- reiniciamos aquí
            prepararPartida(tauler, moviments);
            jugar(tauler, moviments, eliminadesB, eliminadesN);

            mostrarResum(moviments);

            seguirJugant = novaPartida();
        }

        System.out.println("Gràcies per jugar! Fins aviat.");
    }

    public String demanarNom(String color) {
        System.out.println("Introdueix el nom del jugador " + color + ": ");
        return sc.nextLine();
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

    public void jugar(char[][] tauler, ArrayList<String> moviments, ArrayList<Character> eliminadesB, ArrayList<Character> eliminadesN) {
        
        while (!fiPartida) {
            mostrarTauler(tauler);
            mostrarCapturades(eliminadesB, eliminadesN);
            mostrarTorn();
            String mov = llegirMoviment();

            if (mov.equalsIgnoreCase("Abandonar")) {
                fiPartida = true;
            }
            else if (validarMoviment(mov, tauler)) {

                String[] parts = separarMoviment(mov);
                int[] origen = convertirCoordenada(parts[0]);
                int[] desti = convertirCoordenada(parts[1]);

                aplicarMoviment(origen, desti, tauler, eliminadesB, eliminadesN);
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

        // Peó
        if (peca == 'P' || peca == 'p') {
            if (!movimentPeo(origen, desti, tauler)) {
                System.out.println("Moviment de peó no vàlid");
                return false;
            }
        }

        // Torre
        if (peca == 'T' || peca == 't') {
            if (!movimentTorre(origen, desti, tauler)) {
                System.out.println("Moviment de torre no vàlid");
                return false;
            }
        }

        // Alfil
        if (peca == 'A' || peca == 'a') {
            if (!movimentAlfil(origen, desti, tauler)) {
                System.out.println("Moviment d'alfil no vàlid");
                return false;
            }
        }

        // Cavall
        if (peca == 'C' || peca == 'c') {
            if (!movimentCavall(origen, desti, tauler)) {
                System.out.println("Moviment de cavall no vàlid");
                return false;
            }
        }

        // Reina
        if (peca == 'Q' || peca == 'q') {
            if (!movimentReina(origen, desti, tauler)) {
                System.out.println("Moviment de reina no vàlid");
                return false;
            }
        }

        // Rei
        if (peca == 'K' || peca == 'k') {
            if (!movimentRei(origen, desti, tauler)) {
                System.out.println("Moviment de rei no vàlid");
                return false;
            }
        }

        return true;
    }

    public void aplicarMoviment(int[] o, int[] d, char[][] tauler, ArrayList<Character> eliminadesB, ArrayList<Character> eliminadesN) {

        char destiPeca = tauler[d[0]][d[1]];
        if (destiPeca != ' ') {
            if (Character.isUpperCase(destiPeca)) {
                eliminadesB.add(destiPeca);
            } else {
                eliminadesN.add(destiPeca);
            }
        }

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

    public boolean movimentTorre(int[] o, int[] d, char[][] tauler) {

        // Es mou a la fila/columna 
        if (o[0] != d[0] && o[1] != d[1]) {
            return false;
        }

        int pasFila = Integer.signum(d[0] - o[0]);
        int pasCol = Integer.signum(d[1] - o[1]);

        int filaActual = o[0] + pasFila;
        int colActual = o[1] + pasCol;

        // No hi han obstacles
        while (filaActual != d[0] || colActual != d[1]) {
            if (tauler[filaActual][colActual] != ' ') {
                return false;
            }

            filaActual += pasFila;
            colActual += pasCol;
        }

        // Mira si mata a una peça o no
        char origen = tauler[o[0]][o[1]];
        char desti  = tauler[d[0]][d[1]];

        if (desti == ' ') {
        return true;
        }

        // Validació color peça destí
        return Character.isUpperCase(origen) != Character.isUpperCase(desti);
        
    }

    public boolean movimentAlfil(int[] o, int[] d, char[][] tauler) {

        // Es mou en diagonal
        if (Math.abs(d[0] - o[0]) != Math.abs(d[1] - o[1])) {
            return false;
        }

        int pasFila = Integer.compare(d[0], o[0]); // -1 o 1
        int pasCol  = Integer.compare(d[1], o[1]); // -1 o 1

        int filaActual = o[0] + pasFila;
        int colActual = o[1] + pasCol;

        // No hi han obstacles
        while (filaActual != d[0] && colActual != d[1]) {
            if (tauler[filaActual][colActual] != ' ') {
                return false;
            }
            filaActual += pasFila;
            colActual += pasCol;
        }

        // Mira si mata a una peça o no
        char origen = tauler[o[0]][o[1]];
        char desti  = tauler[d[0]][d[1]];

        if (desti == ' ') {
            return true;
        }

        // Validació color peça destí
        return Character.isUpperCase(origen) != Character.isUpperCase(desti);
    }

    public boolean movimentCavall(int[] o, int[] d, char[][] tauler) {

        int df = Math.abs(d[0] - o[0]);
        int dc = Math.abs(d[1] - o[1]);

        // Movimient L
        if (!((df == 2 && dc == 1) || (df == 1 && dc == 2))) {
            return false;
        }

        char origen = tauler[o[0]][o[1]];
        char desti  = tauler[d[0]][d[1]];

        // Validació color peça destí
        if (desti != ' ' && Character.isUpperCase(origen) == Character.isUpperCase(desti)) {
            return false;
        }

        return true;
    }

    public boolean movimentReina(int[] o, int[] d, char[][] tauler) {
        // La reina utilitza els moviments de la torre i l'alfil
        return movimentTorre(o, d, tauler) || movimentAlfil(o, d, tauler);
    }

    public boolean movimentRei(int[] o, int[] d, char[][] tauler) {
    
        char peca = tauler[o[0]][o[1]];
        
        int df = Math.abs(d[0] - o[0]);
        int dc = Math.abs(d[1] - o[1]);

        // El rei es pot moure solament una casella en qualsevol direcció
        if (df <= 1 && dc <= 1) {
            
            char destiPeca = tauler[d[0]][d[1]];
            if (destiPeca == ' ') return true;
            
            if (Character.isUpperCase(peca) != Character.isUpperCase(destiPeca)) return true; // captura
        }
        return false;
    }

    public boolean novaPartida() {
        System.out.println("Vols començar una nova partida? (Sí/No): ");
        String resposta = sc.nextLine().trim().toLowerCase();

        if (resposta.equals("si") || resposta.equals("sí")) {
            return true;
        } else {
            System.out.println("Gràcies per jugar! Fins aviat.");
            return false;
        }
    }

    public void mostrarResum(ArrayList<String> moviments) {
        System.out.println("\nMoviments de la partida:");
        for (int i = 0; i < moviments.size(); i++) {
            System.out.println(moviments.get(i));
        }
    }

    public void mostrarCapturades(ArrayList<Character> eliminadesB, ArrayList<Character> eliminadesN) {
        
        System.out.print("Capturades blanques: ");
        
        for (int i = 0; i < eliminadesB.size(); i++) {
            System.out.print(eliminadesB.get(i) + " ");
        }
        System.out.println();

        System.out.print("Capturades negres: ");
        
        for (int i = 0; i < eliminadesN.size(); i++) {
            System.out.print(eliminadesN.get(i) + " ");
        }
        System.out.println();
    }
       
}
