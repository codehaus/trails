package org.trails.component;

import junit.framework.TestCase;
import org.trails.test.Gazonk;

public class EnumSelectionModelTest extends TestCase
{
    EnumSelectionModel selectionModel;
    EnumSelectionModel nullableSelectionModel;

    public void setUp()
    {
        selectionModel = new EnumSelectionModel(Gazonk.Origin.class);
        nullableSelectionModel = new EnumSelectionModel(Gazonk.Origin.class, true);
    }

    public void testGetValue() throws Exception
    {
        assertEquals("right value", "AMERICA", selectionModel.getValue(1));
        assertEquals("none value", IdentifierSelectionModel.DEFAULT_NONE_VALUE, nullableSelectionModel.getValue(0));
        assertEquals("right value", "AFRICA", nullableSelectionModel.getValue(1));
    }

    public void testNoneLabelValue() throws Exception
    {
        nullableSelectionModel.setNoneLabel("Any");
        assertEquals("Any", nullableSelectionModel.getLabel(0));
    }

    public void testGetLabel() throws Exception
    {
        assertEquals("right label", "AMERICA", selectionModel.getLabel(1));
        assertEquals("none value", EnumSelectionModel.DEFAULT_NONE_LABEL, nullableSelectionModel.getLabel(0));
        assertEquals("right label", "AFRICA", nullableSelectionModel.getLabel(1));
    }

    public void testTranslateValue() throws Exception
    {
        Gazonk.Origin origin = (Gazonk.Origin) selectionModel.translateValue("AMERICA");
        assertEquals("got right origin", Gazonk.Origin.AMERICA, origin);
        assertEquals("should be null", null, nullableSelectionModel.translateValue(EnumSelectionModel.DEFAULT_NONE_VALUE));
        assertEquals("correct origin", Gazonk.Origin.AFRICA, nullableSelectionModel.translateValue("AFRICA"));
    }
    
    public void testTranslateValueWithToString() throws Exception
    {
        EnumSelectionModel animalSelectionModel = new EnumSelectionModel(Gazonk.Animal.class, false);
        Gazonk.Animal kitty = (Gazonk.Animal) animalSelectionModel.translateValue("Cat");
        assertEquals(Gazonk.Animal.CAT, kitty);
    }

    public void testGetOptionCount() throws Exception
    {
        assertEquals(5, selectionModel.getOptionCount());
        assertEquals(6, nullableSelectionModel.getOptionCount());
    }
}
