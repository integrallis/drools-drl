package org.integrallis.drools;

import org.apache.log4j.Logger;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {
	
	private static Logger applicationLogger = Logger.getLogger(DroolsTest.class);

	public static final void main(String[] args) {
	    KieSession knowledgeSession = null;
	    try {
	        // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
		    KieContainer kContainer = ks.getKieClasspathContainer();
		    knowledgeSession = kContainer.newKieSession("ksession-rules");
			
		    knowledgeSession.setGlobal("applicationLogger", applicationLogger);
			knowledgeSession.insert(new SomeClass("Hey you!"));

			knowledgeSession.fireAllRules();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			knowledgeSession.dispose();
        }
	}
}