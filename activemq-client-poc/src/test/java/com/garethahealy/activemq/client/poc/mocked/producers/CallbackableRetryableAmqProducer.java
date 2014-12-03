package com.garethahealy.activemq.client.poc.mocked.producers;

import com.garethahealy.activemq.client.poc.callbacks.DefaultCallbackHandler;
import com.garethahealy.activemq.client.poc.config.BrokerConfiguration;
import com.garethahealy.activemq.client.poc.config.RetryConfiguration;
import com.garethahealy.activemq.client.poc.producers.RetryableAmqProducer;
import com.garethahealy.activemq.client.poc.resolvers.ConnectionFactoryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class CallbackableRetryableAmqProducer extends RetryableAmqProducer {

        private static final Logger LOG = LoggerFactory.getLogger(CallbackableRetryableAmqProducer.class);

        private DefaultCallbackHandler defaultCallbackHandler;

        public CallbackableRetryableAmqProducer(DefaultCallbackHandler defaultCallbackHandler, RetryConfiguration retryConfiguration, BrokerConfiguration brokerConfiguration,
                                                ConnectionFactoryResolver connectionFactoryResolver) {
                super(retryConfiguration, brokerConfiguration, connectionFactoryResolver);

                this.defaultCallbackHandler = defaultCallbackHandler;
        }

        @Override
        protected Connection createConnection() throws JMSException {
                Connection amqConnection = super.createConnection();

                defaultCallbackHandler.createConnection();
                return amqConnection;
        }

        @Override
        protected Session createSession(Connection amqConnection, boolean isTransacted, int acknowledgeMode) throws JMSException {
                Session session = super.createSession(amqConnection, isTransacted, acknowledgeMode);

                defaultCallbackHandler.createSession();
                return session;
        }

        @Override
        protected Queue createQueue(Session amqSession, String queueName) throws JMSException {
                Queue queue = super.createQueue(amqSession, queueName);

                defaultCallbackHandler.createQueue();
                return queue;
        }

        @Override
        protected MessageProducer createProducer(Session amqSession, Queue amqQueue) throws JMSException {
                MessageProducer messageProducer = super.createProducer(amqSession, amqQueue);

                defaultCallbackHandler.createProducer();
                return messageProducer;
        }

        @Override
        protected Message createMessage(Session amqSession, Object[] body) throws JMSException {
                Message message = super.createMessage(amqSession, body);

                defaultCallbackHandler.createMessage();
                return message;
        }
}
