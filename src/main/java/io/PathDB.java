package io;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class PathDB {
    private static List<String> paths;
    private static Set<String> pathSet;

    static {
        paths = new ArrayList<>();
        pathSet = new HashSet<>();
    }

    private PathDB() { }

    static String getPath(int idx) {
        return paths.get(idx);
    }

    static boolean containsPath(String path) {
        return pathSet.contains(path);
    }

    static boolean addPath(String str) {
        if (pathSet.contains(str)) {
            return false;
        } else {
            paths.add(str);
            pathSet.add(str);
            return true;
        }

    }

    static boolean delete(int idx) {
        if (idx < 0 || idx >= paths.size()) {
            return false;
        } else {
            pathSet.remove(paths.remove(idx));
            return true;
        }

    }

    static int getSize() {
        return paths.size();
    }

}
