# Křižovatka #
Zadání úkolu pro účely hiringu do týmu CleverAssets.

## Zadání ##
Vytvořte a okomentujte návrh tříd a rozhraní pro aplikaci - simulátor dopravní křižovatky. 
Měla by to být jednoduchá aplikace malého rozsahu, kterou by bylo možné implementovat za 1-2 dny práce.

Simulátor obsahuje pouze jednu konkrétní křižovatku. Je to klasická křižovatka 4 silnic (sever-jih-východ-západ), 
každá má v příchozím směru jen jeden jednoduchý semafor, který ukazuje pouze zelenou nebo červenou. Semafory jsou
řízeny tak, že po nějakou dobu t1 svítí semafory na silnicích sever a jih zeleně, zatímco na silnicích západ a východ
červeně. Následně se barvy prohodí a po dobu t2 svítí opačně. A tak stále dokola.
Auta přijíždějí ke křižovatce ze všech 4 směrů v počtech: ze severu c1 aut za minutu, z jihu c2 aut za minutu,
ze západu c3 aut za minutu, z východu c4 aut za minutu. Automobily opouští křižovatku ve stejném pořadí, v jakém přijely.
Opuštění křižovatky trvá autu s1 sekund, pak teprve může odjet další. Všechna auta jedou pouze rovně, žádné neodbočuje.
Žádné jiné okolnosti nebo prvky nehrají v simulaci roli. Neřešte tedy například kolize automobilů, přednosti v jízdě apod.

Zde uvádíme příklad toho, jak problém uchopit: lépe než real-time simulace může být aplikace navržena jako 
[diskrétní simulace](https://cs.wikipedia.org/wiki/Diskr%C3%A9tn%C3%AD_simulace). To znamená, že aplikace bude mít jakousi
časovou frontu událostí. V té budou seřazeny naplánované události podle času, kdy mají nastat. Potom v aplikaci běží smyčka,
která frontu událostí postupně vybírá a spouští tyto události.
Příkladem události může být:

- nové auto přijelo ke křižovatce z východu 
- semafory přepnou světla

Obsloužení takové události může mít za následek modifikaci různých objektů v aplikaci nebo například přidání nových událostí
do časové fronty nebo jejich odebrání.

## Hodnocení ##
Kritéria hodnocení jsou:

1. funkčnost návrhu - zda takto navržená aplikace je vůbec realizovatelná
2. srozumitelnost popisu - jak srozumitelně to dokážete vysvětlit, popsat případně nakreslit
3. elegance návrhu - jak dobře jsou navrženy třídy a rozhraní z hlediska realizace a údržby

## Řešení ##
Přijímáme řešení v jakékoliv čitelné formě. Například okomentovaný nástřel zdrojového kódu, slovní popis, pseudokód, UML diagramy apod.
Vždy ale myslete na 2. bod hodnocení - srozumitelnost popisu.