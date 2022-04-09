package com.bhut.api.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bhut.api.dto.CarDTO;
import com.bhut.api.dto.CarLogDTO;
import com.bhut.api.models.Car;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@RestController
@RequestMapping("/api")
public class BhutController {

	// External End-point
	private static String url = "http://api-test.bhut.com.br:3000/api/cars";

	// Instance of class Logger
	private Logger logger = LogManager.getLogger(BhutController.class);

	// Creating the ObjectMapper object
	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * This method returns a list of Objects Car from an external API
	 * 
	 * @return
	 */
	@GetMapping(value = "/listCars")
	public List<Object> getCars() {

		RestTemplate restTemplate = new RestTemplate();
		Object[] list = restTemplate.getForObject(url, Object[].class);

		return Arrays.asList(list);
	}

	/**
	 * This method saves an instance of Object Car into an
	 * 
	 * @param car
	 * @return
	 */
	@PostMapping(value = "/createCar")
	public String setCar(@RequestBody Car car) {

		String response = null;

		try {

			// Converting the Object to JSONString
			String data = mapper.writeValueAsString(car);

			URL url1 = new URL(url);
			HttpURLConnection http = (HttpURLConnection) url1.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("Accept", "application/json");
			http.setRequestProperty("Content-Type", "application/json");

			byte[] out = data.getBytes(StandardCharsets.UTF_8);

			OutputStream stream = http.getOutputStream();
			stream.write(out);

			BufferedReader br = null;

			// Reading responses from external API
			if (http.getResponseCode() == 200) {
				br = new BufferedReader(new InputStreamReader(http.getInputStream()));
				String strCurrentLine;
				while ((strCurrentLine = br.readLine()) != null) {
					response = strCurrentLine;

					// Calling method to save all the logs **************************
					saveLogs(response);
				}
			} else {
				br = new BufferedReader(new InputStreamReader(http.getErrorStream()));
				String strCurrentLine;
				while ((strCurrentLine = br.readLine()) != null) {
					response = strCurrentLine;
				}
			}

			http.disconnect();

		} catch (Exception e) {
			logger.error(e);
			return e.getMessage();
		}
		return response;
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/logs")
	public List<Object> getLogs() throws IOException {

		ArrayList<Object> list = new ArrayList<>();

		try {
			// The file to be opened for reading
			FileInputStream fileInputStream = new FileInputStream("logs/bhut-logger.log");
			Scanner sc = new Scanner(fileInputStream); // file to be scanned
			// returns true if there is another line to read
			while (sc.hasNextLine()) {
				var line = sc.nextLine(); // returns the line that was skipped
				Object car = mapper.readValue(line, Object.class);
				list.add(car);
			}
			sc.close(); // closes the scanner
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		return list;
	}

	/**
	 * This method saves the response into a file, through Logger, related to the
	 * new cars saved into the External API.
	 * 
	 */
	public void saveLogs(String response) {

		try {

			// Converting the JSONString to Object
			CarDTO carDTO = mapper.readValue(response, CarDTO.class);

			// Instance of Object CarLogDTO
			CarLogDTO carLog = new CarLogDTO(0, null, null);
			carLog.setCar_id(carDTO.get_id());
			carLog.setData_hora(LocalDateTime.now().toString());

			// Converting the JSONString to Object
			String jsonString = mapper.writeValueAsString(carLog);

			// Saving JSONString to Log file
			logger.info(jsonString);

		} catch (Exception e) {
			logger.error(e);
		}

	}

}