/*

Projekt Łamigłówki "Trzy Liczby" Mateusz Niewiadomski 2020 r.

*/

package Logika;

import java.util.Random;

public class MagicznyKwadrat {
    
    /*
    
    Procedura generuje automatycznie planszę, bazuje na losowości rozszerza algorytm 
    magicznego kwadratu 3x3 https://pl.wikipedia.org/wiki/Kwadrat_magiczny_(matematyka)
    i jest w stanie wygenerować ok. 1000 unikalnych planszy 
    
    */

    public void Generuj(int[][] dane) {
        
        Random random = new Random();
        int a, b, c;
        a = random.nextInt(10)+9;
        b = a/(random.nextInt(5)+2)+1;
        c = b/(random.nextInt(3)+2)+1;

        dane[0][0] = dane[3][0] = dane[0][3] = dane[3][3] = a-b;
        dane[0][1] = dane[3][1] = dane[0][4] = dane[3][4] = a+b-c;
        dane[0][2] = dane[3][2] = dane[0][5] = dane[3][5] = a+c;
        dane[1][0] = dane[4][0] = dane[1][3] = dane[4][3] = a+b+c;
        dane[1][1] = dane[4][1] = dane[1][4] = dane[4][4] = a;
        dane[1][2] = dane[4][2] = dane[1][5] = dane[4][5] = a-b-c;
        dane[2][0] = dane[5][0] = dane[2][3] = dane[5][3] = a-c;
        dane[2][1] = dane[5][1] = dane[2][4] = dane[5][4] = a-b+c;
        dane[2][2] = dane[5][2] = dane[2][5] = dane[5][5] = a+b;
        
        dane[0][1] = dane[3][1] = dane[0][4] = dane[3][4] = a+b;
        dane[1][0] = dane[4][0] = dane[1][3] = dane[4][3] = a+b;
        dane[2][2] = dane[5][2] = dane[2][5] = dane[5][5] = a+b;
        
        dane[0][2] +=-2*c;
        dane[1][2] +=2*c;
        dane[3][2] +=-2*c;
        dane[4][2] +=2*c;
        dane[5][0] +=2*c;
        dane[5][1] +=-2*c;
        dane[5][3] +=2*c;
        dane[5][4] +=-2*c;
        
        int test = 0;
        for(int i = 0; i < 6; i++) {
            test += dane[0][i];
        }
        int dopelnienie = 0;
        
        if(test < 100) {
            dopelnienie = random.nextInt((100 - test))/6;   //losuje dopełnienie dla wszyskich liczb
        }

        for(int i=0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                dane[i][j] += dopelnienie;
            }
        }
        test = 0;
        for(int i = 0; i < 6; i++) {
            test += dane[0][i];
        }
        dopelnienie = 100 - test; ////dopełnienia tablice w odpowiednich miejscach, aby suma wierszy, kolumn i skosow byla rowna 100

        dane[0][0] += dopelnienie;
        dane[1][4] += dopelnienie;
        dane[2][1] += dopelnienie;
        dane[3][5] += dopelnienie;
        dane[4][2] += dopelnienie;
        dane[5][3] += dopelnienie;
    }
}