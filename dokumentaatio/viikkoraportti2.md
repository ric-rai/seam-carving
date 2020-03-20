#Viikkoraportti 2

## Edistyminen

* IntelliJ:n käyttö alkaa taas tuntua sujuvalta.
* Peruskomentoliittymä on valmis.
* Luokkien suunnittelu edistynyt ja kokonaisuus alkaa hahmottumaan.
* Energiataulujen laskemiseen tarvittavien luokkien kehitys aloitettu
* Luokille kirjoitettu testejä (jotka ei kaikki valitettavasti vielä mene läpi). 

Javan kuvien käsittelyyn liittyvien luokkien opettelemiseen/ymmärtämiseen on mennyt aikaa.

## Ongelmia ja kysymyksiä
Algoritmin toteuttaminen osoittautui haastavammaksi kuin kuvittelin (yllättäen).
Ikäväksi ongelmaksi on osoittautunut RGB-arvojen saaminen kuvadatasta Javalla. Luettaessa ImageIO-luokan read()-metodilla kuvatiedosto BufferedImage-olioksi tulee kuvan tyypiksi TYPE_3BYTE_BGR tai toisinaan TYPE_INT_RGB (ilmeisesti monokromaattisista kuvista).

Niin, pitääkö ne BufferedImage -oliot muuttaa pikseli kerrallaan toisen tyyppisiksi vai onko joku tapa saada RGB-arvot erityyppisistä kuvista yksinkertaisesti? PixelTable -luokassa yritin nyt muuttaa TYPE_3BYTE_BGR tyyppisen kuvan pikselit normaaleiksi RGB-luvuiksi. Mistä edes tietää, että minkä tyyppisen BufferedImage-olion ImageIO-luokalta saa?

## Mitä opin
Liikaa kaikkea, bittimaskeista väriavaruuksiin.

## Seuraavaksi
Keksin jonkin tavan, jolla saa helposti eri kuvista RGB-arvot, tai sitten hylkään automaattisesti kaikki kuvat, jotka ei ole TYPE_3BYTE_BGR -tyyppiä.

Laitan testit menemään läpi ja siirryn algoritmin seuraavaan vaiheeseen, eli saumojen etsintään.
