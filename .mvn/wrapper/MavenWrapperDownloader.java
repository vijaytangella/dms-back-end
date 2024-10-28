package org.apache.maven.wrapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

public class MavenWrapperDownloader {
    private static final String WRAPPER_URL = "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar";

    public static void main(String[] args) {
        try {
            String wrapperJarPath = ".mvn/wrapper/maven-wrapper.jar";
            File wrapperJarFile = new File(wrapperJarPath);

            if (!wrapperJarFile.exists()) {
                downloadFile(WRAPPER_URL, wrapperJarFile);
                System.out.println("Maven Wrapper JAR downloaded to " + wrapperJarPath);
            } else {
                System.out.println("Maven Wrapper JAR already exists at " + wrapperJarPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadFile(String fileURL, File destination) throws IOException {
        HttpURLConnection httpConn = (HttpURLConnection) new URL(fileURL).openConnection();
        httpConn.setRequestMethod("GET");
        httpConn.setDoOutput(true);

        try (InputStream in = httpConn.getInputStream();
             OutputStream out = Files.newOutputStream(destination.toPath())) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
