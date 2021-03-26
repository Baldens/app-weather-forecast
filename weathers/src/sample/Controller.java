package sample;

import javafx.scene.text.Text;
import org.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import netscape.javascript.JSObject;
import org.json.JSONObject;

public class Controller {

    @FXML
    private TextField setInputText;

    @FXML
    private Text temp_info;

    @FXML
    private Text speed_info;

    @FXML
    private Text sky_info;

    @FXML
    private Text atmos_info;

    @FXML
    private Button getCity;

    @FXML
    void initialize() {
        getCity.setOnAction(event->{
            String getCityWithInputText = setInputText.getText().trim();
            String outPut = getUrl("http://api.openweathermap.org/data/2.5/weather?q=" + getCityWithInputText +
                    "&appid=11c0d3dc6093f7442898ee49d2430d20&units=metric");
            if(!outPut.isEmpty()){
                JSONObject obj = new JSONObject(outPut);
                JSONArray weather = (JSONArray) obj.get("weather");
                String skyInfo = "";
                Iterator<Object> iterator = weather.iterator();
                while (iterator.hasNext()) {
                    JSONObject objVn = (JSONObject) iterator.next();
                    skyInfo = (String) objVn.get("description");
                }
                temp_info.setText("Temperature: " + obj.getJSONObject("main").getDouble("temp"));
                speed_info.setText("Speed: " + obj.getJSONObject("wind").getDouble("speed"));
                sky_info.setText("Sky: " + skyInfo);
                atmos_info.setText("Atmospheric pressure: " + obj.getJSONObject("main").getDouble("pressure"));
                System.out.println(outPut);
            }
        });
    }
    private static String getUrl(String urlAdress){
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine())!=null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }catch (Exception e){
            System.out.println("Не найден город");
        }
        return content.toString();
    }
}
