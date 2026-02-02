package com.ecole221.application.port.out.event;

public interface EventTransport {
    void send(String topic, String key, String payload);
}
