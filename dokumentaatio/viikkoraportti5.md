# Viikkoraportti 5

## Edistyminen

* Vertaisarviointi tehty.
* Toteutusdokumenttia laajennettu.
* Aloitettu merkittävä uudelleenjärjetely koodiin.
* Suunnitelmat algoritmin tehostamiseksi ovat edenneet.
* Taulu-tietorakenteen (AbstractTable) toiminnallisuuden laajentaminen aloitettu.
* Testit eivät mene tällä hetkellä läpi, koska uudelleenjärjestely vielä kesken. 

## Ongelmia

Olin aikeissa poistaa pikseli-luokan, kunnes totesin, ettei se ole järkevää. Pienenä ongelmana monimutkaiset ehtolauseet metodeissa vain sen tarkastamiseen, että onko käsiteltävänä reunapikseli. Päädyin ratkaisemaan tämän siirtymällä imperatiivisempaan tyyliin, jossa käydään taulu selkeästi vaihe vaiheelta läpi, eli siten, että ensin vasen yläkulma, sitten ylärivin pikselit, oikea yläkulma, sitten rivi kerrallaan, siten että ensimmäinen ja viimeinen sarakkeen käsitellään omissa vaiheissaan, ja niin edelleen...

Saumojen poistamisen ja lisäämisen jälkeinen taulun koon muutos on tuottanut päänvaivaa. Kehittelin tähän ratkaisun System.arraycopy-metodia käyttävän ratkaisun, jonka pitäisi olla huomottavasti tehokkaampi, kuin koko taulukon kopiointi pienemmäksi pikseli pikseliltä. Tämän metodin toteuttaminen 

Kokonaisuus on kuitenkin hahmottunut nyt aika hyvin ja luulen tietäväni miten edetä.

## Kysymyksiä

Ei mitään erityistä kysyttävää.

## Mitä opin

Hyvin suunniteltu olisi puoliksi tehty :grin:

Opin miten tarvittaessa vältetään ehtolauseiden käyttöä. Tällaista algoritmia toteuttaessa tuntuu turvallisimmalta edetä suoraviivaisesti vaihe vaiheelta mahdollisimman vähillä ehdoilla. Koodin luettavuus kärsii jonkin verran.

Luin, että JVM-toteutukset optimoivat luokkien sisäisiä metodikutsuja, jos niitä kutsutaan miljoonia kertoja. Ilmeisesti "final" määree yläluokkien metodeissa voi edesauttaa optimointia.

System.arraycopy on tarpeen kun pitää muunnella ja käsitellä taulukoita. Löysin mielenkiintoisen "binary fill" -algoritmin taulukon nopeaan alustamiseen:
https://stackoverflow.com/questions/9128737/fastest-way-to-set-all-values-of-an-array


## Seuraavaksi
Aion saada koodin perusrakenteen kuntoon, jotta voin pääsen tekemään erilaisia toteutuksia algoritmista.