package project;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class FilesWalker extends SimpleFileVisitor<Path> {
    static ArrayList<String> techList = new ArrayList<>();
    static ArrayList<String> qualList = new ArrayList<>();
    static ArrayList<String> commList = new ArrayList<>();

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

        if (file.getParent().toString().contains("Техника")) {
            techList.add(file.toAbsolutePath().toString());
        }
        if (file.getParent().toString().contains("Квалификация")) {
            qualList.add(file.toAbsolutePath().toString());
        }
        if (file.getParent().toString().contains("Коммерческая")) {
            commList.add(file.toAbsolutePath().toString());
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

    public static void collectLists(String dirPath) throws IOException {
        Path path = Paths.get(dirPath);
        Files.walkFileTree(path, new FilesWalker());
    }

    public static void main(String[] args) throws IOException {
        collectLists("/media/v/Samsung USB1/ТЭКТОРГ/04.21 РН10402622 ЗАПРОС ЦЕН АЮ");
        FilesWalker.qualList.forEach(System.out::println);
        System.out.println("QUAL SIZE = " + FilesWalker.qualList.size() + "----------------------------------------------------------------------");
        System.out.println();
        FilesWalker.techList.forEach(System.out::println);
        System.out.println("TECH SIZE = " + FilesWalker.techList.size() + "----------------------------------------------------------------------");
        System.out.println();
        FilesWalker.commList.forEach(System.out::println);
        System.out.println("COMM SIZE = " + FilesWalker.commList.size() + "----------------------------------------------------------------------");
        System.out.println("TOTAL SIZE = " + (FilesWalker.qualList.size() + FilesWalker.techList.size() + FilesWalker.commList.size()));
    }
}

