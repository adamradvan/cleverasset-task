# Intersection App

### Configurable properties
V križovatke figurujú tieto entity:
- 2 semafory, každý semafor svieti na zeleno premenlivý čas
  - každý semafor má svoje označenie (viz. `SemaphoreType` enum)
- 4 cesty pre autá, na každú cestu príde premenlivý počet áut v premenlivom intervale
  - každá cesta má svoje označenie (viz. `CarLaneType` enum)

Premenlivé hodnoty som zadeklaroval ako konfiguračné properties v `application.yml`
- je konfigurovateľné pre aké cesty je semafor zelený (`intersection-app.semaphores[].on-green`)
- je konfigurovateľné v akom poradí semafory nasledujú po sebe

### Design
- Appka beží na SpringBoote, podstata appky je že sa v daných intervaloch spracúvajú udalosti
ako príchod auta, zelená na semafore, odchod auta ...
- Komponenty vystupujúce v appke som zadeklaroval ako beany v `IntersectionConfiguration`
  - teoreticky a prakticky `CarLane` + `Semaphore` nemusia byť beany ale chcel som si zjednodušiť prácu aspoň touto Bean deklaráciou o nich prehlásiť, že budú Singletony
- Appka si drží kontext `IntersectionContext` kde v podstate sa len prepína aktívny semafor (semafor kde je zelená)
- Kompnenty, kt. robia scheduling to robia cez `TaskScheduler` a nie cez `@Scheduled`
  - `CarLaneHandler`
    - pridáva a odstraňuje autá do/z fronty (každá cesta má svoju frontu áut)
  - `CarMayLeavePublisher`
    - v intervale publikuje eventy (`CarMayLeaveEvent`) o tom, že auto môže opustiť frontu
  - `SemaphoreHandler`
    - prepína semafory v štýle, že vždy keď začne jeden semafor svietiť na zeleno `SemaphoreGreenStartedEvent`
      tak nascheduluje ďaľší semafor, ktorý sa prepne za `t1/t2` čas, 
      podľa toho ako dlho má aktuálny semafor svietiť na zeleno

### Notes
Appka má silu v tom, že sú veci konfigurovateľné cez `application.yml`, a dá sa cez takúto konfiguráciu jednoducho 
aj pridávať nové cesty, semafory alebo meniť logiku semaforov v zmysle, že pre aké cesty svietia na zeleno.

V appke je zjednodušený proces kedy autá odchádzajú z križovatky. 
Namiesto toho aby po zelenej sa spúšťal interval kedy môže auto odísť, 
tak som si to zjednodušil na to, že tento interval nie je naviazaný na to, kedy začala zelená. 
V intervale sa publikujú eventy o tom, že auto môže odísť a ak má zelenú tak odíde.

Pridal som nejaký základný logging priebehu aplikácie ale nie je to uniformné
a dalo by sa určite toto zlepšiť aby sa situácia na križovatke vykreslila lepšie a priamo.
