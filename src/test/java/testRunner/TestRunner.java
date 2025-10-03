package testRunner;
//import io.cucumber.junit.Cucumber;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;


//Cucumber
@CucumberOptions(features = "src/test/resources/features", glue = {"stepDefinitions"}, plugin = {"pretty", "html:target/cucumber-report.html", "json:target/cucumber-json-report.json"}, tags = "@Regression")
public class TestRunner extends AbstractTestNGCucumberTests {

}
