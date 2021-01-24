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




