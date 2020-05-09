## Yksikkötestit

Käytössä on PIT-mutaatiotestaus. Jokaiselle luokalle on pyritty tekemään mahdollisimman kattavat luokkakohtaiset testit.

## Suorituskykytestit

### ResizableTable

ResizableTable-tietorakennetta testaan yksinkertaisemmin toteuteuttua SimpleResizableTable-tietorakennetta vastaan. Saamani tulokset testillä (ResizableTablePerformanceTest), jossa skaalataan 1920x1080 kokoinen taulukko ensin puolet pienemmäksi ja sitten takaisin alkuperäiseen kokoon, olivat keskimäärin seuraavat: 

| ResizableTable | SimpleResizableTable|
|----------|-------------|
| 3107 ms | 9142 ms |

### Verkko- ja taulukkototeutusten suorituskyky

RecomputingVerticalSeamTable ja RecomputingVerticalSeamGraph -luokkien suorituskykyä vertaillaan ScalingPerformanceTest -testissä, jossa testikuvaa pienennetään puolet pienemmäksi leveyssuunnassa. Verkkototeutus toimii odotetusti nopeammin kuin taulukkototeutus. Ero oli suurempi kuin oletin.

| Taulukko | Verkko|
|----------|-------------|
| 14912 ms | 2206 ms |


## Skaalaustulos

Alkuperäinen kuva:

![original arch](../jar/arch.jpg)

Skaalattu puolet pienemmäksi leveyssuunnassa:

![arch](../jar/arch_half_width.png)

Skaalattu puolet suuremmaksi leveyssuunnassa:

![arch](../jar/arch_one_and_a_half_width.png)

