package ru.job4j.cinema.model;

import java.util.Objects;

public class File {
    private final int id;
    private final String name;
    private final String path;

    public File(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        File file = (File) o;
        return id == file.id && Objects.equals(path, file.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, path);
    }

    @Override
    public String toString() {
        return "File{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", path='" + path + '\''
                + '}';
    }
}