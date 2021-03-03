package Project;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class FilesWalkerBase extends SimpleFileVisitor<Path> {
    static ArrayList<String> techList = new ArrayList<>();
    static ArrayList<String> qualList = new ArrayList<>();
    static ArrayList<String> commList = new ArrayList<>();
    static int techSize = 0;
    static int qualSize = 0;
    static int commSize = 0;

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        if (dir.getFileName().toString().equals("Документация")) {
            return FileVisitResult.SKIP_SUBTREE;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

        if (file.getParent().toString().contains("Техника")) {
            techList.add(file.toAbsolutePath().toString());
            techSize++;
        }
        if (file.getParent().toString().contains("Квалификация")) {
            qualList.add(file.toAbsolutePath().toString());
            qualSize++;
        }
        if (file.getParent().toString().contains("Коммерческая")) {
            commList.add(file.toAbsolutePath().toString());
            commSize++;
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    public static void Walker(String dirPath) throws IOException {
        Path path = Paths.get(dirPath);
        Files.walkFileTree(path, new FilesWalkerBase());
    }

    public static void main(String[] args) throws IOException {
        Walker("");
        FilesWalkerBase.qualList.forEach(System.out::println);
        System.out.println("qualList = " + FilesWalkerBase.qualList.size() + "-----------------------------------");
        FilesWalkerBase.techList.forEach(System.out::println);
        System.out.println("techList = " + FilesWalkerBase.techList.size() + "-----------------------------------");
        FilesWalkerBase.commList.forEach(System.out::println);
        System.out.println("commList = " + FilesWalkerBase.commList.size() + "-----------------------------------");
    }
}

