package BlackJack.process;

import BlackJack.data.Card;
import BlackJack.ui.CLI;

import java.util.ArrayList;
import java.util.Random;


public class BlackJack {
    public static ArrayList<Card> deck;
    static Random random =  new Random(); /**Para mezclar el mazo**/

    /**Estos seran los metodos para el dealer**/
    public static Card cartaEscondida;
    public static ArrayList<Card> dealerHand;
    public static int dealerSum;
    public static int dealerAceCount;

    /**Jugador**/
    public static ArrayList<Card> manoJugador;
    public static int playerSum;
    public static int playerAceCount;


    public BlackJack(){
        startGame();
        CLI.crearTablero();
    }

    public static void startGame(){
        /**Con este metodo daremos por iniciado el juego**/
        buildDeck();
        mezclarDeck();

        /**Dealer*/
        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAceCount = 0;

        cartaEscondida = deck.remove(deck.size()-1);
        dealerSum += cartaEscondida.getValores();
        dealerAceCount += cartaEscondida.isAce() ? 1 : 0;

        Card card = deck.remove(deck.size()-1);
        dealerSum += card.getValores();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);

        System.out.println("DEALER: ");
        System.out.println(cartaEscondida);
        System.out.println(dealerHand);
        System.out.println(dealerSum);
        System.out.println(dealerAceCount);

        /**Jugador**/
        manoJugador = new ArrayList<Card>();
        playerSum = 0;
        playerAceCount = 0;

        for(int i = 0; i< 2; i++){
            card = deck.remove(deck.size()-1);
            playerSum += card.getValores();
            playerAceCount += card.isAce() ? 1 : 0;
            manoJugador.add(card);
        }

        System.out.println("PLAYER: ");
        System.out.println(manoJugador);
        System.out.println(playerSum);
        System.out.println(playerAceCount);

    }

    public static void buildDeck(){
        deck = new ArrayList<Card>();
        String [] valores = {"A","1","2","3","4","5","7","8","9","10","J","Q","K"};
        String [] formas = {"C", "D","H","S"}; /**Declaramos las formas que van a tener: C = Clover, D = Diamantes , H = Hearts y S = Spades**/

        for (int i = 0; i<formas.length; i++){
            for(int j =0; j<valores.length; j++){
                Card card = new Card(valores[j],formas[i]);
                deck.add(card);
            }
        }

        System.out.println("Build Deck: ");
        System.out.println(deck);
    }

    public static void mezclarDeck(){
        for(int i = 0; i < deck.size(); i++){
            int j = random.nextInt(deck.size());
            Card cartaAct = deck.get(i);
            Card cartaRand = deck.get(j);

            deck.set(i, cartaRand);
            deck.set(j, cartaAct);

        }

        System.out.println("Despues de mezclar");
        System.out.println(deck);
    }

    public static int reducePlayerAce(){
        while (playerSum > 21 && playerAceCount > 0){
            playerSum -=10;
            playerAceCount -= 1;
        }
        return playerSum;
    }

    public static int reduceDealerAce(){
        while (dealerSum > 21 && dealerAceCount > 0){
            dealerSum -= 10;
            dealerAceCount -= 1;
        }
        return dealerSum;
    }

}
