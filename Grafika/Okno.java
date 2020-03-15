/*

Projekt Łamigłówki "Trzy Liczby" Mateusz Niewiadomski 2020 r.

*/

package Grafika;

import Logika.MagicznyKwadrat;
import Logika.SprawdzPoprawnosc;
import Logika.LiczCzas;

import Akcja.Plik;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

public class Okno extends JFrame implements ActionListener, KeyListener {

    private JTextField[][] plansza = new JTextField[6][6];
    private MagicznyKwadrat magicznyKwadrat = new MagicznyKwadrat();
    private SprawdzPoprawnosc sprawdzPoprawnosc = new SprawdzPoprawnosc();
    private int[][] dane = new int[6][6];
    private int[][] dane2 = new int[6][6];
    private boolean[][] edytowalne = new boolean[6][6];
    private boolean trudny = true;
    private JButton bStart = new JButton("NOWA GRA");
    private JButton bKoniec = new JButton("WYJDŹ");
    private JButton bCofnij = new JButton();
    private JButton bPrzod = new JButton();
    private JButton bZapisz = new JButton("ZAPISZ");
    private JButton bWczytaj = new JButton("WCZYTAJ");
    private JButton bPokazPlansze = new JButton("ODPOWIEDŹ");
    private JButton bSamouczek = new JButton("?");
    private ButtonGroup bgPoziomTrudnosci = new ButtonGroup();
    private JLabel lTryby = new JLabel("TRYBY:");
    private JPanel pTryby = new JPanel();
    private JRadioButton rbTrudny = new JRadioButton("ZWYKŁY", true);
    private JRadioButton rbLatwy = new JRadioButton("UŁATWIONY", false);
    private JPanel pPanel = new JPanel();
    public LiczCzas liczCzas = new LiczCzas();
    private Plik plik = new Plik();

    //Konstruktor tworzy okno głównej części programu

    public Okno() {
        setSize(940, 630);
        setTitle("Trzy Liczby");
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(146, 208, 80));

        RysujPlansze(plansza, true);    //Procedura rysuje planszę i wprowadza główne parametry

        DodajInterfejs();               //Procedura dodaje przyciski po prawej stronie interfejsu

