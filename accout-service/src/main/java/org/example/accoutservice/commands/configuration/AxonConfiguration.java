package org.example.accoutservice.commands.configuration;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.common.Registration;
import org.example.accoutservice.commands.util.validation.AggregateCommandDispatchInterceptor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Cấu hình Axon: Tích hợp Axon Framework với Spring thông qua bean.
@Configuration
public class AxonConfiguration implements DisposableBean {

    private Registration registration;

    @Bean
    public CommandBus commandBus(AggregateCommandDispatchInterceptor interceptor) {
        CommandBus commandBus = SimpleCommandBus.builder().build();
        registration = commandBus.registerDispatchInterceptor(interceptor);
        return commandBus;
    }

    @Override
    public void destroy() {
        if (registration != null) {
            registration.close();
        }
    }
}
