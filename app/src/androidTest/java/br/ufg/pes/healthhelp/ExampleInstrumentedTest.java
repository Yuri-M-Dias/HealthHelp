package br.ufg.pes.healthhelp;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.ufg.pes.healthhelp.Controler.HospitalActivity;
import br.ufg.pes.healthhelp.Controler.LocaisAtendimentoActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.init;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.release;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<LocaisAtendimentoActivity> mActivityRule =
            new ActivityTestRule<>(LocaisAtendimentoActivity.class);

    @Test
    public void testBotaoHospital() throws Exception {
        init();
        mActivityRule.launchActivity(new Intent());
        onView(withId(R.id.botao_hospital))
                .perform(click());
        intended(hasComponent(HospitalActivity.class.getName()));
        release();
    }

    @Test
    public void testBotaoNovoLocalDeAtendimento() throws Exception {
        init();
        mActivityRule.launchActivity(new Intent());
        onView(withId(R.id.botao_novo_local_atendimento))
                .perform(click());
        intended(hasComponent(LocaisAtendimentoActivity.class.getName()));
        release();
    }

}
