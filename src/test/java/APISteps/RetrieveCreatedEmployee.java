package APISteps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import org.junit.Assert;
import utils.APIConstants;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static APISteps.CreateNewEmployee.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class RetrieveCreatedEmployee {
	
	
	@Given("request to retrieve employee is prepared")
	public void request_to_retrieve_employee_is_prepared() {
		request = given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.queryParam("employee_id", employeeID)
				.auth().oauth2(GenerateTokenStep.token);
		
	}
	
	@When("get call is made for retrieving employee")
	public void get_call_is_made_for_retrieving_employee() {
		response = request.when()
				.get(APIConstants.RETRIEVE_CREATED_EMPLOYEE);
	}
	
	@Then("{string} is match with global employee id value")
	public void is_match_with_global_employee_id_value(String empId) {
		response.then()
				.log()
				.ifValidationFails()
				.body(empId, equalTo(employeeID))
				.log()
				.body();
	}
	
	@Then("{string} object data match with created employee information")
	public void object_data_match_with_created_employee_information(String empObject,
																	io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> expectedData = dataTable.asMaps();
		Map<String, String> actualData = response.body().jsonPath().get(empObject);
		BiConsumer<String, String> matcher = Assert::assertEquals;
		
		expectedData.forEach(map -> map.forEach((k, v) -> matcher.accept(v, actualData.get(k))));
		
	}
}
