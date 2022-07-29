package Tester;

import java.security.KeyException;
import java.util.ArrayList;

public class Scene {
    private final ArrayList<ArrayList<Object>> objectMap;

    public Scene() {
        objectMap = new ArrayList<>();
    }

    public boolean keyInScene(String objectKey) {
        for (ArrayList<Object> pair : objectMap) {
            if (pair.get(0).equals(objectKey)) {
                return true;
            }
        }
        return false;
    }

    public Object getObject(String objectKey) throws KeyException {
        for (ArrayList<Object> pair : objectMap) {
            if (pair.get(0).equals(objectKey)) {
                return pair.get(1);
            }
        }
        throw new KeyException();
    }

    public String keyFromObject(Object object) {
        for (ArrayList<Object> pair : objectMap) {
            if (pair.get(1) == object) {
                return (String) pair.get(0);
            }
        }
        return "Object not found in scene!";
    }

    public void addObject(String key, Object object) throws KeyException {
        if (keyInScene(key))
            throw new KeyException();
        ArrayList<Object> temp = new ArrayList<>();
        temp.add(key);
        temp.add(object);
        objectMap.add(temp);
    }
}