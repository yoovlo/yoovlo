package org.yoovlo.shr.java.events;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import org.yoovlo.shr.java.T;
import org.yoovlo.shr.java.Utils;
import org.yoovlo.shr.java.Ser;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.yoovlo.shr.java.req.Req_props;
import org.springframework.boot.SpringApplication;
import org.yoovlo.shr.java.req.Req_info_mock;
import org.yoovlo.shr.java.Globals;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
this is a Consumer of messages (and producer, because task handling may produce subsequent messages) that handles messages that may be executed by third party binaries
	its purpose is to provide execution of tasks by third party binaries, whether through:
		to a rs,grpc,graphql,rest,etc endpoint
		to some serverless topology
		file system binary
	if you want build a jar that directly handles tasks, ie an event_proc that executes tasks or workflows in it's jvm, you can extend or compose this
		intercept tasks and execute functions directly based on Req content
		create workflow packages and add them to the Tmpl_svc

the purpose of having a message queue (tasks) in your architecture is to gain the functionality of having a distributed queue of requests from which tasks are pulled
	as opposed to services executing requests directly on each other with some RPC protocol, risking a blocking  server like apache, will start hanging in the instance that all of it's threads are busy
	essentially, a message queue provides the funcionality of maintaining messages when the message handlers are blocked

the objective of this event_proc is to:
	be a generic consumer of messages by executing messages against mainstream,popular protocols,endpoints and other services in an efficient manner
		in terms of efficiency: some applications may offer multiple endpoint protocols, but offer an internal protocol that is optimized
		some operations may be generic enough to map to multiple services, essentially, it is not outside the bounds of this processor to provide a messaging API for generic service operations
			for example, an operation with the intent of deleting an operator from kbr (kubernetes), may comprise of several requests:
				however, there may be value of providing an implementation directly in event_proc that simply consumes something like:
				{
					svc:kbr
					op:delete_op
				}
				as such, there isn't a very strong opinionated boundary on what may be implemented interally to the event_proc (main binary or service cluster) as opposed to only handled by a third party endpoint
					and what is handled directly by the tasks proc
					the intent is that the implementation of internal message handling is based on:
						practical requirements
						volume and necessities of live network traffic for what event_proc is used for 
						the fact that some tasks are geared toward generic network topologies and as such warrant a generic internal implementation or supporting frameworks in event_proc
							for example:
								the handling of third party services  includes operations that are generic, namely authentication, service accounts, etc
								the problem of workload scheduling, though at this time has a market leader in kubernetes, it is not a fact that this will remain the case forever. 
									however, the problem is a generic one that has been addressed in multiple ways and thus may contain an underlying generic toplogy and set of operations. the following is a list of software that has implemented workload scheduling:
										kbr, peleton (by uber), bistro (facebook), jupiter (facebook)
								similarily, the following categories of functionality contain operations that are common irregardless of the implementing middleware:
									storage, real-time workflows, ETL workflows, DAGs, authentication, data forwarding, data invalidation, etc
						the overlying objective is to provide practical solutions that execute functionality for the types of messages that are being produced
	ie, provide a relatively unified, and practical way to pass a range of common message types to a message-queue to be executed by a number of different endpoints and protocols
*/
public class Consumer_app_gen extends Consumer_app_base {
	public static void main(String[] args) throws IOException {
		T.t("Consumer_app_gen main");
		//Utils.kill_port_holder(8080);
		Globals.init();
		ConfigurableApplicationContext context=SpringApplication.run(Consumer_app_gen.class, args);
		Globals.spring_ctx=context;
		Globals.beans_init();
		Consumer_app_gen app = context.getBean(Consumer_app_gen.class);
		app.run_t();
		T.t("Consumer_app_gen main end");
	}
	public Consumer_app_gen() {
		super();
	}
}