package com.actuator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import de.codecentric.boot.admin.server.notify.LoggingNotifier;
import reactor.core.publisher.Mono;

@Component
public class CustomNotifier extends AbstractEventNotifier {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingNotifier.class);

	public CustomNotifier(InstanceRepository repository) {
		super(repository);
	}

	@Override
	protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
		return Mono.fromRunnable(() -> {
			if (event instanceof InstanceStatusChangedEvent) {

				LOGGER.info("Instance {} ({}) is {}", instance.getRegistration().getName(), event.getInstance(),
						((InstanceStatusChangedEvent) event).getStatusInfo().getStatus());
				if(instance.getRegistration().getName().equals("soundApi")) {
					if (((InstanceStatusChangedEvent) event).getStatusInfo().getStatus().equals("UP")) {
						try {
							ChatbotSend.send("小链音响上线了");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					if (((InstanceStatusChangedEvent) event).getStatusInfo().getStatus().equals("OFFLINE")) {
						try {
							ChatbotSend.send("小链音响下线了");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
				}
				
			} else {

				LOGGER.info("Instance {} ({}) {}", instance.getRegistration().getName(), event.getInstance(),
						event.getType());
			}
		});
	}
}