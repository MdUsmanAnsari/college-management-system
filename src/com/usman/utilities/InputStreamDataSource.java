package com.usman.utilities;

import javax.activation.DataSource;
import javax.activation.MimetypesFileTypeMap;
import java.io.*;

public class InputStreamDataSource implements DataSource {

    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private final String name;

    public InputStreamDataSource(InputStream inputStream, String name) {
        this.name = name;
        try {
            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getContentType() {
        return new MimetypesFileTypeMap().getContentType(name);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(buffer.toByteArray());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new IOException("Read-only data");
    }

}