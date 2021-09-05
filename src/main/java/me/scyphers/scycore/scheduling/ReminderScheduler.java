package me.scyphers.scycore.scheduling;

import net.md_5.bungee.api.chat.BaseComponent;

import java.util.UUID;

public class ReminderScheduler {

    // TODO - flesh out

    // plugin reference

    // messenger reference // TODO - test if reloading plugin affects messenger

    // Map<Integer, Reminder> Reminders

    // int nextID

    // constructor - only plugin

    // methods

    // int addReminder(UUID player, String path, String... replacements, int ticksToRemind)
    // return a reminderID

    // int addReminder(UUID player, BaseComponent[] message, int ticksToRemind)
    // return a reminderID

    // int addReminder(UUID player, String message, int ticksToRemind)
    // return a reminderID

    // removeReminder(int reminderID)

    // private Instant nextReminder(Reminder r)
    // returns an instant for when this reminder should next remind. It is assumed that now is the starting point for the next reminder.

    private static record Reminder(int id, UUID player, BaseComponent[] message, int ticksToRemind) {

    }

}
