package org.ns1.gatherbot.util;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageId {
    private final String messageId;

    @Override
    public String toString() {
        return messageId;
    }
}
