/*

Projekt Łamigłówki "Trzy Liczby" Mateusz Niewiadomski 2020 r.

*/


import javax.swing.JFrame;

import Grafika.Okno;

public class TrzyLiczby extends JFrame {

    public static void main(String[] args) {
        Okno aplikacja = new Okno();    //Tworzy główne okno aplikacji
        aplikacja.setDefaultCloseOperation(EXIT_ON_CLOSE);
        aplikacja.setVisible(true);
    }
}