## Ohjelman rakenne

Ohjelman alkuperäisen rakenteen tarkoitus on ollut helpottaa koodin kirjoittamista ja testaamista. Rakenne voi vielä muuttua, esim. pikseliolioiden osalta, sillä niiden käytöllä on mahdollisesti vaikutusta suorituskykyyn.

## Suorituskyky

Algoritmin aikavaativuus on 0(n^2), jos n on kuvan leveys ja korkeus pikseleinä. Tällä hetkellä algoritmi yksinkertaisesti laskee jokaisen yksittäisen sauman poistossa kaikki energia-arvot uudestaan ja lisäksi kopioi kuvan uuteen taulukkoon. Full-hd -resoluutioisen kuvan pienentäminen muutamalla sadalla pikselillä leveyssuunnassa ottaa useita sekunteja tämän hetken toteutuksella. Tässä on vielä paljon parantamisen varaa. Kertoimia voidaan saada siis huomattavasti pienemmiksi.