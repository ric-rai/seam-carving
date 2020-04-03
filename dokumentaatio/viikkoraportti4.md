# Viikkoraportti 4

## Edistyminen

* Algoritmi toimii ja sillä voi skaalata kuvia rajoitetusti!
* Luokkia ja koodia on uudelleenjärjestelty; funktionaaliset rakenteet poistettu.

## Ongelmia
Koska en löytänyt mistää esimerkkiä, että miten energia-arvoja tulisi päivittää saumojen poistamisen jälkeen, niin olen kehitellyt siihen algoritmiä. Hahmotan nyt, että mitä saumojen poiston jälkeen tulisi vaihe vaiheelta tehdä ja miten vältytään päivittämästä energiatauluja muilta kuin välttämättömin osin. En kuitenkaan tiedä toimiiko kehittelemäni algoritmi todellisuudessa oikein kaikissa tapauksissa. Ehkä vain koodaan sen ja testaan, että miten se toimii kuvien kanssa. Tosin sen kirjoittaminen tulee olemaan työlästä ja kaukana yksinkertaiseta.

Yksi vaihtoehto on laskea yhdellä kerralla useita toisistaan erillisiä saumoja, ja sitten poistaa niitä. En ole kuitenkaan varma, miten se vaikuttaa skaalauksen lopputulokseen. Nyt kun olen algoritmiä pohtinut. Sitten on vielä mahdollista päivittää jokaisen sauman poiston jälkeen vain pyramidin (jonka huippu on sauman alkupikseli) muotoinen alue energiatauluista.

## Kysymyksiä
Ei mitään erityistä kysyttävää. Tällä hetkellä mietityttää eniten, että miten saumojen poisto tulisi tehdä, jos ei halua laskea jokaisen sauman poiston jälkeen kaikkien pikseleiden perus- ja kumulatiivista energiaa uudelleen.

Voiko testeissä käyttää javan valmiita tietorakenteita ja funktionaalisia ominaisuuksia? 

## Mitä opin
Opin, että kannattaa pysähtyä todella pohtimaan jokaisessa vaiheessa, että mitä kaikkea on mahdollista tehdä esim. yhdellä taulukon läpikäynnillä, jotta välttyy turhalta koodaamiselta.

## Seuraavaksi
Aion tehostaa algoritmia. Yritän vielä etsiä jotain sellaisia esimerkkitoteutuksia algoritmista, joissa ei päivitetä kaikkia energioita jokaisen saumanpoiston jälkeen.
