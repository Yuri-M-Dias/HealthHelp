package br.ufg.pes.healthhelp;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.ufg.inf.pes.healthhelp.view.FormularioLocalAtendimentoActivity;
import br.ufg.inf.pes.healthhelp.view.LocaisAtendimentoActivity;
import br.ufg.inf.pes.healthhelp.view.LocalAtendimentoActivity;

import static android.support.test.espresso.intent.Intents.init;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.release;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

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
        //onView(withId(R.id.botao_hospital))
        //        .perform(click());
        intended(hasComponent(LocalAtendimentoActivity.class.getName()));
        release();
    }

    @Test
    public void testBotaoNovoLocalDeAtendimento() throws Exception {
        init();
        mActivityRule.launchActivity(new Intent());
        //onView(withId(R.id.botao_novo_local_atendimento))
        //        .perform(click());
        intended(hasComponent(FormularioLocalAtendimentoActivity.class.getName()));
        release();
    }

}
