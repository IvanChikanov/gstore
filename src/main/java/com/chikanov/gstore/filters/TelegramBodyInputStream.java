package com.chikanov.gstore.filters;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TelegramBodyInputStream extends ServletInputStream {
    private InputStream bodyStream;

    public TelegramBodyInputStream(byte[] body){
        this.bodyStream = new ByteArrayInputStream(body);
    }
    @Override
    public boolean isFinished() {
        try {
            return bodyStream.available() == 0;
        }catch (IOException ex){
            return true;
        }

    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {

    }

    @Override
    public int read() throws IOException {
        return bodyStream.read();
    }
}
