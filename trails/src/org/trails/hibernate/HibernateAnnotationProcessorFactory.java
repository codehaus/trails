package org.trails.hibernate;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.util.DTDEntityResolver;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.apt.Filer;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.Declaration;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.util.SimpleDeclarationVisitor;
import com.sun.org.apache.bcel.internal.generic.GETFIELD;

public class HibernateAnnotationProcessorFactory implements AnnotationProcessorFactory
{

    public HibernateAnnotationProcessorFactory()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public Collection<String> supportedOptions()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public Collection<String> supportedAnnotationTypes()
    {
        return Arrays.asList("javax.persistence.Entity");
    }

    public AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> decls, AnnotationProcessorEnvironment env)
    {
        return new HibernateAnnotationProcessor(env, decls);
    }
    
    public class HibernateAnnotationProcessor implements AnnotationProcessor
    {
        private AnnotationProcessorEnvironment env;
        private Set<AnnotationTypeDeclaration> annotationTypeDeclarations;
        
        public HibernateAnnotationProcessor(
                AnnotationProcessorEnvironment env, 
                Set<AnnotationTypeDeclaration> annTypeDecls)
        {
            this.env = env;
            this.annotationTypeDeclarations = annTypeDecls;
        }
        
        Element sessionFactoryElement;
        
        public void process()
        {
            System.out.println(env.getOptions());
            String sourcePath = env.getOptions().get("-sourcepath");
            String configFileName =sourcePath + File.separator + "hibernate.cfg.xml";
            System.out.println(configFileName);
            try
            {
                SAXReader reader = new SAXReader();
                reader.setValidation(false);
                reader.setEntityResolver(new DTDEntityResolver());
                reader.setIncludeExternalDTDDeclarations(false);
                reader.setIncludeInternalDTDDeclarations(false);
                Document doc = reader.read(configFileName);
                sessionFactoryElement = (Element)doc.getRootElement().selectSingleNode("//session-factory");
                for (Iterator iter = sessionFactoryElement.selectNodes("mapping[not(starts-with(@class, 'org.trails'))]").iterator(); iter.hasNext();)
                {
                    Element element = (Element) iter.next();
                    sessionFactoryElement.remove(element);
                }
                List listenerElements = sessionFactoryElement.elements("listener");
                sessionFactoryElement.elements().removeAll(listenerElements);
                
                for (AnnotationTypeDeclaration annotationTypeDecl : annotationTypeDeclarations)
                {
                    System.out.println(annotationTypeDecl);
                    for (Declaration declaration : env.getDeclarationsAnnotatedWith(annotationTypeDecl))
                    {
                        declaration.accept(new SimpleDeclarationVisitor() 
                        {
                            public void visitClassDeclaration(ClassDeclaration classDeclaration)
                            {
                                System.out.println(classDeclaration.getQualifiedName());
                                sessionFactoryElement.addElement("mapping").setAttributeValue("class", 
                                        classDeclaration.getQualifiedName());
                            }                       
                        });
                    }
                    sessionFactoryElement.elements().addAll(listenerElements);
                }
                PrintWriter writer =env.getFiler().createTextFile(Filer.Location.SOURCE_TREE, "", new File("hibernate.cfg.xml"), null);
                doc.write(writer);
                
            }
            catch (Exception ex) {ex.printStackTrace();}

        }
        
    }
}
