Upute za pokretanje:

Aplikacija se pokreće klikom na run gumb u razvojnom okruženju nad razredom AlarmsApplication.java ili iz terminala pozicioniranjem
u direktorij sa aplikacijom te upisivanjem spring run AlarmsApplication.java.

Nakon toga u alatu Postman koristimo sljedeće:

1. Za Keycloak autentifikaciju:
POST zahtjev na URL adresu "https://iotat.tel.fer.hr:58443/auth/realms/spring/protocol/ openid-
connect/token".U tijelu zahtjeva potrebno je ispravno postaviti parametre i specifici-
rati korisniˇcko ime i lozinku s kojima želimo ostvariti vezu.

2. Dodavanje alarma
POST zahtjev na URL adresu "http://localhost:8080/create". Primjer alarma koji dodajemo u body zahtjeva:

{
	"alarmId":"4028b8818853b8d1018853c187000003",
	"deviceId":"SAP01",
	"extractDataQuery":{
		"dataFormat":"csv",
		"timeColumn":"_time",
		"valueColumn":"_value"
	},
	"alarmMessage":"Nema dostupnih mjerenja za BAT!",
	"triggerOperator":"<",
	"triggerValue":"25.5",
	"checkInterval":"45s",
	"dataRequest":{
		"URI":"https://iotat.tel.fer.hr:57786/api/v2/query?org=fer",
		"method":"POST",
		"headers":{
			"Authorization":"a",
			"Content-type":"application/vnd.flux",
			"Accept":"application/csv"
		},
		"payload":"from(bucket:\"telegraf\")\n  |> range(start: -10h)\n  |> filter(fn: (r) => r[\"_measurement\"] == \"BAT\")\n  |> filter(fn: (r) => r[\"id_wasp\"] == \"SAP01\")"
	},
	"alarmTarget":"u1",
	"alarmChannel":"telegram",
	"alarmUrl":"6023056715:AAG6tNbl-9IYUtOfirZHhnjoHstlYU4rwek;6272648058"
}

3. Ažuriranje alarma 
PUT zahtjev na URL adresu "http://localhost:8080/update/<ALARM_ID>". U bodyju alarma upisujemo nove vrijednosti koje želimo promijeniti.

4. Brisanje alarma
DELETE zahtjev na URL adresu "http://localhost:8080/delete/<ALARM_ID>".




Kako bi aplikacija uspješno slala alarme na Microsoft Teams i Telegram platforme bilo je potrebno kreirati Microsoft Teams i Telegram bot.

Kreiranje Telegram bota uključuje nekoliko koraka.
Prvi korak je otvaranje razgovora s Telegramovim "BotFatherom". BotFather je
službeni bot koji olakšava stvaranje novih botova. U razgovor s BotFatherom treba
unijeti naredbu "/newbot" kako bi se započeo proces stvaranja novog bota. Nakon
unosa naredbe, BotFather traži da mu se dodijeli ime za novog bota. Ime koje sam
odabrala "Alarms2", koristi se za identifikaciju novonastaloga bota.
Sljedeći korak je odabir korisničkog imena (eng. username) za novog bota. Bitno je
za napomenuti da korisničko ime treba biti jedinstveno te završavati s "bot". Odabrala
sam "Alarmi2bot".
Nakon toga BotFather kreira novog bota te šalje poruku s linkom preko kojeg se
pristupa botu. Također pruža token koji se koristi za pristup HTTP API-ju bota te neke
dodatne upute.
Za ostvarivanje komunikacije s botom unutar aplikacije, važno je također imati
pridružen Chat ID. Da bismo dobili Chat ID, potrebno je započeti konverzaciju s
botom slanjem proizvoljnih poruka. Nakon toga, možemo koristiti web preglednik i 
posjetiti URL "https://api.telegram.org/bot<bot_token>/getUpdates". Na toj stranici
možemo izvući Chat ID koji je povezan s našom konverzacijom. 


Kreiranje Microsoft Teams bota također se sastoji od više koraka. Najprije sam u timu
"IOT Alarms" otvorila novi kanal nazvan "IoTAlarms" u koji želim integrirati bota.
Klikom na tri točke pored imena kanala otvorio mi se izbornik s dodatnim opcijama
iz kojeg sam odabrala opciju "Connectors". U odjeljku "Connectors" odabrala sam
"Incoming Webhook" klikom na nju. Zatim mi se otvorio prozor sa formom u koju
je trebalo upisati ime za Webhook, odabrala sam "Alarms". 
Nakon što je Webhook stvoren, prikazao se URL koji mi je potreban
za integraciju u aplikaciju. Kopirala sam taj URL jer će mi trebati prilikom 
kreiranja alarma kako bi se mogla uspostaviti komunikacija sa Microsoft Teamsom. Nakon
uspostavljanja konekcije bot će u kanal poslati odgovarajuću poruku o uspostavljenoj 
konekciji, a prilikom pokretanja aplikacije i generiranja alarma u kanal će se slati
odgovarajuća poruka za taj alarm.
