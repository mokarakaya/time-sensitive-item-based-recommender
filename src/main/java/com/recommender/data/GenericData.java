package com.recommender.data;

import com.recommender.model.Purchase;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class GenericData implements Data {

    private final  Map<Integer, Map<Integer, Purchase>> userMap;
    private final Map<Integer, List<Integer>> itemMap;

    public GenericData(){
        userMap = new HashMap<>();
        itemMap = new HashMap<>();
    }

    @Override
    public void loadData(String filePath) throws IOException, URISyntaxException {
        try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource(filePath).toURI()))) {
            stream.forEach(line -> {
                String[] split = line.split("::");
                int userId = Integer.parseInt(split[0].trim());
                int itemId = Integer.parseInt(split[1].trim());
                int rating = Integer.parseInt(split[2].trim());
                double time = Double.parseDouble(split[3]);
                userMap.putIfAbsent(userId, new HashMap<>());
                userMap.get(userId).put(itemId, new Purchase(rating, time));
                itemMap.putIfAbsent(itemId, new ArrayList<>());
                itemMap.get(itemId).add(userId);
            });
        }
    }

    public Map<Integer, Purchase> getUser(int userId) {
        return userMap.get(userId);
    }

    public List<Integer> getItem(int itemId) {
        return itemMap.get(itemId);
    }

    public Map<Integer, List<Integer>> getItemMap() {
        return itemMap;
    }
}
