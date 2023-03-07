package com.github.walkgs.lubes.utilities.seekers;

import sun.net.www.protocol.file.FileURLConnection;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Classes {

    public static Class<?> getClass(String packageName, String name) throws ClassNotFoundException {
        try {
            return Class.forName(packageName + '.' + name);
        } catch (final NoClassDefFoundError ignore) {}
        return null;
    }

    public static ArrayList<Class<?>> getClassesForPackage(ClassLoader classLoader, String packageName) throws ClassNotFoundException {
        final ArrayList<Class<?>> classes = new ArrayList<>();
        try {
            if (classLoader == null)
                throw new ClassNotFoundException("Can't get class loader.");
            final Enumeration<URL> resources = classLoader.getResources(packageName.replace('.', '/'));
            for (URL url; resources.hasMoreElements() && ((url = resources.nextElement()) != null); ) {
                try {
                    final URLConnection connection = url.openConnection();
                    if (connection instanceof JarURLConnection)
                        collectClasses((JarURLConnection) connection, packageName, classes);
                    else if (connection instanceof FileURLConnection)
                        try {
                            collectClasses(new File(URLDecoder.decode(url.getPath(), "UTF-8")), packageName, classes);
                        } catch (final UnsupportedEncodingException ex) {
                            throw new ClassNotFoundException(packageName + " does not appear to be a valid package (Unsupported encoding)", ex);
                        }
                    else
                        throw new ClassNotFoundException(packageName + " (" + url.getPath() + ") does not appear to be a valid package");
                } catch (final IOException ex) {
                    throw new ClassNotFoundException("IOException was thrown when trying to get all resources for " + packageName, ex);
                }
            }
        } catch (final NullPointerException ex) {
            throw new ClassNotFoundException(packageName + " does not appear to be a valid package (Null pointer exception)", ex);
        } catch (final IOException ex) {
            throw new ClassNotFoundException("IOException was thrown when trying to get all resources for " + packageName, ex);
        }
        return classes;
    }

    public static void collectClasses(File directory, String packageName, ArrayList<Class<?>> classes) throws ClassNotFoundException {
        File tempDirectory;
        if (directory.exists() && directory.isDirectory()) {
            final String[] files = directory.list();
            for (final String file : files)
                if (file.endsWith(".class"))
                    try { classes.add(Class.forName(packageName + '.' + file.substring(0, file.length() - 6))); } catch (final NoClassDefFoundError ignore) {}
                else if ((tempDirectory = new File(directory, file)).isDirectory())
                    collectClasses(tempDirectory, packageName + "." + file, classes);
        }
    }

    public static void collectClasses(JarURLConnection connection, String packageName, ArrayList<Class<?>> classes) throws ClassNotFoundException, IOException {
        final JarFile jar = connection.getJarFile();
        final Enumeration<JarEntry> entries = jar.entries();
        for (JarEntry entry; entries.hasMoreElements() && ((entry = entries.nextElement()) != null); ) {
            String name = entry.getName();
            if (!name.contains(".class"))
                continue;
            name = name.substring(0, name.length() - 6).replace('/', '.');
            if (!name.contains(packageName))
                continue;
            classes.add(Class.forName(name));
        }
    }

}
