import events.ProjectileHit;
import items.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import thirtyvirus.uber.UberItems;
import thirtyvirus.uber.UberMaterial;
import thirtyvirus.uber.helpers.*;

import java.util.Arrays;
import java.util.Collections;

public class nef extends JavaPlugin {

    public void onEnable() {

        // enforce UberItems dependancy
        if (Bukkit.getPluginManager().getPlugin("UberItems") == null) {
            this.getLogger().severe("UberItems Addons requires UberItems! disabled because UberItems dependency not found");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // register events and UberItems
        registerEvents();
        registerUberMaterials();
        registerUberItems();

        // post confirmation in chat
        getLogger().info(getDescription().getName() + " V: " + getDescription().getVersion() + " has been enabled");
    }
    public void onDisable() {
        // posts exit message in chat
        getLogger().info(getDescription().getName() + " V: " + getDescription().getVersion() + " has been disabled");
    }
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new ProjectileHit(), this);
    }

    // NEW UBER ITEM CHECKLIST

    // - make a new class file, named with all lowercase lettering and underscores for spaces
    // - copy the UberItemTemplate class contents into the new class, extend UberItem
    // - make a putItem entry, follow the format of previous items and make sure to give a unique id
    // - write the unique item ability code in the appropriate method

    // - add the following line of code just after executing the item's ability:
    //      onItemUse(player, item); // confirm that the item's ability has been successfully used

    // - if the ability needs a cooldown, prefix it's code with a variation of the following line of code:
    //      if (!Utilities.enforceCooldown(getMain(), player, "name", 1, item, true)) return;

    // - if the item needs work done on create (like adding enchantments, adding other data) refer to onItemStackCreate
    // - if the item needs a prefix or suffix in its description,
    //   refer to the getSpecificLorePrefix and getSpecificLoreSuffix functions, then add the following:
    //      lore.add(ChatColor.RESET + "text goes here");

    // - if you need to store & retrieve ints and strings from items, you can use the following functions:
    //      Utilities.storeIntInItem(getMain(), item, 1, "number tag");
    //      if (Utilities.getIntFromItem(getMain(), item, "number tag") == 1) // { blah blah blah }
    //      (the same case for strings, just storeStringInItem and getStringFromItem)

    private void registerUberItems() {
        UberItems.putItem("empty_item", new empty_item(Material.DIAMOND, "Empty UberItem", UberRarity.COMMON,
                false, false, false, Collections.emptyList(), null));

        UberItems.putItem("farmers_scythe", new farmers_scythe(Material.DIAMOND_HOE, "Farmer's Scythe",
                UberRarity.UNFINISHED, false, false, false,
                Arrays.asList(
                        new UberAbility("Sweeping Motion", AbilityType.LEFT_CLICK, "Break crops in a 3x3 block square with a single swing!"),
                        new UberAbility("Fruitful Harvest", AbilityType.NONE, "Automatically replant broken crops, drops go directly into the player inventory")),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_diamond").makeItem(1),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_diamond").makeItem(1),
                        new ItemStack(Material.WOODEN_HOE),
                        UberItems.getMaterial("enchanted_diamond").makeItem(1),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_diamond").makeItem(1),
                        new ItemStack(Material.AIR)), false, 1)));

        UberItems.putItem("seed_pouch", new seed_pouch(Utilities.getSkull("http://textures.minecraft.net/texture/4a3890aa8c76f177cbae7dc0966149a2e0fb1c1efeb283deb7deaa09fd0fb6"), "Seed Pouch",
                UberRarity.UNFINISHED, false, false, false,
                Collections.singletonList(new UberAbility("Windbound Seeds", AbilityType.RIGHT_CLICK, "Plant seeds on all connected farmland")),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.LEATHER, 16),
                        UberItems.getMaterial("enchanted_seed").makeItem(1),
                        new ItemStack(Material.LEATHER, 16),
                        UberItems.getMaterial("enchanted_seed").makeItem(1),
                        new ItemStack(Material.SHULKER_BOX),
                        UberItems.getMaterial("enchanted_seed").makeItem(1),
                        new ItemStack(Material.LEATHER, 16),
                        UberItems.getMaterial("enchanted_seed").makeItem(1),
                        new ItemStack(Material.LEATHER, 16)), false, 1)));

        UberItems.putItem("enchanted_clock", new enchanted_clock(Material.CLOCK, "Enchanted Clock", UberRarity.UNFINISHED,
                false, false, true,
                Collections.singletonList(new UberAbility("Hyperbolic Time", AbilityType.RIGHT_CLICK, "Time moves faster in a 10 block radius, consumes redstone")), null));

    }
    private void registerUberMaterials() {
        UberItems.putMaterial("enchanted_seed", new UberMaterial(Material.WHEAT_SEEDS,
                "Enchanted Seed", UberRarity.UNCOMMON, true, true, false, "",
                gser(Material.WHEAT_SEEDS)));
    }

    // "Generate Standard Enchanted Recipe"
    // generate the standard recipe for an enchanted Uber Material (160 of the base item)
    private static UberCraftingRecipe gser(ItemStack ingredient) {
        ItemStack newIngredient = ingredient.clone();
        newIngredient.setAmount(32);

        return new UberCraftingRecipe(Arrays.asList(
                new ItemStack(Material.AIR),
                newIngredient,
                new ItemStack(Material.AIR),
                newIngredient,
                newIngredient,
                newIngredient,
                new ItemStack(Material.AIR),
                newIngredient,
                new ItemStack(Material.AIR)), true, 1);
    }
    private static UberCraftingRecipe gser(Material material) {
        return gser(new ItemStack(material));
    }
}