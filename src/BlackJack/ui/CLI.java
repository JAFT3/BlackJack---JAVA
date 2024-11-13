package BlackJack.ui;

import BlackJack.data.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static BlackJack.process.BlackJack.*;


public class CLI {
    static int boardWidth = 600;
    static int boardHeight = boardWidth;

    static  int cardWidth = 110;
    static int cardHeight = 154;

    static JFrame frame = new JFrame("Black Jack");
    static JPanel gamePanel = new JPanel(){
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            try {
                /**Carta Escondida*/
                Image cartaEsconImg = new ImageIcon(getClass().getResource("/BlackJack/data/cards/BACK.png")).getImage();
                if (!stayButton.isEnabled()){
                    cartaEsconImg = new ImageIcon(getClass().getResource(cartaEscondida.getImagePath())).getImage();
                }
                g.drawImage(cartaEsconImg, 20, 20, cardWidth, cardHeight, null);

                /**Carta dealer**/
                for(int i = 0; i < dealerHand.size(); i++){
                    Card card = dealerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, cardWidth + 25 + (cardWidth + 5)*i, 20, cardWidth, cardHeight, null);
                }

                /**Mano Jugador**/
                for (int i = 0; i < manoJugador.size(); i++){
                    Card card = manoJugador.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, 20 + (cardWidth + 5)*i, 320, cardWidth,cardHeight,null);
                }

                if (!stayButton.isEnabled()){
                    dealerSum = reduceDealerAce();
                    playerSum = reducePlayerAce();
                    System.out.println("STAY: ");
                    System.out.println(dealerSum);
                    System.out.println(playerSum);

                    String message = "";
                    if (playerSum > 21){
                        message = "Perdiste! ";
                    } else  if (dealerSum > 21){
                        message = "Ganaste! ";
                    } else if (playerSum == dealerSum){
                        message = "Es un empate";
                    } else if (playerSum > dealerSum){
                        message = "Ganaste! ";
                    } else if (playerSum < dealerSum){
                        message = "Perdiste! ";
                    }

                    g.setFont(new Font("Calibri", Font.PLAIN,30));
                    g.setColor(Color.white);
                    g.drawString(message,220,250);
                }


            } catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    static JPanel buttonPanel = new JPanel();
    static JButton hitButton = new JButton("Pedir");
    static JButton stayButton = new JButton("Plantarse");


        public static void crearTablero(){
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53,101,77));

        frame.add(gamePanel);
        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card card = deck.remove(deck.size()-1);
                playerSum += card.getValores();
                playerAceCount += card.isAce() ? 1: 0;
                manoJugador.add(card);
                if (reducePlayerAce() > 21){
                    hitButton.setEnabled(false);
                }


                gamePanel.repaint();
            }
        });

        stayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);

                while (dealerSum < 17){
                    Card card = deck.remove(deck.size()-1);
                    dealerSum += card.getValores();
                    dealerAceCount += card.isAce() ? 1:0;
                    dealerHand.add(card);
                }
                gamePanel.repaint();
            }
        });

        gamePanel.repaint();

    }

}
