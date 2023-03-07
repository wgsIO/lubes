package com.github.walkgs.lubes.utilities.identifiers;

import com.github.walkgs.lubes.utilities.Applicable;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Base64;

public class CodeGenerator implements Applicable<CodeGenerator> {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#$%*()/+-!:.,^~][ ";

    @Getter
    private final int size;
    private final String characters;

    @Getter
    protected int seed = 1;
    protected int code = 1;
    protected int last = 1;

    @Getter
    private String result;

    public static CodeGenerator create(String seedCode, int size) {
        return new CodeGenerator(CHARACTERS, seedCode, size);
    }

    public CodeGenerator(String characters, String seedCode, int size) {
        this.size = size;
        this.characters = characters;
        final byte[] bytes = seedCode.getBytes();
        for (int i = 1; i < bytes.length + 1; i++)
            seed = (seed + (i * ((((Byte.toUnsignedInt(bytes[i -1]) + 1) * i) / bytes.length) * size))) % Integer.MAX_VALUE;
        update();
    }

    public void update() {
        code = Math.abs((code + (last * size) * seed)) % Integer.MAX_VALUE;
        int value = 1;
        final StringBuilder result = new StringBuilder();
        final byte[] bytes = Integer.toString(code).getBytes();
        for (int i = 1; i < size + 1; i++) {
            final byte bit = bytes[(i - 1) % bytes.length];
            final int length = code * bytes.length * Byte.toUnsignedInt(bit);
            value = Math.abs((value + (length * last) * i ^ result.length()) / (size + i)) % Integer.MAX_VALUE;
            final int index = value % characters.length();
            result.append(characters.charAt(index));
        }
        last++;
        this.result = result.toString();
    }

    public void setParameters(String parameters) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(parameters))) {
            final DataInputStream dataStream = new DataInputStream(inputStream);
            seed = dataStream.readInt();
            code = dataStream.readInt();
            last = dataStream.readInt();
            result = dataStream.readUTF();
        } catch (Exception ignored) {}
    }

    public String getParameters() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            final DataOutputStream dataStream = new DataOutputStream(outputStream);
            dataStream.writeInt(seed);
            dataStream.writeInt(code);
            dataStream.writeInt(last);
            dataStream.writeUTF(result);
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception ignored) {
            return null;
        }
    }

}
