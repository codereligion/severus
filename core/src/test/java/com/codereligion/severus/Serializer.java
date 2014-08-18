package com.codereligion.severus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public final class Serializer {

    private Serializer() {
        // utility class
    }

    static <T extends Serializable> T copy(T object) {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            new ObjectOutputStream(output).writeObject(object);
            final byte[] bytes = output.toByteArray();
            final ByteArrayInputStream input = new ByteArrayInputStream(bytes);

            @SuppressWarnings("unchecked")
            final T result = (T) new ObjectInputStream(input).readObject();
            return result;
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
}
