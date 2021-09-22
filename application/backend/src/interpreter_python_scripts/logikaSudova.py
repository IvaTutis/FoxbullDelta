from pj import *

class TT(enum.Enum):
    NEGACIJA, KONJUKCIJA, DISJUNKCIJA, OTVORENA, ZATVORENA = '!&|()'
    KONDICIONAL, BIKONDICIONAL = '->', '<->'
    class VAR(Token):
        def vrijednost(self, **interpretacija):
            return pogledaj(interpretacija, self)

def lv_lex(string):
    lex = Tokenizer(string)
    for znak in iter(lex.čitaj, ''):
        if znak.isspace():
            lex.zanemari()
        elif znak == 'P':
            broj = lex.čitaj()
            if not broj.isdigit():
                lex.greška('Krivi format varijable')
            if broj != '0':
                lex.zvijezda(str.isdigit)
            yield lex.token(TT.VAR)
        elif znak == '-':
            lex.pročitaj('>')
            yield lex.token(TT.KONDICIONAL)
        elif znak == '<':
            lex.pročitaj('-')
            lex.pročitaj('>')
            yield lex.token(TT.BIKONDICIONAL)
        else:
            yield lex.literal(TT)

'''
formula -> NEG formula | PVAR | OTV formula binvez formula ZATV
binvez -> KONJ | DISJ | KOND | BIKOND
'''
class LVParser(Parser):
    def formula(self):
        if self >> TT.VAR:
            return self.zadnji
        elif self >> TT.NEGACIJA:
            trenutni = self.formula()
            return Negacija(trenutni)
        elif self >> TT.OTVORENA:
            lijevo = self.formula()
            operator = self.pročitaj(TT.KONDICIONAL, TT.BIKONDICIONAL, TT.DISJUNKCIJA, TT.KONJUKCIJA)
            desno = self.formula()
            self.pročitaj(TT.ZATVORENA)
            return Binarna(lijevo, operator, desno)
        else:
            raise self.greška()

    start = formula

class Negacija(AST('izraz')):
    def vrijednost(self, **interpretacija):
        return not self.izraz.vrijednost(**interpretacija)

class Binarna(AST('lijevo operator desno')):
    def vrijednost(self, **interpretacija):
        l = self.lijevo.vrijednost(**interpretacija)
        d = self.desno.vrijednost(**interpretacija)
        o = self.operator

        if o ^ TT.DISJUNKCIJA:
            return l or d
        elif o ^ TT.KONDICIONAL:
            return l <= d
        elif o ^ TT.KONJUKCIJA:
            return l and d
        elif o ^ TT.BIKONDICIONAL:
            return l == d
        else:
            assert False, 'nepokriveni slučaj'

def pars(niz, tokeni):
    fo = LVParser.parsiraj(tokeni)
    return fo.vrijednost(P0 = niz[0], P1 = niz[1], P2=niz[2], P3=niz[3], P4=niz[4], P5=niz[5], P6=niz[6], P7=niz[7], P8=niz[8], P9=niz[9])

if __name__ == '__main__':

    broj = brojVarijabli
    niz = nizIstinitosti
    tokeni = list(lv_lex(ulaz))
   # s=tokeni
    s = pars(niz, tokeni) #u toj funkciji ce se dogoditi ove dvije stvari dolje
    #fo = LVParser.parsiraj(tokeni)
    #s = fo.vrijednost(P0=False, P3=True, P5=False)

   # ulaz = '(P0 & P1)'
   # ulaz1 = '!(P5&!!(P3->P0))'

    #tokeni = list(lv_lex(ulaz1))
    #print(*tokeni)
    #print('\n')

    #fo = LVParser.parsiraj(tokeni)
    #print(fo.vrijednost(P0=False, P3=True, P5=False))