package com.codereligion.severus;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.junit.Test;

public final class GrammarVersionReaderTest {

    @Test
    public void test() {
        final CharStream stream = new ANTLRInputStream("1.2.3-beta.17+git.efb45a2");
        final VersionLexer lexer = new VersionLexer(stream);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final VersionParser parser = new VersionParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new VersionFormatException(msg, e);
            }
        });

        final VersionParser.VersionContext version = parser.version();
        
        System.out.println("Major: " + version.major().getText());
        System.out.println("Minor: " + version.minor().getText());
        System.out.println("Patch: " + version.patch().getText());
        
        if (version.preRelease() != null) {
            for (VersionParser.IdentifierContext context : version.preRelease().identifier()) {
                System.out.println("Pre-release: " + context.getText());
            }
        }
        
        if (version.build() != null) {
            for (VersionParser.BuildIdentifierContext context : version.build().buildIdentifier()) {
                System.out.println("Build: " + context.getText());
            }
        }
    }

}
