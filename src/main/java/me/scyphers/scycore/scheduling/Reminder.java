package me.scyphers.scycore.scheduling;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class Reminder {

    // Reminder Recipients
    private final Set<UUID> recipients;

    // Reminder Management
    private final Instant start;
    private final Instant end;

    // Message
    private final String messagePath;
    private final String[] messageReplacements;

    public Reminder(UUID recipient, Instant start, Instant end, String messagePath, String[] messageReplacements) {
        this(Collections.singleton(recipient), start, end, messagePath, messageReplacements);
    }

    public Reminder(Set<UUID> recipients, Instant start, Instant end, String messagePath, String[] messageReplacements) {
        this.recipients = recipients;
        this.start = start;
        this.end = end;
        this.messagePath = messagePath;
        this.messageReplacements = messageReplacements;
    }

    public Set<UUID> getRecipients() {
        return recipients;
    }

    public Instant getStart() {
        return start;
    }

    public Instant getEnd() {
        return end;
    }

    public long getDurationMillis() {
        return end.toEpochMilli() - start.toEpochMilli();
    }

    public String getMessagePath() {
        return messagePath;
    }

    public String[] getMessageReplacements() {
        return messageReplacements;
    }

}
