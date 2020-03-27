# Viikkoraportti 2

## Edistyminen

* RGB-arvojen lukemiseen löytyi ratkaisu; arvot luetaan Javan valmiilla yleisellä (mutta hitaalla) menetelmällä vain jos ohjelma ei tunne parempaa menetelmää käsiteltävälle kuvatyypille.
* Ensimmäinen oma tietorakenne (Table) on luotu.
* Energiatauluja saadaan nyt luotua Dual-gradient energiafunktiolla.  
* Luokille on kirjoitettu kattavat yksikkötestit.
* Mutaatiotestaus ja checkstyle otettu käyttöön.

## Ongelmia
Saumojen etsiminen tehokkaasti on osoittautunut haastavaksi käytännössä, sillä energiatauluja tulisi päivittää aina kuin sauma poistetaan. Mietin uudestaan ohjelman luokkarakennetta, ja liitän energia-arvot osaksi pikseliolioita. Pikseleiden täytyy ehkä myös tietää sijaintinsa taulussa, jotta taulua olisi helpompi käsitellä verkkona.

## Kysymyksiä
Miten PIT-testauksen saa konfiguroitua siten, että se testaa jokaisen luokan vain niille tarkoitetulla testillä? Haluan esim. testata ArrayTable-luokan vain ArrayTableTest-testillä, mutta PIT ilmoittaa, että muiden luokkien (jotka riippuvat ArrayTablesta) testit "tappavat" mutaatioita.

Onko sallittua käyttää Javan valmiita funktionaalisia rajapintoja algoritmissa vai tuleeko ne kirjoittaa itse?

## Mitä opin
Erityisesti testauksesta olen oppunut lisää. Suunnittelumalleja ja ohjelmointiparadigmoja on tullut pohdittua myös tehokkuusnäkökulmasta.

## Seuraavaksi
Uudelleenjärjestelen luokkia, jonka jälkeen alan etsitä saumoja dynaamisella ohjelmoinnilla. Etsin kaikki polut yhdellä iteraatiolla, ja sen jälkeen päivitän polkuja kun saumoja poistetaan. Saumojen lisääminen täytynee tehdä samassa järjestyksessä kuin jos niitä poistettaisiin.
