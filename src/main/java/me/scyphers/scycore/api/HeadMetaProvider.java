package me.scyphers.scycore.api;

import com.destroystokyo.paper.profile.PlayerProfile;
import me.scyphers.scycore.BasePlugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public final class HeadMetaProvider {

    // Constant for how many heads can be loaded in a single second
    private static final int MAX_HEADS_PER_SECOND = 1000;

    // Profiles
    private static final Map<UUID, PlayerProfile> profiles = new HashMap<>();

    // Profile API Request management
    private static final Queue<ProfileData> profileQueue = new ArrayDeque<>();
    private static int taskID;

    private static boolean initialised = false;

    // ItemStack constant
    private static final SkullMeta HEAD = (SkullMeta) new ItemStack(Material.PLAYER_HEAD).getItemMeta();

    public static void init(BasePlugin plugin) {

        if (initialised) throw new IllegalStateException("Cannot initialise meta provider more than once");

        // Schedule the repeating task to process each head
        taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

            // Add the profiles to the map
            for (int i = 0; i < MAX_HEADS_PER_SECOND; i++) {
                if (profileQueue.isEmpty()) break;
                ProfileData request = profileQueue.poll();
                profiles.put(request.uuid, request.playerProfile);
            }
        }, 20, 20);

        initialised = true;

    }

    public static ItemMeta getMeta(BasePlugin plugin, UUID uuid) {

        // The profile has been added, so complete the meta request
        if (profiles.containsKey(uuid)) {
            SkullMeta newHead = HEAD.clone();
            newHead.setPlayerProfile(profiles.get(uuid));
            return newHead;

        // The profile is a new profile, so add an async request to complete it and return a default head
        } else {
            completeProfile(plugin, uuid);
            return HEAD.clone();
        }

    }

    public static void loadHead(BasePlugin plugin, UUID uuid) {
        completeProfile(plugin, uuid);
    }

    public static void loadHeads(BasePlugin plugin, Collection<UUID> uuids) {
        completeProfiles(plugin, uuids);
    }

    public static void disableHeadRequests(BasePlugin plugin) {
        plugin.getServer().getScheduler().cancelTask(taskID);
    }

    private static void completeProfile(BasePlugin plugin, UUID uuid) {
        completeProfiles(plugin, Collections.singleton(uuid));
    }

    private static void completeProfiles(BasePlugin plugin, Collection<UUID> uuids) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            for (UUID uuid : uuids) {
                PlayerProfile profile = plugin.getServer().createProfile(uuid);
                profile.complete();
                ProfileData data = new ProfileData(uuid, profile);
                addProfile(data);
            }
        });
    }

    private static synchronized void addProfile(ProfileData data) {
        profileQueue.offer(data);
    }

    private static record ProfileData(UUID uuid, PlayerProfile playerProfile) {}

}
