# Specifikace systému #

## Cíl systému: ##
> Snadno dostupné informace o vozech a zákaznících. Zaměstnanci půjčovny budou mít jasný přehled o tom, kdy a jaké vozy jsou k dispozici, za jakou cenu a v jakém jsou stavu. Součástí systému je i blokace vozů v případě poškození a hlášení o nutnosti informovat zákazníka o ztrátě dostupnosti daného vozu v nadcházejících termínech.

## Očekávaný přínos a omezení: ##
  * Databáze vozů a zákazníků.
  * Okamžitý výpis vypůjčených, volných či poškozených vozů, rezervace a blokace vozů pro zákazníky na dané termíny, rozvrhování výpůjček.
  * Automatizované upozornění na nedostupnost vozů v blokovaných/rezervovaných termínech (v případě vypůjčení/odstavení všech vozidel dané kategorie a třídy nebo havárie blokovaného vozidla).
  * Každý vůz v databázi má nastavenu kategorii vozu (dodávka, osobní, sportovní, mikrobus) a třídu (1,2,3).
  * Jednotlivým skupinám vozů (shodná třída a kategorie) je přidělena cena za vypůjčení (kč/den), výše kauce (kč) , penále za pozdní odevzdání (Kč/den), poplatek za prodloužení vypůjčení vozu po termínu (méně než 3 dny před ukončením výpůjční doby).
  * Rezervace vozu zároveň vypíše cenu za vypůjčení včetně kauce. Rezervaci lze provádět nejdéle 5 dní před vypůjčením vozu, pozdější termíny jsou závazné a je rovnou blokován konkrétní vůz (je přijata kauce).
  * Jedna objednávka bude spravovat pouze jeden vůz. Bude-li si chtít zákazník zablokovat více vozů, bude v systému vytvořeno více objednávek jednomu zákazníkovi.
  * Výpůjčku vozu lze prodloužit bez příplatku nejdéle 3 dny před ukončením původní doby výpůjčky.
  * Záznam o navrácení vozu upozorňuje na výši vracené kauce, případně doplatek zákazníka, bylo-li vozidlo navráceno příliš pozdě. V případě předčasného navrácení vozu je do navrácené ceny započítán poplatek, roven 3 dnům ceny výpůjčky (v případě navrácení vozu méně než 3 dny před plánovanou dobou navrácení je započítána jen zbývající doba vypůjčení vozu).
  * Pokud není vůz včas vyzvednut, je vůz nadále blokován, dokud není vyčerpána kauce - poslední 3 dny, které kauce pokrývá propadnou autopůjčovně jako rezerva na další pronájem vozu a blokace vozu zákazníkem propadá.
  * Systém poskytne seznam náhradních vozů v případě neočekávané odstávky blokovaného vozu (nenavrácení vozu jiným zákazníkem, poškození vozu aj.) a umožní vozidlo přidělit zákazníkovi beze změny dojednané ceny za výpůjčku jak před vyzvednutím vozu, tak v průběhu výpujčky.

## Role Systému: ##
### Uživatel: ###
> Zadává a mění zákazníky a jejich objednávky. Zadává a mění vozy a jejich stav. Nechává si zobrazit dostupné vozy a termíny pro jejich blokaci. Je upozorněn na neošetřené problémy objednávek. Blokuje a rezervuje vozy na konkrétní termíny (od-do).
### Čas: ###
> Provádí kontrolu splnění všech termínů zapsaných v systému. Generuje upozornění systému na nesplněné termíny.