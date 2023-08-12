package se.systementor.javawebservicewarmupfacit.services;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

   public List<WeatherPrediction> getAll(){
        return allPredictions;
   }
    private List<WeatherPrediction> readFromFile() throws IOException {
        if(!Files.exists(Path.of("predictions.xml"))) return new ArrayList<WeatherPrediction>();
        ObjectMapper objectMapper = getObjectMapper();
        var jsonStr = Files.readString(Path.of("predictions.xml"));
        return  new ArrayList(Arrays.asList(objectMapper.readValue(jsonStr, WeatherPrediction[].class ) ));
    }


    private void writeAllToFile(List<WeatherPrediction> weatherPredictions) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);


        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, weatherPredictions);

        Files.writeString(Path.of("predictions.xml"), stringWriter.toString());

    }


    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new XmlMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    public void add(WeatherPrediction weatherPrediction) throws IOException {
        allPredictions.add(weatherPrediction);
        this.writeAllToFile(allPredictions);
    }

    public void update(WeatherPrediction t) throws IOException {
        var existing = allPredictions.stream().filter(c->c.getId().equals(t.getId())).findFirst().orElseThrow();
        existing.setTemperature(t.getTemperature());
        existing.setDate(t.getDate());
        existing.setHour(t.getHour());
        this.writeAllToFile(allPredictions);
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
