package com.ai.bot.web.websocket;

import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.server.WebSocketServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.jetty.JettyRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.ai.bot.common.util.MessageSystemContext;
import com.ai.message.actor.ActorSystem;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

	private final ActorSystem system = MessageSystemContext.getActorSystem();
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(messageWebSocketHandler(), "/message.bot").setHandshakeHandler(handshakeHandler()).addInterceptors(new WebSocketHandshakeInterceptor());
		registry.addHandler(functionWebSocketHandler(), "/function.bot").setHandshakeHandler(handshakeHandler()).addInterceptors(new WebSocketHandshakeInterceptor());
	}

	@Bean
	public DefaultHandshakeHandler handshakeHandler() {
		WebSocketPolicy policy = new WebSocketPolicy(WebSocketBehavior.SERVER);
		policy.setInputBufferSize(8192);
		policy.setIdleTimeout(600000);

		return new DefaultHandshakeHandler(new JettyRequestUpgradeStrategy(new WebSocketServerFactory(policy)));
	}

	@Bean
	public MessageWebSocketHandler messageWebSocketHandler() {
		return new MessageWebSocketHandler();
	}

	@Bean
	public FunctionWebSocketHandler functionWebSocketHandler() {
		return new FunctionWebSocketHandler(system);
	}
}
