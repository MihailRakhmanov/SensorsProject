import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Client {
    public static void main(String[] args) {
        final String sensorName = "Sensor2";

        registerSensor(sensorName);

        Random random = new Random();

        double minTemperature = 0.0;
        double maxTemperature = 35.0;
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
            sendMeasurement(random.nextDouble() * maxTemperature,
                    random.nextBoolean(), sensorName);
        }

    }
    private static void registerSensor(String sensorName){
        final String url = "http://localhost:8080/sensors/registration";

        Map<String, Object> jsonToSend = new HashMap<>();
        jsonToSend.put("name", sensorName);

        makePostRequest(url, jsonToSend);
    }

    private static void sendMeasurement(double value, boolean raining, String sensorName){
        final String url = "http://localhost:8080/measurements/add";

        Map<String, Object> jsonToSend = new HashMap<>();
        jsonToSend.put("value", value);
        jsonToSend.put("raining", raining);
        jsonToSend.put("sensor", Map.of("name", sensorName));

        makePostRequest(url, jsonToSend);
    }

    private static void makePostRequest(String url, Map<String, Object> jsonToSend){
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(jsonToSend, headers);

        try {
            restTemplate.postForObject(url, request, String.class);

            System.out.println("Измерение успешно отправлено на сервер");
        } catch (HttpClientErrorException e){
            System.out.println("Ошибка:" + e.getMessage());
        }
    }
}
