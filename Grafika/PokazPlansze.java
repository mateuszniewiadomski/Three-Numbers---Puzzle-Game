/*

Projekt Łamigłówki "Trzy Liczby" Mateusz Niewiadomski 2020 r.

*/

package Grafika;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;



public class PokazPlansze extends JFrame {
    
    private JTextField[][] plansza = new JTextField[6][6];
    
    //Konstruktor tworzy okno planszy z rozwiazaniem

    PokazPlansze(int[][] dane, boolean[][] edytowanlne) {

        setSize(300, 330);
        setTitle("Trzy Liczby - Odpowiedź");
        setLocation(1010, 0);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(255, 192, 0));

        RysujPlansze(dane, edytowanlne);
    }

    //Procedura rysuje plansze 

    private void RysujPlansze(int[][] dane, boolean[][] edytowanlne) {
        int a, b = 0;
        for(int i = 0; i < 6; i++) {
            a = i*50 + 2;
            for(int j = 0; j < 6; j++) {
                b = j*50 + 2;
                String x = String.valueOf(dane[i][j]);
                plansza[i][j] = new JTextField();
                if (edytowanlne[i][j]) {
                    plansza[i][j].setBackground(new Color(255, 242, 204));
                    plansza[i][j].setForeground(Color.RED);
                } else {
                    plansza[i][j].setBackground(new Color(255, 230, 153));
                    plansza[i][j].setForeground(Color.BLACK);
                }
                plansza[i][j].setText(x);
                plansza[i][j].setEditable(false);
                plansza[i][j].setBounds(b, a, 46, 46);
                plansza[i][j].setFont(new Font("SansSerif", Font.CENTER_BASELINE, 20));
                plansza[i][j].setHorizontalAlignment(JTextField.CENTER);
                add(plansza[i][j]);
            }
        }
    }
}