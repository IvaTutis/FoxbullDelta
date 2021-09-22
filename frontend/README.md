


## React frontend - opis 
React je open source JavaScript biblioteka za pisanje frontend aplikacija.U njemu smo pisali frontend za našu aplikaciju
koja rješava određene matematičke izraze.

### Instalacija

Instalacija se izvršava pomoću npm/npx packet managera ,koji se može instalirat zajedno s Node.js na ovom linku https://docs.npmjs.com/downloading-and-installing-node-js-and-npm

```
npx create-react-app frontend
cd frontend
npm start
```

### Opis strukture

Ova naredba kreira praznu aplikaciju. Nju je sada potrebo popuniti potrebnim komponentama i servisima.
Svaka komponenta ima render() fju. u kojoj se vraća JSX koji će se renderirat.

Detaljnije o strukturi fajlova:
Svaka React aplikacija je razdvojena na jasno odvojene logičke cjeline: servise i komponente.
Root folder se satoji od:
	App.js		-ima ulogu rutera, preusmjerava na ostale komponente u aplikaciji
	App.css
	index.js  	-root aplikacije, pokreće komponentu App.js
	index.css 

App.js sadrži glavnu logiku za login korisnika. Funkcije  Login i Logoout koje su definirane ovdje
se proslijeđuju ostalim komponentama kako bi ih one koristile kad je potrebno.


App.js preusmjerava komponente na sljedeći način ,tj. vraća u render() funkciji:

	(je li korisnik ulogiran) 
		ne -> vrati Login komponentu (na kojoj je Login forma)
		da -> vrati Router komponentu koja u sebi sadrži 
			rute na sve komponente koje na koje korisnik smije ići jednom ulogiran

Što je ruta (Route)?
	To je komponenta koja olakšava redirect u aplikaciji,predamo joj: path i neku Komponentu na koju
					će se korisnik proslijediti ako ode na taj path


### Kratki opis komponenti

- LoginForm	login forma koja poziva Login fju (koju smo proslijedili kao parametar) pri Submitu 
- Footer i Header komponente : samo ime govori, njih se umeće U App.js-u u JSX kod koji App.js vraća

- MainComponent.js
	komponenta s glavnim izbornikom 

- InputAritmComponent -glavna komponenta za aritmetičke izraze.
			Komponenta ima formu za upis stringa te očekuje string kad se klikne na gumb Solve


- InputLogicComponent- komponenta za logičke izraze
			Komponenta ima polje za logički izraz koje se puni kroz gumbe na sučelju,
			kao rezultat se ispiše korisniku na ekran tablica istinitosti za taj logički izraz
- InputMatrixComponent - komponenta za matrice
			Komponenta zahtjeva da se iz odabira odabere dimenzije matrica, te operacija,
			te će komponenta na izlazu ispisati rezultat u obliku stringa
- HistoryComponenent - Komponenta sadrži popis prošlih upita korisnika

Još malo o strukturi komponenti:
Svaka komponenta ima render() funkciju koja vraća JSX code ,taj code će se iscrtati na ekran.
U konstruktoru komponente se definira STATE , to je array varijabli koji "žive" u toj komponenti.
Te varijable su dio event driven okruženja koje se koristi kod komponenti.
U pravilu to znači da se pisanje State varijable i njihovo čitanje odvaja (jer na primjer ako nešto dobivamo s 
servera, moramo taj podatak čekati,te će se state pobrinuti da pisanje/čitanje te varijable bude korektno).
Svaka komponenta sadrži i funkciju componentDidMount() koja tome pomaže: ona se pozove kada sav code 
završi s iscrtavanjem,u njega uglavnom stavljamo pozive servisa koji čekaju da podaci dođu sa servera.

Servisi su konceptualno isti kao u ostalim jezicima, oni stoje između komponenti i servera.
Mi koristimo 2 servisa ,jedan za upite na Java Controller koji upravlja korisnicima,
i drugi koji služi za izraze.
Servisi su dosta konceptualno jednostavni ,oni samo vraćaju response od Java aplikacije ,pritom se koristi high-level
axios biblioteka, te zbog nje se nemoramo gnjaviti s slanjem podataka i čekanjem (await) servera , on to sve obavi u pozadini.
Jedan jednostavan poziv onda izgleda ovako:

```
return axios.get("http://localhost:8001/expressionControl/get"); 
```


