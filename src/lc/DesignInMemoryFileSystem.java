package lc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wshung on 2018/12/19.
 */
public class DesignInMemoryFileSystem {
    public static void main(String[] args) {
        DesignInMemoryFileSystem fs = new DesignInMemoryFileSystem();
        System.out.println(fs.ls("/"));
        fs.mkdir("/a/b/c/");
        fs.addContentToFile("/a/b/c/e", "world");
        fs.addContentToFile("/a/b/c/d", "hello");
        System.out.println(fs.ls("/"));
        System.out.println(fs.ls("/a/b/c"));
        System.out.println(fs.readContentFromFile("/a/b/c/d"));
        System.out.println(fs.readContentFromFile("/a/b/c/e"));
        System.out.println(fs.ls("/a/b/c/d"));
        fs.mkdir("/a/c/");
        fs.addContentToFile("/a/c/c", "c file");
        System.out.println(fs.find("/a/", "c"));

    }
    public class Dir {
        public Map<String, Dir> dirs = new HashMap<>();
        public Map<String, String> files = new HashMap<>();
    }
    private Dir root = new Dir();
    private void findHelper(Dir cur, StringBuilder tmp, String name, List<String> res) {
        for (String file : cur.files.keySet()) {
            tmp.append("/" + file);
            if (file.equals(name))
                res.add(tmp.toString());
            tmp.setLength(tmp.length() - 1 - file.length());
        }
        for (String dir :cur.dirs.keySet()) {
            tmp.append("/" + dir);
            if (dir.equals(name)) {
                res.add(tmp.toString());
            }
            findHelper(cur.dirs.get(dir), tmp, name, res);
            tmp.setLength(tmp.length() - 1 - dir.length());
        }
    }
    public List<String> find(String path, String name) {
        Dir cur = root;
        String[] parts = path.split("/");
        StringBuilder prefix = new StringBuilder("/");
        for (int i = 1; i < parts.length; i++) {
            prefix.append(parts[i]);
            cur = cur.dirs.get(parts[i]);
        }
        List<String> res = new ArrayList<>();
        findHelper(cur, prefix, name, res);
        return res;
    }
    public String readContentFromFile(String path) {
        Dir cur = root;
        String[] parts = path.split("/");
        int n = parts.length;
        for (int i = 1; i < n - 1; i++)
            cur = cur.dirs.get(parts[i]);
        return cur.files.get(parts[n-1]);
    }
    public void addContentToFile(String path, String content) {
        Dir cur = root;
        String[] parts = path.split("/");
        int n = parts.length;
        for (int i = 1; i < n - 1; i++) {
            cur.dirs.putIfAbsent(parts[i], new Dir());
            cur = cur.dirs.get(parts[i]);
        }
        System.out.println("add file:" + parts[n-1]);
        cur.files.put(parts[n-1], content);
    }
    public void mkdir(String path) {
        Dir cur = root;
        if (!"/".equals(path)) {
            String[] parts = path.split("/");
            for (int i = 1; i < parts.length; i++) {
                cur.dirs.putIfAbsent(parts[i], new Dir());
                cur = cur.dirs.get(parts[i]);
            }
        }
    }
    public List<String> ls(String path) {
        Dir cur = root;
        List<String> res = new ArrayList<>();
        if (!path.equals("/")) {
            String[] parts = path.split("/");
            int n = parts.length;
            for (int i = 1; i < n - 1; i++) {
                cur = cur.dirs.get(parts[i]);
            }
            if (cur.files.containsKey(parts[n-1])) {
                res.add(path);
                return res;
            } else {
                cur = cur.dirs.get(parts[n-1]);
            }
        }

        res.addAll(cur.dirs.keySet());
        res.addAll(cur.files.keySet());
        Collections.sort(res);
        return res;
    }

    /* public static class Dir {
        public Map<String, Dir> dirs = new HashMap<>();
        public Map<String, String> files = new HashMap<>();
    }
    public static class FileSystem {

        public Dir root = new Dir();
        public void mkdir(String path) {
            String[] dirs = path.split("/");
            Dir cur = root;
            for (int i = 1; i < dirs.length; i++) {
                cur.dirs.putIfAbsent(dirs[i], new Dir());
                cur = cur.dirs.get(dirs[i]);
            }
        }
        public String readContentFromFile(String filePath) {
            String[] dirs = filePath.split("/");
            Dir cur  = root;
            for (int i = 1; i < dirs.length - 1; i++)
                cur = cur.dirs.get(dirs[i]);
            return cur.files.get(dirs[dirs.length-1]);
        }

        public void addContentToFile(String filePath, String content) {
            String[] dirs = filePath.split("/");
            Dir cur = root;
            for (int i = 1; i < dirs.length - 1; i++)
                cur = cur.dirs.get(dirs[i]);
            cur.files.put(dirs[dirs.length-1], cur.files.getOrDefault(dirs[dirs.length-1], "") + content);
        }

        public List<String> ls(String path) {
            List<String> res = new ArrayList<>();
            Dir cur = root;
            if (!path.equals("/")) {
                String[] dirs = path.split("/");
                for (int i = 1; i < dirs.length - 1; i++)
                    cur = cur.dirs.get(dirs[i]);
                if (cur.files.containsKey(dirs[dirs.length-1])) {
                    res.add(dirs[dirs.length-1]);
                    return res;
                } else {
                    cur = cur.dirs.get(dirs[dirs.length-1]);
                }
            }
            res.addAll(cur.dirs.keySet());
            res.addAll(cur.files.keySet());
            Collections.sort(res);
            return res;
        }
    }*/
}
