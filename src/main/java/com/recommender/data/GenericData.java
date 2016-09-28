package com.recommender.data;

import com.recommender.model.Purchase;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class GenericData implements Data {

    private final  Map<Integer, Map<Integer, Purchase>> userMap;
    private final Map<Integer, Map<Integer, Purchase>> itemMap;

    public GenericData(){
        userMap = new HashMap<>();
        itemMap = new HashMap<>();
    }

    @Override
    public void loadData(URI filePath) throws IOException, URISyntaxException {
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(line -> {
                String[] split = line.split("::");
                int userId = Integer.parseInt(split[0].trim());
                int itemId = Integer.parseInt(split[1].trim());
                int rating = Integer.parseInt(split[2].trim());
                double time = Double.parseDouble(split[3]);
                userMap.putIfAbsent(userId, new HashMap<>());
                final Purchase purchase = new Purchase(rating, time);
                userMap.get(userId).put(itemId,purchase );
                itemMap.putIfAbsent(itemId, new HashMap<>());
                itemMap.get(itemId).put(userId,purchase );
            });
        }
    }

    public Map<Integer, Purchase> getUser(int userId) {
        return userMap.get(userId);
    }

    public Map<Integer, Purchase> getItem(int itemId) {
        return itemMap.get(itemId);
    }

    public Map<Integer, Map<Integer, Purchase>> getItemMap() {
        return itemMap;
    }
}
