# Ohjelman rakenne ja suorituskyky

Ohjelman alkuperäisen määrittelydokumentissa kuvatun rakenteen tarkoitus on ollut helpottaa koodin kirjoittamista ja testaamista. Rakenne voi vielä muuttua, esim. pikseliolioiden osalta, sillä niiden käytöllä on mahdollisesti vaikutusta suorituskykyyn.

#### Merkinnöistä

Merkintöjen yksinkertaistamiseksi aikavaativuutta käsitellään olettamalla, että kuvan leveys ja korkeus ovat samat, ja että kuvaa skaalataan saman verran sekä leveys- että korkeussuunnassa. Korkeudelle ja leveydelle käytetään merkintää _n_, ja skaalausarvolle, eli sille määrälle pikseleitä, joka tulee lisätä tai poistaa, merkintää _k_.

Algoritmin aikavaativuus eri toteutuksilla on aina luokkaa O(n^2), jos ei käytetä rinnakkaisohjelmointia.

## Tietorakenteet

### ResizableTable
Projektia varten toteutettiin yksi geneerinen tietorakenne ResizableTable. Tietorakenteen idea on mahdollistaa kaksiulotteisen taulukon mahdollisimman tehokas pienentäminen tai suurentaminen vaaka- ja pystysuunnassa.

Sisäisesti luokka ylläpitää tietoa taulukon koon muutoksista kahden indeksitaulukon avulla, joihin se päivittää koon muutoksen myötä muuttuvia rivi- ja sarakeindeksejä.

Taulukon kopiointia manuaalisesti silmukalla vältetään, ja sen sijaan käytetään System.arraycopy()-metodia. Koska Javassa kaksiulotteinen taulukko ei ole muistissa yhtenäisenä, niin siirryttäessä pysty- tai vaakasuuntaisesta koon muutoksesta päinvastaiseen transponoidaan taulukko luokan sisällä. Tämä tietorakenne toimii siis nopeasti vain jos kaikki samansuuntaiset koon muutokset tehdään aina peräkkäin.

Toinen vaihtoehto luokan toteuttamiselle olisi ollut pitää taulukkoa muistissa yksiulotteisena. Oletin nyt, että tällainen toteutus ei olisi ollut yhtä nopea. Jos System.arraycopy(), tai ylipäätään taulukon yhtenäisten lohkojen siirtely muistissa on vakioaikaista, niin siinä tapauksessa tämä toteutustapa olisi paras.

Tietorakenteen suorituskyvyn varmistamiseksi loin testaushakemistoon SimpleResizableTable-luokan, jonka toteutus on hyvin yksinkertainen, mutta ResizableTable luokkaa vastaava. Siinä käytetään vain yksinkertaisia for-silmukoita taulukon koon muuttamiseksi. Suorituskykytestissä ResizableTable oli noin kolme kertaa nopeampi.

### Verkkorakenteet



## Pääluokat

### Pixel

Pixel-luokka kuvaa pikseliolion, jolla on attribuutteina mm. sijainti (row, col), energia ja kumulatiivinen energia. Taulu-oliot säilyttävät ja käsittelevät pikseleitä.

Algoritmeissa käsitellään pikseleitä myös pysty- tai vaakasuuntaisena verkkona, jossa jokaisesta pikselistä voidaan liikkua kolmeen seuraavaan pikseliin. Pystysuuntaisessa verkossa liikutaan ylhäältä alas ja vaakasuuntaisessa vasemmalta oikealle. Vieruspikselit pystysuuntaisessa verkossa ovat

<img src="https://latex.codecogs.com/svg.latex?\text{adjacents}(x,&space;y)=&space;\big\{\text{get}(x-1,y-1),\text{get}(x-1,y),\text{get}(x-1,y&plus;1)\big\}" title="\text{adjacents}(x, y)= \big\{\text{get}(x-1,y-1),\text{get}(x-1,y),\text{get}(x-1,y+1)\big\}" /></br>

### PixelTable

Luo pikselioliot kuvasta.

### EnergyTable

Luokka tarjoaa metodit energia-arvojen laskemiseen. Energia-arvojen laskeminen on toteutettu dual-gradient-energiafunktiolla. Määritellään se funktiona _E_:

