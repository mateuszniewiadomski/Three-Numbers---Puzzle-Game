/*

Projekt Łamigłówki "Trzy Liczby" Mateusz Niewiadomski 2020 r.

*/

package Logika;


public class SprawdzPoprawnosc {
    
    //Funkcja sprawdza czy suma wszystkich wierszy, kolumn i skosów jest równa "100" 

    public boolean Czek(int[][] dane) {
        int[] suma = new int[14];
        for(int i = 0; i < 14; i++) {
            suma[i] = 0;
        }
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                suma[i] += dane[i][j];
            }
        }
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                suma[i+6] += dane[j][i];
            }
        }
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                if(i==j) {
                    suma[12] += dane[j][i];
                }
                if(i==5-j) {
                    suma[13] += dane[j][i];
                }
            }
        }
        for(int i = 0; i < 14; i++) {
            if(suma[i] != 100) {
                return false;
            }
        }
        return true;
    }
}