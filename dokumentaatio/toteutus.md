## Ohjelman rakenne ja suorituskyky

Ohjelman alkuperäisen määrittelydokumentissa kuvatun rakenteen tarkoitus on ollut helpottaa koodin kirjoittamista ja testaamista. Rakenne voi vielä muuttua, esim. pikseliolioiden osalta, sillä niiden käytöllä on mahdollisesti vaikutusta suorituskykyyn.

Merkintöjen yksinkertaistamiseksi aikavaativuutta käsitellään olettamalla, että kuvan leveys ja korkeus ovat samat, ja että kuvaa skaalataan saman verran sekä leveys- että korkeussuunnassa. Korkeudelle ja pituudelle käytetään merkintää _n_, ja skaalausarvolle, eli sille määrälle pikseleitä, joka tulee lisätä tai poistaa, merkintää _k_. 

Algoritmin yläraja eri toteutuksilla on aina O(n^2), jos ei käytetä rinnakkaisohjelmointia. Keskitynkin ensisijassa tutkimaan eri toteutusten aikavaativuuden alarajoja.

### Recomputing -toteutus

RecomputingVertical- ja RecomputingHorizontalSeamTable -luokat toteuttavat algoritmin siten, että kaikkien pikseleiden energia-arvot lasketaan uudelleen jokaisen saumanpoiston tai -lisäyksen yhteydessä. Tällöin algoritmin suorittamien askelten määrä on aina sama:

<img src="https://latex.codecogs.com/svg.latex?\sum&space;_{i=0}^{k-1}&space;n&space;(n-i)&plus;\sum&space;_{i=0}^{k-1}&space;(n-i)&space;(n-k)" title="\sum _{i=0}^{k-1} n (n-i)+\sum _{i=0}^{k-1} (n-i) (n-k)" /></br>

Helpoin tapaus on yhden sauman poisto, eli k = 1, joten toteutuksen alarajaksi saadaan:

<img src="https://latex.codecogs.com/svg.latex?\Omega(2&space;n^2-n)" title="\Omega(2 n^2-n)" /></br>

Kyseessä on suoraviivainen, mutta laskennallisesti kallis tapa toteuttaa algoritmi. Käytännössä tarkkaresoluutioisen kuvan skaalaaminen tällä tavalla kestää jopa kymmeniä sekunteja. Esimerkiksi ns. täysteräväpiirtokuvassa on pikseleitä 1920 x 1080 = 2 073 600. Tällä toteutuksella askelia tulee 2 239 746 840, kun kuva skaalataan puolet pienemmäksi.

Laadullisesti tämän toteutuksen lopputulos on erinomainen ja on melko selvää, että algoritmi löytää varmasti jokaisella iteraatiolla vähiten merkitsevän pikselijonon.