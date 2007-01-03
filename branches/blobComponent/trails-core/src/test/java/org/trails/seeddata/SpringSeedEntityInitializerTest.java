package org.trails.seeddata;

import org.acegisecurity.userdetails.UserDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.trails.persistence.PersistenceService;
import org.trails.security.TrailsUserDAO;
import org.trails.security.domain.Role;
import org.trails.test.Foo;

import junit.framework.TestCase;

public class SpringSeedEntityInitializerTest extends TestCase {
	private ApplicationContext applicationContext;
	private SpringSeedEntityInitializer seedDataInitializer;
	private PersistenceService persistenceService;
	private TrailsUserDAO userDAO;
	private Role roleUser;
	private Role roleAdmin;
	
	@Override
	protected void setUp() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[]{"applicationContext-test.xml", "seed-data-test.xml"} );
		persistenceService = (PersistenceService)applicationContext.getBean("persistenceService");
		
		userDAO =  (TrailsUserDAO)applicationContext.getBean("trailsUserDAO");
		seedDataInitializer = (SpringSeedEntityInitializer)applicationContext.getBean(SeedDataInitializer.class.getSimpleName());
		roleUser = (Role)applicationContext.getBean("roleUser");
		roleAdmin = (Role)applicationContext.getBean("roleAdmin");
		seedDataInitializer.init();
	}
	
	public void testInit() {
		UserDetails user = userDAO.loadUserByUsername("user");
		assertEquals(user.getAuthorities()[0], roleUser);
	}

	public void testArbitraryEntitySeeded() {
		seedDataInitializer.init();
    DetachedCriteria criteria = DetachedCriteria.forClass(Foo.class);
    criteria.add(Restrictions.eq("name", "seed foo"));
    Foo foo = (Foo)persistenceService.getInstance(criteria);
    assertNotNull(foo);
	}
	
	public void tearDown() {
		// Clean up
		// Seems I have to clean up because HSQL must be using static class members
		UserDetails user = userDAO.loadUserByUsername("user");
		persistenceService.remove(user);
		user = userDAO.loadUserByUsername("admin");
		persistenceService.remove(user);
		persistenceService.remove(roleUser);
		persistenceService.remove(roleAdmin);

		Foo foo = new Foo();
		foo.setId(1);
    persistenceService.remove(foo);
		
		// TODO see if you can do this instead: sessionFactory.dropDatabaseSchema();
	}
	
}
