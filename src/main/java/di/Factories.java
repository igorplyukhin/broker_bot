package di;

import repository.Repository;
import repository.TestRepository;

import java.util.HashMap;

public class Factories {
    private static final HashMap<String, Repository> instances = new HashMap<>();

    public static Repository getRepository(String name){
        if (instances.containsKey(name))
            return instances.get(name);
        var rep = new TestRepository();
        instances.put(name, rep);
        return rep;
    }
}
