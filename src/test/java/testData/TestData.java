package testData;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

public class TestData {

    private static final Map<String, Object> yamlData;
    private static final String dataFilePath = "testData/testData.yaml";

    static {
        try (InputStream inputStream = TestData.class.getClassLoader().getResourceAsStream(dataFilePath)) {
            if (inputStream == null) {
                throw new RuntimeException("YAML file not found in resources path: " + dataFilePath);
            }
            Map<String, Object> loadedData = new Yaml().load(inputStream);
            yamlData = loadedData != null ? Collections.unmodifiableMap(loadedData) : Collections.emptyMap();
        } catch (Exception e) {
            throw new RuntimeException("Failure at loading Test Data YAML File: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public static String get(String path) {
        if (yamlData.isEmpty()) {
            throw new IllegalStateException("Test data YAML file is empty or was not loaded correctly.");
        }

        Map<String, Object> currentMap = yamlData;
        String[] keys = path.split("\\.");

        for (int i = 0; i < keys.length - 1; i++) {
            Object obj = currentMap.get(keys[i]);
            if (obj instanceof Map) {
                currentMap = (Map<String, Object>) obj;
            } else {
                throw new IllegalArgumentException("Invalid test data key path: [" + path + "]. Failed at key node: [" + keys[i] + "]");
            }
        }

        Object value = currentMap.get(keys[keys.length - 1]);
        if (value == null) {
            throw new IllegalArgumentException("Test data key not found: [" + path + "] in file " + dataFilePath);
        }

        return value.toString();
    }
}