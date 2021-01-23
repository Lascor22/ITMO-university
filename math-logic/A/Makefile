SOURCES=Main.hs 
GENERATED=Lexer.hs Syntax.hs
PACKAGE=task-1.zip

.PHONY: pack all run clean

all: parser

run: parser
	./parser

clean:
	rm -rf *.o *.hi
	rm -f parser

parser: $(SOURCES)
	ghc Main.hs -o parser