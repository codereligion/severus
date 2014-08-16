package com.codereligion.versions;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.FluentIterable.from;

final class ParseCommand implements Command {

    @Override
    public int execute(Options options) {
        final Format format = options.getFormat();
        final Version version = options.getVersion();

        switch (format) {
            case TEXT:
                text(version);
                break;
            case SCRIPT:
                script(version);
                break;
            case JSON:
                json(version);
                break;
            case XML:
                xml(version);
                break;
            default:
                throw new UnsupportedOperationException(format.toString());
        }

        return 0;
    }

    private void text(Version version) {
        System.out.println("Major: " + version.getMajor());
        System.out.println("Minor: " + version.getMinor());
        System.out.println("Patch: " + version.getPatch());
        System.out.println("Pre-release: " + version.getPreRelease());
        System.out.println("Build: " + version.getBuild());
    }

    private void script(Version version) {
        System.out.println("MAJOR=" + quote(version.getMajor()));
        System.out.println("MINOR=" + quote(version.getMinor()));
        System.out.println("PATCH=" + quote(version.getPatch()));
        System.out.println("PRE_RELEASE=" + quote(version.getPreRelease()));
        System.out.println("BUILD=" + quote(version.getBuild()));
    }

    private String quote(Object s) {
        return '"' + s.toString() + '"';
    }

    private void json(Version version) {
        final Map<String, Object> map = Maps.newLinkedHashMap();

        map.put("major", version.getMajor().getValue());
        map.put("minor", version.getMinor().getValue());
        map.put("patch", version.getPatch().getValue());
        map.put("pre-release", listify(version.getPreRelease()));
        map.put("build", listify(version.getBuild()));

        new Gson().toJson(map, System.out);
    }

    private List<Object> listify(Iterable<? extends Identifier<?>> identifiers) {
        return from(identifiers).transform(toValue()).toList();
    }

    private Function<Identifier<?>, Object> toValue() {
        return new NullHostileFunction<Identifier<?>, Object>() {
            @Override
            public Object apply(Identifier<?> input) {
                return input.getValue();
            }
        };
    }

    private void xml(Version version) {
        final DocumentBuilder builder;

        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException(e);
        }

        final Document document = builder.newDocument();

        final Element root = document.createElement("version");
        document.appendChild(root);

        root.appendChild(node(document, "major", version.getMajor()));
        root.appendChild(node(document, "minor", version.getMinor()));
        root.appendChild(node(document, "patch", version.getPatch()));
        root.appendChild(node(document, "pre-release", version.getPreRelease()));
        root.appendChild(node(document, "build", version.getBuild()));

        final Transformer transformer;
        
        try {
            final TransformerFactory factory = TransformerFactory.newInstance();
            transformer = factory.newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new IllegalStateException(e);
        }

        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        
        try {
            transformer.transform(new DOMSource(document), new StreamResult(System.out));
        } catch (TransformerException e) {
            throw new IllegalStateException(e);
        }
    }

    private Node node(Document document, String tag, Identifier<?> identifier) {
        final Element node = document.createElement(tag);
        node.setTextContent(identifier.getValue().toString());
        return node;
    }

    private Node node(Document document, String tag, Iterable<? extends Identifier<?>> identifiers) {
        final Element node = document.createElement(tag);

        for (Identifier<?> identifier : identifiers) {
            node.appendChild(node(document, "identifier", identifier));
        }

        return node;
    }

}
