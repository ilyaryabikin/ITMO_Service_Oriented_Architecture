package se.ifmo.soa.lab1.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CachedDataHttpServletRequest extends HttpServletRequestWrapper {

  private final byte[] data;

  public CachedDataHttpServletRequest(final HttpServletRequest request) throws IOException {
    super(request);
    this.data = readBytes(request.getInputStream());
  }

  @Override
  public BufferedReader getReader() {
    return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data)));
  }

  @Override
  public ServletInputStream getInputStream() {
    return new CachedDataServletInputStream(data);
  }

  private byte[] readBytes(final InputStream inputStream) throws IOException {
    final int bufferSize = 4096;
    final ByteArrayOutputStream baos = new ByteArrayOutputStream(bufferSize);

    byte[] buffer = new byte[bufferSize];
    while (inputStream.read(buffer, 0, buffer.length) != -1) {
      baos.write(buffer);
    }

    baos.flush();

    return baos.toByteArray();
  }

  private static class CachedDataServletInputStream extends ServletInputStream {

    private final ByteArrayInputStream inputStream;

    public CachedDataServletInputStream(final byte[] data) {
      this.inputStream = new ByteArrayInputStream(data);
    }

    @Override
    public boolean isFinished() {
      return inputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
      return true;
    }

    @Override
    public void setReadListener(final ReadListener readListener) {
      throw new UnsupportedOperationException("Can't set ReadListener - not implemented");
    }

    @Override
    public int read() {
      return inputStream.read();
    }
  }
}
