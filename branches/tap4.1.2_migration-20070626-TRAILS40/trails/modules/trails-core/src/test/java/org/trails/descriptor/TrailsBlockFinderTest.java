package org.trails.descriptor;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.components.Block;
import org.apache.tapestry.test.Creator;
import org.apache.tapestry.util.ComponentAddress;
import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.trails.page.IEditorBlockPage;
import org.trails.test.Foo;

public class TrailsBlockFinderTest extends MockObjectTestCase
{

	IPropertyDescriptor descriptor;
	IPropertyDescriptor stringDescriptor;
	IPropertyDescriptor fooDescriptor;
	IPropertyDescriptor overriddenDescriptor;
	ComponentAddress readOnlyEditor;
	ComponentAddress numericEditor;
	ComponentAddress stringEditor;
	EditorBlockFinder editorService = new EditorBlockFinder();

	public void setUp() throws Exception
	{
		HashMap editorMap = new HashMap();
		readOnlyEditor = new ComponentAddress("trails:Editors", "readOnlyEditor");
		numericEditor = new ComponentAddress("trails:Editors", "numericEditor");
		stringEditor = new ComponentAddress("trails:Editors", "stringEditor");
		editorService.setDefaultBlockAddress(stringEditor);
		editorMap.put("numeric", numericEditor);
		editorMap.put("string", stringEditor);

		editorService.setEditorMap(editorMap);
		descriptor = new TrailsPropertyDescriptor(Foo.class, "number", Double.class);
		stringDescriptor = new TrailsPropertyDescriptor(Foo.class, "string", String.class);
		fooDescriptor = new TrailsPropertyDescriptor(Foo.class, "foo", Foo.class);
		overriddenDescriptor = new TrailsPropertyDescriptor(Foo.class, "overridden", String.class);

	}

	public void testFindBlockAddress()
	{

		assertEquals(numericEditor, editorService.findBlockAddress(descriptor));
		assertEquals(stringEditor, editorService.findBlockAddress(stringDescriptor));
		assertEquals("got default editor",
			stringEditor, editorService.findBlockAddress(fooDescriptor));
		stringDescriptor.setReadOnly(true);

	}

	public void testFindBlock()
	{

		Creator creator = new Creator();

		final IEditorBlockPage page = mock(IEditorBlockPage.class);
		final IRequestCycle cycle = mock(IRequestCycle.class);
		final Foo model = new Foo();

		final Block overrriddenBlock = (Block) creator.newInstance(Block.class, new Object[]{"page", page});
		overrriddenBlock.setContainer(page);

		final Block block = (Block) creator.newInstance(Block.class, new Object[]{"page", page});
		block.setContainer(page);

		final Map pageComponents = new HashMap();
		pageComponents.put("overridden", overrriddenBlock);

		checking(new Expectations() {{
//			atLeast(1).of(page).getPageName(); will(returnValue("trails:Editors"));
			ignoring(page).getIdPath(); will(returnValue(null)); // the test failed without this
			ignoring(page).getLocation(); will(returnValue(null)); // the test failed without this
			atLeast(1).of(page).getComponents(); will(returnValue(pageComponents));
			atLeast(1).of(page).getComponent("overridden"); will(returnValue(overrriddenBlock));


			atLeast(1).of(page).setModel(model);
			atLeast(1).of(page).getModel(); will(returnValue(model));
			one(page).setDescriptor(stringDescriptor);
			one(page).getNestedComponent("stringEditor"); will(returnValue(block));

			atLeast(1).of(cycle).getPage(); will(returnValue(page));
			atLeast(1).of(cycle).getPage("trails:Editors"); will(returnValue(page));
		}});

		assertEquals(overrriddenBlock, editorService.findBlock(cycle, overriddenDescriptor));
		assertEquals(block, editorService.findBlock(cycle, stringDescriptor));
	}

	public void testFromSpring()
	{
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
			"applicationContext-test.xml");
		BlockFinder editorService = (BlockFinder) appContext.getBean("editorService");
		IPropertyDescriptor stringDescriptor = new TrailsPropertyDescriptor(Foo.class, "string", String.class);
		assertNotNull(editorService.findBlockAddress(stringDescriptor));
		assertTrue(editorService.findBlockAddress(stringDescriptor) instanceof ComponentAddress);
		stringDescriptor.setReadOnly(true);
		ComponentAddress editorAddress = editorService.findBlockAddress(stringDescriptor);
		assertEquals("readOnly", editorAddress.getIdPath());
		IPropertyDescriptor passwordDescriptor = new TrailsPropertyDescriptor(Foo.class, "password", String.class);
		editorAddress = editorService.findBlockAddress(passwordDescriptor);
		assertEquals("passwordEditor", editorAddress.getIdPath());
	}

}
