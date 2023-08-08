package se.systementor.javawebservicewarmupfacit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.systementor.javawebservicewarmupfacit.models.WeatherPrediction;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SpringBootApplication
public class JavaWebserviceWarmUpFacitApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(JavaWebserviceWarmUpFacitApplication.class, args);
    }

    private List<WeatherPrediction> predictions = new ArrayList<WeatherPrediction>();

    @Override
    public void run(String... args) throws Exception {
        var scan = new Scanner(System.in);
        while(true){
            showHeaderMenu();
            System.out.print("Action:");
            int sel = Integer.parseInt(scan.nextLine()) ;
            if(sel == 1) listPredictions(scan);
            else if(sel == 2) createNewPrediction(scan);
            else if(sel == 3) updatePrediction(scan);
            else if(sel == 9) break;
        }
    }


    private void createNewPrediction(Scanner scan) {
        System.out.println("*** CREATE PREDICTION ***");

        System.out.printf("Ange vilken dag, ex %s:", new SimpleDateFormat("yyyyMMdd").format(new Date()));
        int dag = Integer.parseInt(scan.nextLine()) ;

        System.out.print("Hour:");
        int hour = Integer.parseInt ( scan.nextLine() );

        System.out.print("Temperature:");
        float temp = Float.parseFloat ( scan.nextLine() );

        var weatherPrediction = new WeatherPrediction(UUID.randomUUID());
        weatherPrediction.setDate(dag);
        weatherPrediction.setHour(hour);
        weatherPrediction.setTemperature(temp);
        predictions.add(weatherPrediction);
        predictions = predictions.stream().sorted((a,b)->b.SortOrder().compareTo(a.SortOrder()))
                .collect(Collectors.toList());
    }

    private void updatePrediction(Scanner scan) {
        int num = 1;
        for(var prediction : predictions){
            System.out.printf("%d) %d  Kl %d:00  Temp:%f   %n",num, prediction.getDate(), prediction.getHour(), prediction.getTemperature() );
            num++;
        }
        System.out.print("Select a row number:");
        int sel = Integer.parseInt ( scan.nextLine() );
        var t = predictions.get(sel-1);

        System.out.print("New temperature:");
        int temp = Integer.parseInt ( scan.nextLine() );

        t.setTemperature(temp);
    }

    private void listPredictions(Scanner scan) {
        for(var prediction : predictions){
            System.out.printf("%d  Kl %d:00  Temp:%f  %n", prediction.getDate(), prediction.getHour(), prediction.getTemperature() );
        }

    }

    private void showHeaderMenu() {
        System.out.println("1. List all predictions");
        System.out.println("2. Create prediction");
        System.out.println("3. Update prediction");
        System.out.println("9. Exit");
    }

}
