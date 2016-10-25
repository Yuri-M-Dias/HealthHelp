package br.ufg.inf.pes.healthhelp.test;

import cucumber.api.CucumberOptions;

@CucumberOptions(
        features = "features",
        glue = { "br.ufg.inf.pes.healthhelp.cucumber.steps" },
        format = {"pretty",
                "html:/data/data/my.package/cucumber-reports/cucumber-html-report",
                "json:/data/data/my.package/cucumber-reports/cucumber.json",
                "junit:/data/data/my.package/cucumber-reports/cucumber.xml"
        }
)
public class CucumberTestCase {
}
