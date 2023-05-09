package configs;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;

public class BaseTest implements Constants{

	public static void setup() {
		RestAssured.baseURI = APP_BASE_URL;
		RestAssured.basePath = APP_BASE_PATH;
		
		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.setContentType(APP_CONTENT_TYPE);
		RestAssured.requestSpecification = reqBuilder.build();
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}
}
