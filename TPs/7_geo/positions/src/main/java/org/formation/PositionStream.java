package org.formation;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface PositionStream {


	String OUTPUT = "positions-out";
	

	
	@Output(OUTPUT)
	MessageChannel outboundPositions();
}
