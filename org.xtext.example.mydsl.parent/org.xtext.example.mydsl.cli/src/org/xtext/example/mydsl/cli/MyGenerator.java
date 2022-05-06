package org.xtext.example.mydsl.cli;
import java.io.File;
import java.util.List;


import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.GeneratorDelegate;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.xtext.example.mydsl.MyDslStandaloneSetup;
import org.xtext.example.mydsl.generator.MyDslGenerator;

public class MyGenerator {

    @Inject
    private Provider<ResourceSet> resourceSetProvider;

    @Inject
    private IResourceValidator validator;

   

    @Inject
    private JavaIoFileSystemAccess fileAccess;

    public static void execute(String path) {
        final Injector injector = new MyDslStandaloneSetup().createInjectorAndDoEMFRegistration();
        final MyGenerator main = injector.getInstance(MyGenerator.class);
        main.runGenerator(path);

    }

    protected void runGenerator(String path) {
        // Load the resources
        final ResourceSet resourceSet = this.resourceSetProvider.get();
        
        final Resource resource = resourceSet.getResource(URI.createFileURI(path),
                true);

        // Validate the resource
        final List<Issue> list = this.validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
        if (!list.isEmpty()) {
            for (final Issue issue : list) {
                System.err.println(issue);
            }
            return;
        }

        MyDslGenerator myDslGenerator = new MyDslGenerator();
		// Configure and start the generator
        myDslGenerator.doGenerate(resource, this.fileAccess, null);

        System.out.println("Code generation finished.");
    }
}
