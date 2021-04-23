package project;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class FilesWalkerForContracts extends SimpleFileVisitor<Path> {
    private static final String dirPath = "C:\\Users\\v\\Downloads\\chrome";
    private static final Map<Long, String> map = new TreeMap<>();

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        //I need to sort the uploaded files in the download order.
        //Therefore use lastModifiedTime like key and "autosort" in TreeMap
        map.put(attrs.lastModifiedTime().toMillis(), file.toAbsolutePath().toString());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    public static Map<Long, String> collectContracts() throws IOException {
        Path path = Paths.get(dirPath);
        Files.walkFileTree(path, new FilesWalkerForContracts());
        return map;
    }

    public static void main(String[] args) throws IOException {
        Map<Long, String> map = collectContracts();
        map.forEach((k, v) -> System.out.println(v));
        System.out.println(map.size());
    }
}

