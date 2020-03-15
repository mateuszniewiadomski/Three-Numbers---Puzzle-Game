package Akcja;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class Plik extends JFrame {

    private JFileChooser plik = new JFileChooser();
    private ZapisaneDane[] zapisaneDane = new ZapisaneDane[5000];
    private int licznik = 0;
    private int licznikBlok = 0;

    //Wypełnia obiket danymi

    public void WypelnijDanymi(int[][] dane, int[][] dane2, boolean[][] edytowalne){
        for(int m = 0; m < 5000; m++) {
            zapisaneDane[m] = new ZapisaneDane(dane, dane2, edytowalne);
        }
    }

    //Zapisuje do pliku

    public void Zapisz(int[][] dane, int[][] dane2, boolean[][] edytowalne) throws FileNotFoundException {
        if(plik.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {    //otwiera okno wyboru pliku
            File file = plik.getSelectedFile();
            PrintWriter printWriter = new PrintWriter(file);
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    printWriter.println(dane[i][j]);
                    printWriter.println(dane2[i][j]);
                    printWriter.println(edytowalne[i][j]);
                }
            }
            printWriter.println(licznik);
            printWriter.println(licznikBlok);
            for(int m = 0; m < 5000; m++) {
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 6; j++) {
                        try {
                            printWriter.println(zapisaneDane[m].zDane[i][j]);
                            printWriter.println(zapisaneDane[m].zDane2[i][j]);
                            printWriter.println(zapisaneDane[m].zEdytowalne[i][j]);
                        } catch(NullPointerException ex) {
                            printWriter.println(0);
                            printWriter.println(0);
                            printWriter.println(false);
                        }
                    }
                }
            }
            printWriter.close();
        }
        
    }

    //Wczytuje z pliku

    public void Wczytaj(int[][] dane, int[][] dane2, boolean[][] edytowalne) throws FileNotFoundException {
        if(plik.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {    //otwiera okno wyboru pliku
            File file = plik.getSelectedFile();
            Scanner scanner = new Scanner(file);
            for(int i = 0; i < 6; i++) {
                for(int j = 0; j < 6; j++) {
                    dane[i][j] = scanner.nextInt();
                    dane2[i][j] = scanner.nextInt();
                    edytowalne[i][j] = scanner.nextBoolean();                    
                }
            }
            licznik = scanner.nextInt();
            licznikBlok = scanner.nextInt();
            for(int m = 0; m < 5000; m++) {
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 6; j++) {
                        zapisaneDane[m].zDane[i][j] = scanner.nextInt();
                        zapisaneDane[m].zDane2[i][j] = scanner.nextInt();
                        zapisaneDane[m].zEdytowalne[i][j] = scanner.nextBoolean();
                    }
                }
            }
            scanner.close();
        }
    }

    //Zapisuje ruchy

    public void ListaWpisz(int[][] dane, int[][] dane2, boolean[][] edytowalne) {
        zapisaneDane[licznik] = new ZapisaneDane(dane, dane2, edytowalne);
        if(licznik < 4999) {
            licznik++;
            licznikBlok = licznik;
        } else {
            licznik = 0;
        }
    }

    //Redo

    public void ListaDoPrzodu(int[][] dane, int[][] dane2, boolean[][] edytowalne) {
        licznik++;
        if(licznik <= licznikBlok) {
            try {
                for(int i = 0; i < 6; i++) {
                    for(int j = 0; j < 6; j++) {
                        dane[i][j] = zapisaneDane[licznik].zDane[i][j];
                        dane2[i][j] = zapisaneDane[licznik].zDane2[i][j];
                        edytowalne[i][j] = zapisaneDane[licznik].zEdytowalne[i][j];
                    }
                }
            } catch(NullPointerException ex) {
                
            }
        } else {
            licznik = licznikBlok;
        }
    }

    //Undo

    public void ListaCofnij(int[][] dane, int[][] dane2, boolean[][] edytowalne) {
        licznik--;
        if(licznik >= 0) {
            try {
                for(int i = 0; i < 6; i++) {
                    for(int j = 0; j < 6; j++) {
                        dane[i][j] = zapisaneDane[licznik].zDane[i][j];
                        dane2[i][j] = zapisaneDane[licznik].zDane2[i][j];
                        edytowalne[i][j] = zapisaneDane[licznik].zEdytowalne[i][j];
                    }
                }
            } catch(NullPointerException ex) {
            }
        } else {
            licznik = 0;
        }
    }
}

//Obiekt przechowujący zapisane dane

class ZapisaneDane {
    
    public int[][] zDane = new int[6][6];
    public int[][] zDane2 = new int[6][6];
    public boolean[][] zEdytowalne = new boolean[6][6];
    
    //Konstruktor zapisywania danych

    ZapisaneDane(int[][] dane, int[][] dane2, boolean[][] edytowalne) {
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                this.zDane[i][j] = dane[i][j];
                this.zDane2[i][j] = dane2[i][j];
                this.zEdytowalne[i][j] = edytowalne[i][j];
            }
        }
    }
}