package org.xtext.example.mydsl.cli;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

import java.util.Map;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.xtext.generator.GeneratorDelegate;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.eclipse.xtext.validation.IResourceValidator;
import org.xtext.example.mydsl.MyDslStandaloneSetup;



/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	@Inject
    private Provider<ResourceSet> resourceSetProvider;

    @Inject
    private IResourceValidator validator;

    @Inject
    private GeneratorDelegate generator;

    @Inject
    private JavaIoFileSystemAccess fileAccess;

	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		final Map<?, ?> args = context.getArguments();
        final String[] appArgs = (String[]) args.get("application.args");
        
        final Injector injector = new MyDslStandaloneSetup().createInjectorAndDoEMFRegistration();
        System.out.println("Hello");
        MyGenerator.execute(appArgs[1]);
        
        return IApplication.EXIT_OK;
    }
 
	

	@Override
	public void stop() {
		// nothing to do
	}
}