        liczCzas.Start();               //Rozpoczyna liczenie czasu
    }


    //Dodaje interakcje z intefejsem (klikanie)

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object z = ae.getSource();      //Przypisanie autora wydarzenia
        if (z == bStart) {
            WyczyscPlansze(plansza);    //Procedura czyści planszę
            RysujPlansze(plansza, true);    //Procedura rysuje planszę i wprowadza główne parametry
            liczCzas.Start();           //Rozpoczyna liczenie czasu
        } else if (z == bKoniec) {
            System.exit(0);             //Zakończenie aplikacji
        } else if (z == bCofnij) {
            plik.ListaCofnij(dane, dane2, edytowalne);  //Undo
            WyczyscPlansze(plansza);    //Procedura czyści planszę
            RysujPlansze(plansza, false);   //Procedura rysuje planszę i wprowadza główne parametry
        } else if (z == bPrzod) {
            plik.ListaDoPrzodu(dane, dane2, edytowalne);    //Redo
            WyczyscPlansze(plansza);    //Procedura czyści planszę
            RysujPlansze(plansza, false);   //Procedura rysuje planszę i wprowadza główne parametry
        } else if(z == bSamouczek) {
            Samouczek samouczek = new Samouczek();  //Tworzy okno samouczka
            samouczek.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            samouczek.setVisible(true);
        } else if (z == bZapisz) {
            try {
                plik.Zapisz(dane, dane2, edytowalne); //Zapis
            } catch (FileNotFoundException ffe) {
                JOptionPane.showMessageDialog(null, "BŁĄD");
            }
        } else if (z == bWczytaj) {
            try {
                plik.Wczytaj(dane, dane2, edytowalne);  //Wczytywanie
            } catch (FileNotFoundException ffe) {
                JOptionPane.showMessageDialog(null, "BŁĄD");
            }
            WyczyscPlansze(plansza);    //Procedura czyści planszę
            RysujPlansze(plansza, false);   //Procedura rysuje planszę i wprowadza główne parametry
            liczCzas.Start();           //Rozpoczyna liczenie czasu
        } else if (z == bPokazPlansze) {
            PokazPlansze pokazPlansze = new PokazPlansze(dane, edytowalne);     //Pokazuje okno rozwiazania
            pokazPlansze.setDefaultCloseOperation(DISPOSE_ON_CLOSE);    
            pokazPlansze.setVisible(true);
        } else if (z == rbTrudny) {
            trudny = true;
            KolorujPlansze(trudny);     //Koloruje plansze wzgledem odpowiedniego trybu
        } else if (z == rbLatwy) {
            trudny = false;         
            KolorujPlansze(trudny);     //Koloruje plansze wzgledem odpowiedniego trybu
        } 
    }

    //Dodaje interakcje z intefejsem (naciśniecie klawisza)

    @Override
    public void keyPressed(KeyEvent ke) {

        //Wprowadza nacisnieta cyferke z klawiatury i sprawdza czy jest cyderka, dodatkowo sprawdza poprawnosc zagadki

        Object z = ke.getSource();      //Przypisanie autora wydarzenia
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (z == plansza[i][j]) {
                    try {
                        dane2[i][j] = Integer.parseInt(plansza[i][j].getText());    //Konwertuje stringa na inta
                    } catch (NumberFormatException exc) {
                        dane2[i][j] = 0;
                        plansza[i][j].setText("0");
                        plansza[i][j].setText("");      //Czysci pole, jesli wprowadzono niedozwolony znak
                    }
                    if (sprawdzPoprawnosc.Czek(dane2)) {    //Sprawdza czy rozwiazano
                        liczCzas.Stop();        //Zapisuje czas
                        liczCzas.PodajCzas();   //Liczy czas
                        KolorujPlansze(false);      //Koloruje plansze wzgledem odpowiedniego trybu
                        JOptionPane.showMessageDialog(null, "Gratulacje! Twój czas to:\n" + liczCzas.minuty + " m " + liczCzas.sekundy + " s " + liczCzas.milisekundy + " ms"); //Pokazuje zwyciezkie okno dialogowe
                    } else {
                        KolorujPlansze(trudny); //Koloruje plansze wzgledem odpowiedniego trybu
                    }
                }
            }
        }
    }

    //Dodaje interakcje z intefejsem (puszczenie klawisza)

    @Override
    public void keyReleased(KeyEvent ke) {
        Object z = ke.getSource();      //Przypisanie autora wydarzenia
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (z == plansza[i][j]) {
                    try {
                        dane2[i][j] = Integer.parseInt(plansza[i][j].getText());    //Konwertuje stringa na inta
                    } catch (NumberFormatException exc) {
                        dane2[i][j] = 0;
                        plansza[i][j].setText("0");
                        plansza[i][j].setText("");      //Czysci pole, jesli wprowadzono niedozwolony znak
                    }
                    if (sprawdzPoprawnosc.Czek(dane2)) {        //Sprawdza czy rozwiazano
                        liczCzas.Stop();        //Zapisuje czas
                        liczCzas.PodajCzas();   //Liczy czas
                        KolorujPlansze(false);      //Koloruje plansze wzgledem odpowiedniego trybu
                        JOptionPane.showMessageDialog(null, "Gratulacje! Twój czas to:\n" + liczCzas.minuty + " m " + liczCzas.sekundy + " s " + liczCzas.milisekundy + " ms"); //Pokazuje zwyciezkie okno dialogowe
                    } else {
                        KolorujPlansze(trudny);     //Koloruje plansze wzgledem odpowiedniego trybu
                    }
                }
            }
        }
    }

    //Dodaje interakcje z intefejsem (pojawenie sie literki z klawiatury)

    @Override
    public void keyTyped(KeyEvent ke) {
        Object z = ke.getSource();      //Przypisanie autora wydarzenia
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (z == plansza[i][j]) {
                    try {
                        dane2[i][j] = Integer.parseInt(plansza[i][j].getText());    //Konwertuje stringa na inta
                    } catch (NumberFormatException exc) {
                        dane2[i][j] = 0;
                        plansza[i][j].setText("0");
                        plansza[i][j].setText("");      //Czysci pole, jesli wprowadzono niedozwolony znak
                    }
                    if (sprawdzPoprawnosc.Czek(dane2)) {    //Sprawdza czy rozwiazano
                        liczCzas.Stop();        //Zapisuje czas
                        liczCzas.PodajCzas();   //Liczy czas
                        KolorujPlansze(false);      //Koloruje plansze wzgledem odpowiedniego trybu
                        JOptionPane.showMessageDialog(null, "Gratulacje! Twój czas to:\n" + liczCzas.minuty + " m " + liczCzas.sekundy + " s " + liczCzas.milisekundy + " ms"); //Pokazuje zwyciezkie okno dialogowe
                    } else {
                        KolorujPlansze(trudny);     //Koloruje plansze wzgledem odpowiedniego trybu
                        plik.ListaWpisz(dane, dane2, edytowalne);   //Zapisuje ruch
                    }
                }
            }
        }
    }

    //Rysuje plansze

    public void RysujPlansze(JTextField[][] plansza, boolean nowy) {

        int a, b, a1, a2, a3;

        if(nowy) {  //Sprawdza czy ma tworzyć nową, czy już wykorzystać istniejącą
            magicznyKwadrat.Generuj(dane);  //Generuje dane do planszy

            a1 = dane[0][1];
            a2 = dane[0][2];
            a3 = dane[1][1];

            for (int i = 0; i < 6; i++) {
                a = i * 100 + 4;    //Odstęp między polami
                for (int j = 0; j < 6; j++) {
                    b = j * 100 + 4;    //Odstęp między polami
                    String x = String.valueOf(dane[i][j]);  //Konwertje Inta na Stringa
                    if (dane[i][j] == a1 || dane[i][j] == a2 || dane[i][j] == a3) {     //Sprawdza czy pola są edytowalne i dostosowuje odpowiednio parametry
                        dane2[i][j] = 0;
                        plansza[i][j] = new JTextField();
                        plansza[i][j].setText("0");
                        plansza[i][j].setText("");
                        plansza[i][j].setEditable(true);
                        plansza[i][j].setBackground(new Color(255, 242, 204));
                        plansza[i][j].addKeyListener(this);
                        edytowalne[i][j] = true;
                    } else {
                        dane2[i][j] = dane[i][j];
                        plansza[i][j] = new JTextField();
                        plansza[i][j].setText(x);
                        plansza[i][j].setEditable(false);
                        plansza[i][j].setBackground(new Color(255, 230, 153));
                        edytowalne[i][j] = false;
                    }
                    plansza[i][j].setBounds(b, a, 92, 92);
                    plansza[i][j].setForeground(Color.BLACK);
                    plansza[i][j].setFont(new Font("SansSerif", Font.CENTER_BASELINE, 40));
                    plansza[i][j].setHorizontalAlignment(JTextField.CENTER);
                    add(plansza[i][j]);
                }
            }
            plik.WypelnijDanymi(dane, dane2, edytowalne);   //Wypełnia tablicę danymi
        } else {
            for (int i = 0; i < 6; i++) {
                a = i * 100 + 4;    //Odstęp między polami
                for (int j = 0; j < 6; j++) {
                    b = j * 100 + 4;    //Odstęp między polami
                    plansza[i][j] = new JTextField();
                    if(edytowalne[i][j]) {      //Sprawdza czy pola są edytowalne i dostosowuje odpowiednio parametry
                        if(dane2[i][j] == 0) {
                            plansza[i][j].setText("0");
                            plansza[i][j].setText("");
                        } else {
                            String x = String.valueOf(dane2[i][j]);     //Konwertje Inta na Stringa
                            plansza[i][j].setText(x);
                        }
                        plansza[i][j].setEditable(true);
                        plansza[i][j].addKeyListener(this);
                        plansza[i][j].setBackground(new Color(255, 242, 204));

                    } else {
                        String x = String.valueOf(dane[i][j]);      //Konwertje Inta na Stringa
                        plansza[i][j].setText(x);
                        plansza[i][j].setEditable(false);
                        plansza[i][j].setBackground(new Color(255, 230, 153));
                    }
                    plansza[i][j].setBounds(b, a, 92, 92);
                    plansza[i][j].setForeground(Color.BLACK);
                    plansza[i][j].setFont(new Font("SansSerif", Font.CENTER_BASELINE, 40));
                    plansza[i][j].setHorizontalAlignment(JTextField.CENTER);
                    add(plansza[i][j]);
                }
            }
        }
        KolorujPlansze(trudny);     //Koloruje planszę
    }

    //Usuwa plansze

    public void WyczyscPlansze(JTextField[][] plansza) {

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                remove(plansza[i][j]);
            }
        }
    }

    //Koloruje planszę

    public void KolorujPlansze(boolean klasyczna) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (klasyczna) {
                    if (edytowalne[i][j]) {
                        plansza[i][j].setBackground(new Color(255, 242, 204));
                    }
                } else {
                    if (edytowalne[i][j]) {
                        if(dane2[i][j] == 0) {
                            plansza[i][j].setBackground(new Color(255, 242, 204));
                        } else {
                            if (dane2[i][j] > dane[i][j]) {
                                plansza[i][j].setBackground(new Color(248, 203, 173));
                            } else if (dane2[i][j] < dane[i][j]) {
                                plansza[i][j].setBackground(new Color(189, 215, 238));
                            } else {
                                plansza[i][j].setBackground(new Color(197, 224, 180));
                            }
                        }
                    }
                }
            }
        }
    }

    //Procedura dodaje przyciski po prawej stronie interfejsu, dostosowuje wielkosć i kolorystykę 

    public void DodajInterfejs() {

        pPanel.setBounds(0, 0, 600, 600);
        pPanel.setBackground(new Color(255, 192, 0));
        add(pPanel);

        bStart.setBounds(660, 10, 220, 70);
        bStart.setBackground(new Color(255, 230, 153));
        bStart.setFont(new Font("Arial", Font.PLAIN, 30));
        bStart.setForeground(new Color(146, 208, 80));
        add(bStart);
        bStart.addActionListener(this);

        bKoniec.setBounds(660, 520, 220, 70);
        bKoniec.setBackground(new Color(255, 230, 153));
        bKoniec.setFont(new Font("Arial", Font.PLAIN, 30));
        bKoniec.setForeground(new Color(146, 208, 80));
        add(bKoniec);
        bKoniec.addActionListener(this);

        bCofnij.setBounds(660, 100, 100, 90);
        bCofnij.setBackground(new Color(255, 230, 153));
        bCofnij.setIcon(new ImageIcon("/home/mateusz/Uczelnia/sem3/ProgramowanieObiektowe/mniewiadomski/Projekt/Grafika/wstecz2.png"));
        add(bCofnij);
        bCofnij.addActionListener(this);

        bPrzod.setBounds(780, 100, 100, 90);
        bPrzod.setBackground(new Color(255, 230, 153));
        bPrzod.setIcon(new ImageIcon("/home/mateusz/Uczelnia/sem3/ProgramowanieObiektowe/mniewiadomski/Projekt/Grafika/dalej2.png"));
        add(bPrzod);
        bPrzod.addActionListener(this);

        bZapisz.setBounds(660, 280, 220, 70);
        bZapisz.setBackground(new Color(255, 230, 153));
        bZapisz.setFont(new Font("Arial", Font.PLAIN, 30));
        bZapisz.setForeground(new Color(146, 208, 80));
        add(bZapisz);
        bZapisz.addActionListener(this);

        bWczytaj.setBounds(660, 360, 220, 70);
        bWczytaj.setBackground(new Color(255, 230, 153));
        bWczytaj.setFont(new Font("Arial", Font.PLAIN, 30));
        bWczytaj.setForeground(new Color(146, 208, 80));
        add(bWczytaj);
        bWczytaj.addActionListener(this);

        bPokazPlansze.setBounds(660, 440, 220, 70);
        bPokazPlansze.setBackground(new Color(255, 230, 153));
        bPokazPlansze.setFont(new Font("Arial", Font.PLAIN, 28));
        bPokazPlansze.setForeground(new Color(146, 208, 80));
        add(bPokazPlansze);
        bPokazPlansze.addActionListener(this);

        pTryby.setBounds(660, 210, 220, 30);
        pTryby.setBackground(new Color(255, 230, 153));
        lTryby.setForeground(new Color(146, 208, 80));
        lTryby.setFont(new Font("Arial", Font.PLAIN, 20));
        pTryby.add(lTryby);
        add(pTryby);

        rbTrudny.setBounds(660, 240, 110, 30);
        rbTrudny.setBackground(new Color(255, 230, 153));
        rbTrudny.setForeground(new Color(146, 208, 80));
        bgPoziomTrudnosci.add(rbTrudny);
        add(rbTrudny);
        rbTrudny.addActionListener(this);

        rbLatwy.setBounds(770, 240, 110, 30);
        rbLatwy.setBackground(new Color(255, 230, 153));
        rbLatwy.setForeground(new Color(146, 208, 80));
        bgPoziomTrudnosci.add(rbLatwy);
        add(rbLatwy);
        rbLatwy.addActionListener(this);

        bSamouczek.setBounds(890, 550, 50, 50);
        bSamouczek.setBackground(new Color(255, 230, 153));
        bSamouczek.setFont(new Font("Arial", Font.PLAIN, 30));
        bSamouczek.setForeground(new Color(146, 208, 80));
        add(bSamouczek);
        bSamouczek.addActionListener(this);
    }

}