<img src="https://latex.codecogs.com/svg.latex?E(x,y)=\sqrt{\Delta&space;_x^2(x,y)&plus;\Delta&space;_y^2(x,y)}" title="E(x,y)=\sqrt{\Delta _x^2(x,y)+\Delta _y^2(x,y)}" /></br>

missä x- ja y-gradienttien neliöt ovat

<img src="https://latex.codecogs.com/svg.latex?\Delta&space;_x^2(x,y)=R_x(x,y)^2&plus;G_x(x,y)^2&plus;B_x(x,y)^2" title="\Delta _x^2(x,y)=R_x(x,y)^2+G_x(x,y)^2+B_x(x,y)^2" /></br>

<img src="https://latex.codecogs.com/svg.latex?\Delta&space;_y^2(x,y)=R_y(x,y)^2&plus;G_y(x,y)^2&plus;B_y(x,y)^2" title="\Delta _y^2(x,y)=R_y(x,y)^2+G_y(x,y)^2+B_y(x,y)^2" /></br>

joissa esimerkiksi

<img src="https://latex.codecogs.com/svg.latex?R_x(x,y)=\big|\,\text{red}(x&plus;1,y)-\text{red}(x-1,y)\,\big|" title="R_x(x,y)=\big|\,\text{red}(x+1,y)-\text{red}(x-1,y)\,\big|" /></br>

kun red(x, y) palauttaa kohdassa (x, y) olevan pikselin punaisen värikomponentin arvon.

### SeamTable

SeamTable -luokat laskevat vähiten merkitseviä pikelijonoja, eli lyhimpiä energiapolkuja, jotka kulkevat kuvan laidasta laitaan.

Saumojen etsintä on toteutettu dynaamisella ohjelmoinnilla. Esimerkiksi **pystysuuntaisessa** taulussa etsitään alimman rivin pikseleistä se, johon on lyhin polku jostain ylimmän rivin pikselistä energia-arvojen toimiessa pikseleiden painoina. Lyhimmän polun painoa kutsutaan pikselin kumulatiiviseksi energiaksi, ja se voidaan määritellä rekursiivisena funktiona _C_

<img src="https://latex.codecogs.com/svg.latex?C(x,&space;y)&space;=&space;E(x,&space;y)&space;&plus;&space;\text{min}(&space;C(x-1,&space;y-1),&space;C(x,&space;y-1),&space;C(x&plus;1,&space;y-1))" title="C(x, y) = E(x, y) + \text{min}( C(x-1, y-1), C(x, y-1), C(x+1, y-1))" /></br>  

## Eri toteutukset

#### _eli SeamTable -alaluokat_

### Uudelleenlaskennalla

RecomputingVertical- ja RecomputingHorizontalSeamTable -luokat toteuttavat algoritmin siten, että kaikkien pikseleiden energia-arvot lasketaan uudelleen jokaisen saumanpoiston tai -lisäyksen yhteydessä. Luokat käyttävät edellä kuvattua dynaamista ohjelmointia saumojen etsimiseen.

Algoritmin suorittamien askelten määrä on aina sama kuvan sisällöstä riippumatta:

<img src="https://latex.codecogs.com/svg.latex?\sum&space;_{i=0}^{k-1}&space;n&space;(n-i)&plus;\sum&space;_{i=0}^{k-1}&space;(n-i)&space;(n-k)" title="\sum _{i=0}^{k-1} n (n-i)+\sum _{i=0}^{k-1} (n-i) (n-k)" /></br>

Yksinkertainen tapaus on kuvan skaalaminen yhden pikselin pienemmäksi, eli k = 1, jonka aikavaativuus on

<img src="https://latex.codecogs.com/svg.latex?\Theta(2&space;n^2-n)" title="\Theta(2 n^2-n)" /></br>

Kyseessä on suoraviivainen, mutta laskennallisesti kallis tapa toteuttaa algoritmi. Käytännössä tarkkaresoluutioisen kuvan skaalaaminen tällä tavalla kestää jopa kymmeniä sekunteja. Esimerkiksi ns. täysteräväpiirtokuvassa on pikseleitä 1920 x 1080 = 2 073 600. Tällä toteutuksella askelia tulee 2 239 746 840, kun kuva skaalataan puolet pienemmäksi.

Laadullisesti tämän toteutuksen lopputulos on erinomainen ja on melko selvää, että algoritmi löytää varmasti jokaisella iteraatiolla vähiten merkitsevän pikselijonon.