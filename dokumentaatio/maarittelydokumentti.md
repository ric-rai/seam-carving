# Määrittelydokumentti

Ajatuksena on ensisijassa luoda ohjelma, joka hyödyntää niin kutsuttua seam carving -algoritmia kuvan sisältötietoiseen skaalaamiseen. Tämä siis käsittää sekä kuvan leveyden tai korkeuden pienentämisen tai suurentamisen.

## Algoritmin rakenne

Algoritmissa on pääpiirteittäin kolme vaihetta.

### 1. Energiataulun luonti
Algoritmi käsittelee kuvan yksittäisiä kuva-alkioita (pikseleitä) muodostaen niistä ns. energiataulun, joka kuvaa jokaisen pikselin energiaa, eli sen merkitsevyyttä kuvan esittämän sisällön kannalta. Tätä energiataulua käytetään seuraavassa vaiheessa vähiten merkitsevien alkiojonojen etsimiseen.

Energiataulun luomisessa voidaan käyttää erilaisia energiafunktioita, jotka laskevat kuva-alkoiden energia-arvot. Eräs yleisesti käytetty energiafunktio on dual gradient -funktio, joka pyrkii laskemaan vertailuarvon pikselin sijaintikohdassa tapahtuvalle pysty- ja vaakasuuntaiselle muutokselle kuvassa. Tämä tapahtuu laskemalla pikselin naapuripikselien arvojen erotuksia. 

Jos ajan puitteissa on mahdollista, niin pyrin toteuttamaan myöhemmässä vaiheessa myös ns. ennakoivan energia-funktion, joka parantaa huomattavasti skaalaustulosta. 

### 2. Vähiten merkitsevien pikselijonojen tunnistaminen 
Seuraavaksi selvitetään vähiten merkitsevät pikselijonot etsimällä pienemmän energian polkuja, jotka kulkevat taulun jostain reuna-alkiosta vastakkaisen reunan alkioon. 

Tämäkin vaihe voidaan toteuttaa usealla eri algoritmilla. Jos mahdollista, niin kokeilen eri tapoja. Ensisijassa käytän tämän vaiheen toteuttamiseen dynaamista ohjelmointia tai Dijkstran algoritmia.

### 3. Pikselijonojen poistaminen tai lisääminen
Kuvaa pienennettäessä jonoja poistetaan ja suurennettaessa kahdennetaan merkitsevyysjärjestyksessä.

## Tarvittavat tietorakenteet
Kovin monimutkaisia tietorakenteita ei tarvita, sillä kuvat ovat pohjimmiltaan kaksiulotteisia taulukoita. Listarakenteiden lisäksi voidaan tarvita esim. miminikekoa. Jonkinlainen taulu-rakenne voisi olla kätevä.

## Aika- ja tilavaativuus
Tilavaativuus on yksinkertaisesti kuvan koko, eli O(wh), missä w on kuvan leveys ja h sen korkeus. Aikavaativuudessa on eri toteutusten välillä vaihtelua lähinnä kertoimissa. Toisaalta aikavaativuus riippuu myös siitä, miten kuvaa haluataan skaalata, eli tehdäänkö skaalaus esimerkiksi sekä pituus että leveyssuunnassa, jolloin myös aikavaativuus on lähtökohtaisesti O(wh).

[Tässä dokumentissa](http://pacman.cs.tsinghua.edu.cn/~cwg/papers_cwg/icpads14.pdf) (2014) on kuvattu melko edistyneitä optimointeja, joissa hyödynnetään rinnakkaisohjelmointia.

## Syötteet ja tulosteet
Ohjelma/algoritmi käyttää yksinkertaista komentoliittymää, jolla syötteet annetaan.

### Syötteet:
* kuvatiedoston polku
* skaalatun kuvan leveys ja pituus
* skaalatun kuvan tiedostopolku

##### Mahdollisesti
* asetuksia: mitä energiafunktiota halutaan käyttää yms..

### Tulosteet:  
* skaalattu kuva

##### Mahdollisesti
* energiataulu
* saumojen (pikselijonojen) havainnointikuva

## Luokkakaavio ja ohjelman rakenne

Ideana on rakentaa algoritmi vaiheittain käyttäen Table-luokkien välillä perintää.

![Diagram](./kuvat/class-diagram.png)
 

## Lähteet
https://en.wikipedia.org/wiki/Seam_carving
https://www.cs.princeton.edu/courses/archive/spring13/cos226/assignments/seamCarving.html
https://avikdas.com/2019/05/14/real-world-dynamic-programming-seam-carving.html
https://avikdas.com/2019/07/29/improved-seam-carving-with-forward-energy.html