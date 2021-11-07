package me.scyphers.scycore.scheduling;

import me.scyphers.scycore.BasePlugin;
import me.scyphers.scycore.api.Messenger;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ReminderScheduler {

    private final BasePlugin plugin;

    // TODO - test if reloading plugin affects messenger
    private final Messenger messenger;

    private final Map<Integer, Reminder> reminders;

    private final Set<Integer> repeatingReminders;

    private final int reminderTaskID;

    private int nextReminderID = 0;

    public ReminderScheduler(BasePlugin plugin) {
        this.plugin = plugin;
        this.messenger = plugin.getMessenger();
        this.reminders = new HashMap<>();
        this.repeatingReminders = new HashSet<>();
        this.reminderTaskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this::checkReminders, 20, 20);
    }

    private int getNextReminderID() {
        return nextReminderID++;
    }

    private void checkReminders() {
        this.updateReminders();
        this.sendReminders();
    }

    private void updateReminders() {

        // Don't operate on an empty reminder list
        if (reminders.size() == 0) return;

        // Prepare modifications to the set
        Instant now = Instant.now();
        Set<Integer> expiredReminders = new HashSet<>();
        Map<Integer, Reminder> repeatingReminderRefreshMap = new HashMap<>();

        // Iterate over each reminder
        for (int reminderID : reminders.keySet()) {
            Reminder reminder = reminders.get(reminderID);

            // Check for expired reminders
            if (now.isAfter(reminder.getEnd())) {

                // Add the reminder to the expired list
                expiredReminders.add(reminderID);

                // Check for repeating reminders
                if (repeatingReminders.contains(reminderID)) {
                    repeatingReminderRefreshMap.put(reminderID, reminder);
                }

            }

        }

        // Modify the marked reminders

        // Remove the expired reminders, which includes repeating reminders
        for (int reminderID : expiredReminders) {
            reminders.remove(reminderID);
        }

        // Add back the repeating reminders, but extending their timer by their duration
        for (int reminderID : repeatingReminderRefreshMap.keySet()) {
            Reminder oldReminder = repeatingReminderRefreshMap.get(reminderID);
            Instant newReminderStart = oldReminder.getEnd();
            Instant newReminderEnd = oldReminder.getEnd().plus(oldReminder.getDurationMillis(), ChronoUnit.MILLIS);
            Reminder newReminder = new Reminder(oldReminder.getRecipients(), newReminderStart, newReminderEnd, oldReminder.getMessagePath(), oldReminder.getMessageReplacements());
            reminders.put(reminderID, newReminder);
        }

    }

    private void sendReminders() {

        // Iterate over each reminder and send the reminder
        for (int reminderID : reminders.keySet()) {

            Reminder reminder = reminders.get(reminderID);

            for (UUID recipientUUID : reminder.getRecipients()) {

                Player player = plugin.getServer().getPlayer(recipientUUID);
                if (player == null) continue;

                messenger.chat(player, reminder.getMessagePath(), reminder.getMessageReplacements());

            }

        }

    }

    public int addReminder(UUID player, int ticksToRemind, String path, String... replacements) {
        long reminderDuration = ticksToRemind * 20L;
        Instant start = Instant.now();
        Instant end = start.plus(reminderDuration, ChronoUnit.SECONDS);
        Reminder reminder = new Reminder(player, start, end, path, replacements);
        int nextID = getNextReminderID();
        this.reminders.put(nextID, reminder);
        return nextID;
    }

    public int addRepeatingReminder(UUID player, int ticksToRemind, String path, String... replacements) {
        long reminderDuration = ticksToRemind * 20L;
        Instant start = Instant.now();
        Instant end = start.plus(reminderDuration, ChronoUnit.SECONDS);
        Reminder reminder = new Reminder(player, start, end, path, replacements);
        int nextID = getNextReminderID();
        this.reminders.put(nextID, reminder);
        this.repeatingReminders.add(nextID);
        return nextID;
    }

    public void removeReminder(int reminderID) {
        if (!this.reminders.containsKey(reminderID)) return;
        this.reminders.remove(reminderID);
    }

    public void endReminders() {
        plugin.getServer().getScheduler().cancelTask(reminderTaskID);
    }

}
