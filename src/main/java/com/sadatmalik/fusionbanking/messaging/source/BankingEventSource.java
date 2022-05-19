package com.sadatmalik.fusionbanking.messaging.source;

import com.sadatmalik.fusionbanking.messaging.events.UserTokenChangeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * This class creates the logic to publish messages to the Spring Cloud Stream message broker.
 *
 * It injects a Source interface implementation for use by the service. All communication to a
 * specific message topic occurs through a Spring Cloud Stream construct called a channel, which
 * is represented by a Java interface. We use the Source interface, which exposes a single
 * method called output().
 *
 * The Source interface is convenient to use when our service only needs to publish to a single
 * channel. The output() method returns a class of type MessageChannel. With this type, we send
 * messages to the message broker.
 *
 * @author sadatmalik
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class BankingEventSource {
    private final Source source;

    /**
     * Builds and publishes a Java POJO message called UserTokenChangeEvent, sending the message
     * from a channel defined in the Source class.
     *
     * The ActionEnum passed by the parameters in the output() method contains the following
     * actions:
     *
     * public enum ActionEnum {
     *    GET,
     *    CREATED,
     *    UPDATED,
     *    DELETED,
     *    EXPIRED
     * }
     *
     * Publish the message using the send() method on the MessageChannel class returned from
     * source.output(). The Spring helper class, MessageBuilder, takes the contents of the
     * UserTokenChangeEvent  and converts into a Spring Message class.
     *
     * The application configuration property spring.cloud.stream.bindings.output maps the
     * source.output() channel to the userTokenChangeTopic on the message broker.
     *
     * @param action get, created, updated, deleted, expired.
     * @param accessToken the token for which a change event is being sent.
     */
    public void publishUserTokenChangeEvent(String action, String accessToken){
        log.debug("Sending Kafka message {} for Access Token: {}",
                action, accessToken);
        UserTokenChangeEvent event =  new UserTokenChangeEvent(
                UserTokenChangeEvent.class.getTypeName(),
                action,
                accessToken,
                // todo insert actual traceId here
                "traceId:sample-001234-56789");

        source.output().send(
                MessageBuilder
                        .withPayload(event)
                        .build());
    }
}