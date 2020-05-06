package highesLikedTweetJSON;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONexample {

	public static void main(String[] args) throws org.json.simple.parser.ParseException {
		
		//Create an instance of JSONParser to parser the file.
		JSONParser jsonParser = new JSONParser();
		try {
			
			//Create a instance of FileReader to read the file.
			FileReader fr = new FileReader("F:\\ECLIPSE WORKSPACE\\TwitterProject\\src\\test\\resources\\sampleJSON.json");
			JSONObject jObj = (JSONObject) jsonParser.parse(fr);
			System.out.println(jObj);
			
			JSONObject quiz = ((JSONObject)  jObj.get("quiz");
			JSONObject sport = (JSONObject)  quiz.get("sport");
			JSONObject q1 =  (JSONObject) sport.get("q1");
			
			//Printing the question in sport.
			String sQuestion =  (String) q1.get("question");
			System.out.println(sQuestion);
			
			//Converting the options to an array.
			JSONArray sportq1Options = (JSONArray) q1.get("options");
			sportq1Options.forEach( option -> parseOptions((String) option));
			
//			//For every subjects in the file.
//			subjects.forEach( sub -> parseSubject( (JSONObject) sub));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void parseOptions(String option) {
		System.out.println(option);
	}
	
}
