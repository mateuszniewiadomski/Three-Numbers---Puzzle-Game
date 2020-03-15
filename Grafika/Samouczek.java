/*

Projekt Łamigłówki "Trzy Liczby" Mateusz Niewiadomski 2020 r.

*/

package Grafika;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Samouczek extends JFrame implements ActionListener {

    private JButton bZrozumialem = new JButton("ZROZUMIAŁEM");
    private JTextArea tInstrukcja = new JTextArea();
    private JPanel pInstrukcja = new JPanel();
    private JLabel lObrazek = new JLabel();
    private JPanel pObrazek = new JPanel();
    private JTextArea tOpis = new JTextArea(50, 30);
    private JPanel pOpis = new JPanel();
    private ImageIcon imObraz = new ImageIcon("/home/mateusz/Uczelnia/sem3/ProgramowanieObiektowe/mniewiadomski/Projekt/Grafika/samouczek_low.gif");

    //Konstruktor tworzy okno samouczka


    Samouczek() {

        setSize(600, 630);
        setTitle("Trzy Liczby - Instrukcja");
        setLocation(1010, 0);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(255, 248, 229));

        pInstrukcja.setBounds(0, 0, 600, 50);
        tInstrukcja.setText("INSTRUKCJA");
        tInstrukcja.setBackground(new Color(255, 248, 229));
        tInstrukcja.setForeground(new Color(146, 208, 80));
        tInstrukcja.setFont(new Font("Arial", Font.PLAIN, 30));
        tInstrukcja.setEditable(false);
        pInstrukcja.add(tInstrukcja);
        pInstrukcja.setBackground(new Color(255, 248, 229));
        add(pInstrukcja);
        
        pOpis.setBounds(0, 60, 600, 210);
        tOpis.setText("Tylko trzema różnymi liczbami należy wypełnić puste pola diagramu w taki sposób, aby powstał kwadrat magiczny: \nsuma liczb w każdym rzędzie, kolumnie i na każdej z dwu \nprzekątnych powinna być równa 100. Mały przykład z \nrozwiązaniem (suma 20) powinien rozwiać ewentualne \nwątpliwości. \nTryb łatwy pokazuje czy wytypowana liczba jest większa, \nlub mniejsza od liczby wytypowanej przez komputer, \npoprzez wskazanie tła odpowiednim kolorem: czerwony - za dużo, niebieski - za mało, zielony - liczba poprawna.");
        tOpis.setBackground(new Color(255, 248, 229));
        tOpis.setForeground(new Color(146, 208, 80));
        tOpis.setFont(new Font("Arial", Font.PLAIN, 17));
        tOpis.setEditable(false);
        tOpis.setLineWrap(true);
        pOpis.add(tOpis);
        pOpis.setBackground(new Color(255, 248, 229));
        add(pOpis);


        pObrazek.setBounds(0, 270, 600, 240);
        Image iObraz = imObraz.getImage().getScaledInstance(600, 240, Image.SCALE_DEFAULT);
        ImageIcon imObrazRes = new ImageIcon(iObraz);
        lObrazek.setIcon(imObrazRes);
        pObrazek.add(lObrazek);
        pObrazek.setBackground(new Color(255, 248, 229));
        add(pObrazek);

        bZrozumialem.setBounds(200, 520, 170, 50);
        bZrozumialem.setBackground(new Color(255, 230, 153));
        bZrozumialem.setForeground(new Color(146, 208, 80));
        bZrozumialem.setFont(new Font("Arial", Font.PLAIN, 17));
        add(bZrozumialem);
        bZrozumialem.addActionListener(this);
    }


    //Dodaje interakcje z intefejsem (klikanie)
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object z = ae.getSource();
        if(z == bZrozumialem) {
            dispose();
        }
    }
}