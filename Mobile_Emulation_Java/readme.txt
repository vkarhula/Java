Mobiiliohjelmointi
HALUATKO MILJONÄÄRIKSI? -PELI

Virpi Karhula
21.2.2003


OHJELMAN AJAMINEN

1. Luodaan uusi hakemisto esim. miljonaari ja sinne alihakemistot:
	src, classes, tmpclasses ja bin

2. Kopioidaan lähdetiedostot src-hakemistoon.
	PeliMIDlet.java
	PeliControl.java
	Kysymys.java
	PaaNaytto.java
	KysymysNaytto.java
	OlkiNaytto.java
	Viesti.java
	Ajastin.java (sisältää myös RemindTask-luokan)
	TulosNaytto.java (sisältää myös Voittaja-luokan)

3. Sijoitetaan bin-hakemistoon MANIFEST.MF -tiedosto,
jonka sisältö on seuraava:

MIDlet-1: Miljonaari,, mil.PeliMIDlet
MIDlet-Name: PeliMIDlet
MIDlet-Vendor: Virpi Karhula
MIDlet-Version: 1.0
MicroEdition-Configuration: CLDC-1.0
MicroEdition-Profile: MIDP-1.0

4. Kopioidaan makefile-tiedosto miljonaari-hakemistoon (sisältö alla).

5. miljonaari-hakemistossa annetaan käsky: make all
tai yksitellen 
	make compile
	make preverify
	make jar
	
6. Käynnistetään emulaattori komennolla: make run

------------------
makefilen sisältö:

PREVERIFY=E:\WTK104\bin\preverify
EMULATOR=E:\WTK104\bin\emulator
COMPILE=E:\j2sdk1.4.0_03\bin\javac
JAR=E:\j2sdk1.4.0_03\bin\jar
LIB=E:\WTK104\lib


CLASSES=mil.PeliMIDlet mil.Kysymys mil.PeliControl mil.PaaNaytto mil.KysymysNaytto mil.OlkiNaytto mil.Viesti mil.Ajastin mil.Ajastin$$RemindTask mil.TulosNaytto mil.TulosNaytto$$Voittaja
JARFILE=PeliMIDlet.jar
MIDLETNAME=mil.PeliMIDlet

all:	compile preverify jar

jar:
	$(JAR) cfm bin\$(JARFILE) bin\MANIFEST.MF -C classes .\

preverify:
	$(PREVERIFY) -classpath $(LIB)\midpapi.zip;tmpclasses -d classes $(CLASSES)

compile:
	$(COMPILE) -bootclasspath $(LIB)\midpapi.zip -classpath src -d tmpclasses src/*.java

run:
	$(EMULATOR) -classpath bin\$(JARFILE) $(MIDLETNAME)

