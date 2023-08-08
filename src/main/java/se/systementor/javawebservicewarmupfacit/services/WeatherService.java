package se.systementor.javawebservicewarmupfacit.services;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;
import se.systementor.javawebservicewarmupfacit.models.WeatherPrediction;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class WeatherService {
    private static List<WeatherPrediction> allPredictions;

    public WeatherService(){
        if(allPredictions == null) {
            try {
                allPredictions = readFromFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private List<WeatherPrediction> readFromFile() throws IOException {
        if(!Files.exists(Path.of("predictions.json"))) return new ArrayList<WeatherPrediction>();
        ObjectMapper objectMapper = getObjectMapper();
        var jsonStr = Files.readString(Path.of("predictions.json"));
        return  new ArrayList(Arrays.asList(objectMapper.readValue(jsonStr, WeatherPrediction[].class ) ));
    }


    private void writeAllToFile(List<WeatherPrediction> weatherPredictions) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);


        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, weatherPredictions);

        Files.writeString(Path.of("predictions.json"), stringWriter.toString());

    }


    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.registerModule(new JavaTimeModule());
        /*
        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-jsr310</artifactId>
        </dependency>
        */
        return mapper;
    }



//    private List<WeatherPrediction> readFromFile() throws IOException{
//        if(!Files.exists(Path.of("predictions.json"))) return new ArrayList<WeatherPrediction>();
//
//        var fileContent = Files.readString(Path.of("predictions.json"));
//        var parser = new JsonFactory().createParser(new StringReader(fileContent));
//        while (parser.nextToken() != JsonToken.END_OBJECT) {
//            //...
//        }
//    }
//    private void saveToFile(List<WeatherPrediction> predictions) throws IOException {
//        var jsonFactory = new JsonFactory();
//        var generator = jsonFactory.createGenerator(new File("predictions.json"), JsonEncoding.UTF8);
//        generator.writeStartArray();
//        for(var prediction:predictions){
//            generator.writeStartObject();
//            generator.writeNumberField("date", prediction.getDate());
//            generator.writeNumberField("hour", prediction.getHour());
//            generator.writeNumberField("temperature",prediction.getTemperature());
//            generator.writeStringField("id", prediction.getId().toString());
//            generator.writeEndObject();
//        }
//        generator.writeEndArray();
//        generator.flush();
//    }


}
