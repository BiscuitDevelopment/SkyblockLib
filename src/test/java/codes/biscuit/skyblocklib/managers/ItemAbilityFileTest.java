package codes.biscuit.skyblocklib.managers;

import codes.biscuit.skyblocklib.model.SkyblockItemAbility;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;

public class ItemAbilityFileTest {

    private final String TEST_KEY = "Test Ability";
    private final SkyblockItemAbility TEST_ABILITY = new SkyblockItemAbility(TEST_KEY, 10, 100, "TEST_ITEM", null);
    private final Map<String, SkyblockItemAbility> testAbilities = Collections.singletonMap(TEST_KEY, TEST_ABILITY);

    private ItemAbilityFile itemAbilityFile;

    @Before
    public void setUp() throws Exception {
        itemAbilityFile = ItemAbilityFile.fromAbilityMap(testAbilities);
    }

    @Test
    public void findAbilityByName() throws Exception {
        Optional<SkyblockItemAbility> optionalAbility = itemAbilityFile.findAbilityByName(TEST_KEY);
        assertTrue(optionalAbility.isPresent());
        assertEquals(TEST_ABILITY, optionalAbility.get());
    }

    @Test(expected = NoSuchElementException.class)
    public void findAbilityByName_notFound() {
        Optional<SkyblockItemAbility> optionalAbility = itemAbilityFile.findAbilityByName("non existing name");
        assertFalse(optionalAbility.isPresent());
        assertNull(optionalAbility.get());
    }

    @Test(expected = NoSuchElementException.class)
    public void findAbilityByName_nullName() {
        Optional<SkyblockItemAbility> optionalAbility = itemAbilityFile.findAbilityByName(null);
        assertFalse(optionalAbility.isPresent());
        assertNull(optionalAbility.get());
    }

    @Test(expected = NoSuchElementException.class)
    public void findAbilityByName_wrongCase() {
        Optional<SkyblockItemAbility> optionalAbility = itemAbilityFile.findAbilityByName("test Ability"); // wrong capitalization
        assertFalse(optionalAbility.isPresent());
        assertNull(optionalAbility.get());
    }
}