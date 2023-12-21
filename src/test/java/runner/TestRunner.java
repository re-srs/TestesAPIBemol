package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        //plugin = {"json:target/reports/cucumberReport.json", "html:target/reports/"},
        features = "src/test/resources/features",
        tags = "@usuario",
        glue = {"steps"}

)

public class TestRunner {
}
