package Project;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class FilesWalkerContract extends SimpleFileVisitor<Path> {
    static int fileSize = 0;
    static SortedMap<Long, String> filesMap = new TreeMap<>();

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        filesMap.put(attrs.lastModifiedTime().toMillis(), file.toAbsolutePath().toString());
        fileSize++;
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

    public static void Walker(String dirPath) throws IOException {
        Path path = Paths.get(dirPath);
        Files.walkFileTree(path, new FilesWalkerContract());
    }
}

