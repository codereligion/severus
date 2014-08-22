package com.codereligion.severus;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.annotation.concurrent.Immutable;
import java.util.BitSet;

@Immutable
final class GrammarVersionReader implements Reader<Version> {
    
    @Override
    public Version read(String value, VersionPrecedence precedence) {
        return read(value, precedence, ReadMode.NORMAL);
    }

    @Override
    public Version read(String value, VersionPrecedence precedence, ReadMode mode) {
        final CharStream stream = new ANTLRInputStream(value);
        final VersionLexer lexer = new VersionLexer(stream);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final VersionParser parser = new VersionParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new VersionFormatException(msg, e);
            }

            @Override
            public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
                super.reportAmbiguity(recognizer, dfa, startIndex, stopIndex, exact, ambigAlts, configs);
            }
        });

        final VersionParser.VersionContext version = parser.version();

        final ParseTreeWalker walker = new ParseTreeWalker();
        final Listener listener = new Listener(precedence);
        
        walker.walk(listener, version);
        
        return listener.getVersion();
    }

    private static final class Listener extends VersionBaseListener {
        
        private final VersionBuilder builder = Version.builder();

        public Listener(VersionPrecedence precedence) {
            this.builder.precedence(precedence);
        }

        @Override
        public void enterMajor(@NotNull VersionParser.MajorContext ctx) {
            builder.major(ctx.getText());
        }

        @Override
        public void enterMinor(@NotNull VersionParser.MinorContext ctx) {
            builder.minor(ctx.getText());
        }

        @Override
        public void enterPatch(@NotNull VersionParser.PatchContext ctx) {
            builder.patch(ctx.getText());
        }

        @Override
        public void enterPreRelease(@NotNull VersionParser.PreReleaseContext ctx) {
            // TODO use individual fields 
            builder.preRelease(PreReleaseVersion.valueOf(ctx.getText()));
        }

        @Override
        public void enterBuild(@NotNull VersionParser.BuildContext ctx) {
            // TODO use individual parts?
            builder.build(BuildMetadata.valueOf(ctx.getText()));
        }

        public Version getVersion() {
            return builder.create();
        }
     
    } 
    
}
