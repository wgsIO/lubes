package com.github.walkgs.lubes.utilities;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class AdjustableGZIPOutputStream extends GZIPOutputStream {


    public AdjustableGZIPOutputStream(OutputStream out, int size, int level) throws IOException {
        super(out, size);
        def.setLevel(level);
    }

    public AdjustableGZIPOutputStream(OutputStream out, int size, boolean syncFlush, int level) throws IOException {
        super(out, size, syncFlush);
        def.setLevel(level);
    }

    public AdjustableGZIPOutputStream(OutputStream out, int level) throws IOException {
        super(out);
        def.setLevel(level);
    }

    public AdjustableGZIPOutputStream(OutputStream out, boolean syncFlush, int level) throws IOException {
        super(out, syncFlush);
        def.setLevel(level);
    }
}
