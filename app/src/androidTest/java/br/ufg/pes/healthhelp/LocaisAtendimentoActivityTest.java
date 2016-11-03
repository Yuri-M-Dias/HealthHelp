package br.ufg.pes.healthhelp;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.ufg.inf.pes.healthhelp.view.LocaisAtendimentoActivity;
import br.ufg.inf.pes.healthhelp.view.NovoLocalAtendimentoActivity;

import static android.content.ClipData.Item;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.init;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.release;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LocaisAtendimentoActivityTest {

    @Rule
    public ActivityTestRule<LocaisAtendimentoActivity> mActivityRule =
            new ActivityTestRule<>(LocaisAtendimentoActivity.class);

    @Before
    public void setUp() {
        init();
    }

    @After
    public void tearDown() {
        release();
    }

    @Test
    public void testDeveMostrarListaLocaisAtendimento() throws Exception {
        mActivityRule.launchActivity(new Intent());
        Thread.sleep(3000);
        onData(instanceOf(Item.class))
                .inAdapterView(allOf(withId(R.id.list_locais_atendimento), isDisplayed()))
                .atPosition(2)
                .check(matches(isDisplayed()));
        intended(hasComponent(LocaisAtendimentoActivity.class.getName()));
    }

    @Test
    public void testBotaoHospital() throws Exception {
        mActivityRule.launchActivity(new Intent());
        onView(withId(R.id.fab_list_locais_atendimento))
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withId(R.id.fab_list_locais_atendimento_novo_local))
                .check(matches(isDisplayed()))
                .perform(click());
        intended(hasComponent(NovoLocalAtendimentoActivity.class.getName()));
    }

}
