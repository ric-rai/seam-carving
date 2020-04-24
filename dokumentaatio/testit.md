## Yksikkötestit

Käytössä on PIT-mutaatiotestaus. Jokaiselle luokalle pyrin tekemään mahdollisimman kattavat luokkakohtaiset testit.

## Suorituskykytestit

### ResizableTable

ResizableTable-tietorakennetta testaan yksinkertaisemmin toteuteuttua SimpleResizableTable-tietorakennetta vastaan. Saamani tulokset testillä (ResizableTablePerformanceTest), jossa skaalataan 1920x1080 kokoinen taulukko puolet pienemmäksi olivat keskimäärin seuraavat: 

| ResizableTable | SimpleResizableTable|
|----------|-------------|
| 2258 ms | 6566 ms |

## Laadun testaaminen

Tällä hetkellä algoritmilla voi skaalata kuvia leveyssuunnassa pienemmäksi. Alla on ensimmäisiä tuloksia helpoilla kuvilla. Kumpikin kuva skaalattiin leveydeltään puolet pienemmäksi.

![original beach](../jar/beach.jpg)

![original beach](../jar/beach_scaled.png)

![original beach](../jar/tower.jpg)

![original beach](../jar/tower_scaled.png)

