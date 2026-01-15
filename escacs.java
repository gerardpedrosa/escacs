import java.util.ArrayList;
import java.util.Scanner;

public class escacs {

    public static void main(String[] args) {
        escacs p = new escacs();
        p.principal();
    }

    Scanner sc = new Scanner(System.in);

    public void principal() {
        System.out.println("Benvingut al joc d'escacs!");

        char[][] tauler = new char[8][8];

        ArrayList<String> moviments = new ArrayList<String>();

        demanarJugadors();
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

    }

    public void inicialitzarTauler(char[][] tauler) {
        
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
}