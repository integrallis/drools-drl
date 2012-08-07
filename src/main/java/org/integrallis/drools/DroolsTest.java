package org.integrallis.drools;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class DroolsTest {
	
	private static Logger applicationLogger = Logger.getLogger(DroolsTest.class);

	public static final void main(String[] args) {
		try {
			// load up the knowledge base
			KnowledgeBase knowledgeBase = readKnowledgeBase();
			StatefulKnowledgeSession knowledgeSession = knowledgeBase.newStatefulKnowledgeSession();
			
			knowledgeSession.setGlobal("myLogger", applicationLogger);
			
			KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(knowledgeSession, "test");
			
			knowledgeSession.insert(new SomeClass("Hey you!"));

			knowledgeSession.fireAllRules();
			logger.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		knowledgeBuilder.add(ResourceFactory.newClassPathResource("sample.drl"), ResourceType.DRL);
		KnowledgeBuilderErrors errors = knowledgeBuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error: errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
		knowledgeBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages());
		return knowledgeBase;
	}
	
	

}