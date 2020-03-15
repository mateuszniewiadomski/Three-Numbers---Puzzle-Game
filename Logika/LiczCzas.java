/*

Projekt Łamigłówki "Trzy Liczby" Mateusz Niewiadomski 2020 r.

*/

package Logika;

public class LiczCzas {
    
    //Obiekt przechowuje czas rozpoczęcia i zakończenia łamigłówki, procedra PodajCzas() oblicza czas rozwiązywania

    private long[] czas = new long[2];
    private long roznica;
    public int sekundy, minuty, milisekundy;
    
    public void Start() {
        czas[0] = System.currentTimeMillis();
    }
    
    public void Stop() {
        czas[1] = System.currentTimeMillis();
    }
    
    public void PodajCzas() {
        roznica = czas[1] - czas[0];
        sekundy = milisekundy = minuty = 0;
        while(true) {
            if(roznica >= 60000) {
                minuty++;
                roznica -= 60000;
            } else if(roznica >= 1000) {
                sekundy++;
                roznica -= 1000;
            } else {
                milisekundy = (int)roznica;
                break;
            }
        }
    }
}