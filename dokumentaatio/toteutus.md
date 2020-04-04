## Ohjelman rakenne ja suorituskyky

Ohjelman alkuperäisen määrittelydokumentissa kuvatun rakenteen tarkoitus on ollut helpottaa koodin kirjoittamista ja testaamista. Rakenne voi vielä muuttua, esim. pikseliolioiden osalta, sillä niiden käytöllä on mahdollisesti vaikutusta suorituskykyyn.

Merkintöjen yksinkertaistamiseksi aikavaativuutta käsitellään olettamalla, että kuvan leveys ja korkeus ovat samat, ja että kuvaa skaalataan saman verran sekä leveys- että korkeussuunnassa. Korkeudelle ja pituudelle käytetään symbolia _n_, ja skaalausarvolle (määrä pikseleitä, joka tulee lisätä tai poistaa) _k_.

### Recomputing -toteutus: O(k^2 * n^2)

RecomputingVertical- ja RecomputingHorizontalSeamTable -luokat toteuttavat algoritmin siten, että kaikkien pikseleiden energia-arvot lasketaan uudelleen jokaisen saumanpoiston tai -lisäyksen yhteydessä. Aikavaativuus on O(k^2 * n^2).

Tämä on suoraviivainen tapa toteuttaa algoritmi, mutta laskennallisesti erittäin kallis. Käytännössä tarkkaresoluutioisen kuvan skaalaaminen tällä tavalla kestää jopa kymmeniä sekunteja.

Laadullisesti lopputulos on kuitenkin erinomainen ja on melko selvää, että algoritmi löytää jokaisella iteraatiolla vähiten merkitsevän pikselijonon.