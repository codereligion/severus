package com.codereligion.versions;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Subparser;
import net.sourceforge.argparse4j.inf.Subparsers;

import java.io.IOException;
import java.net.URL;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import static com.codereligion.versions.VersionPrecedence.NATURAL;

public final class Main {

    private Main() {
        // static entry point
    }

    public static void main(String... args) throws IOException {
        final ArgumentParser parser = ArgumentParsers.newArgumentParser("version");
        parser.version(extractFromManifest("Version"));

        parser.addArgument("--version").action(Arguments.version());

        final Subparsers commands = parser.addSubparsers();

        final Subparser parse = commands.addParser("parse");
        parse.setDefault("command", new ParseCommand());
        parse.addArgument("version").required(true);
        final Argument format = parse.addArgument("-f", "--format").type(FormatType.INSTANCE);
        format.setDefault(Format.TEXT).choices(FormatType.INSTANCE);

        final Subparser contains = commands.addParser("contains");
        contains.setDefault("command", new ContainsCommand());
        contains.addArgument("range").required(true);
        contains.addArgument("version").required(true);
        final Argument precedence = contains.addArgument("-p", "--precedence").type(PrecedenceType.INSTANCE);
        precedence.setDefault(NATURAL).choices(PrecedenceType.INSTANCE);

        final Subparser validate = commands.addParser("validate");
        validate.setDefault("command", new ValidateCommand());
        validate.addArgument("input").required(true);

        try {
            final Options options = new Options();
            parser.parseArgs(args, options);

            final Command command = options.getCommand();
            System.exit(command.execute(options));
        } catch (ArgumentParserException e) {
            parser.handleError(e);
        }
    }

    private static String extractFromManifest(String key) throws IOException {
        final URL url = Main.class.getProtectionDomain().getCodeSource().getLocation();
        final JarFile jar = new JarFile(url.getFile());
        final Manifest manifest = jar.getManifest();
        return manifest.getMainAttributes().getValue(key);
    }

}
