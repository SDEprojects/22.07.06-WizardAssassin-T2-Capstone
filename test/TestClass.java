import com.wizard_assassin.model.Game;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

class TestClass {

    private Game game;

    public void TestClass(){

    }

    @Before
    void setUp() {
        game = new Game();
    }

    @Test
    void testNumberOne(){
        assertNotNull(game);
    }

    @Test
    void testNumberTwo() {

    }

}