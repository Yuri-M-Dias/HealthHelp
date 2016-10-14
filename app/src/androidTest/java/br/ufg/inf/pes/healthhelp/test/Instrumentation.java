package br.ufg.inf.pes.healthhelp.test;

import android.os.Bundle;
import android.support.test.runner.MonitoringInstrumentation;

import cucumber.api.CucumberOptions;
import cucumber.api.android.CucumberInstrumentationCore;

@CucumberOptions(
        features = "features",
        glue = {"br.ufg.inf.pes.healthhelp.test.steps"},
        format = {"pretty",
                "html:/data/data/my.package/cucumber-reports/cucumber-html-report",
                "json:/data/data/my.package/cucumber-reports/cucumber.json",
                "junit:/data/data/my.package/cucumber-reports/cucumber.xml"
        }
)
public class Instrumentation extends MonitoringInstrumentation {

    private final CucumberInstrumentationCore mInstrumentationCore = new CucumberInstrumentationCore(this);

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);

        mInstrumentationCore.create(arguments);
        start();
    }

    @Override
    public void onStart() {
        super.onStart();

        waitForIdleSync();
        mInstrumentationCore.start();
    }

}
