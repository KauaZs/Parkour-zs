package org.kauazs.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kauazs.Parkour_zs;
import org.kauazs.utils.ItemManager;
import org.kauazs.utils.ItemManager.*;

import java.io.File;

import static org.kauazs.managers.ParkourManager.bootParkour;
import static org.kauazs.utils.ItemManager.ItemCreate;
import static org.kauazs.utils.ItemManager.SetItem;

public class CreateSetup implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = ((Player) sender).getPlayer();
            if (args.length < 1) {
                p.sendMessage(ChatColor.RED + "Erro: Insira um ID para o parkour, exemplo /create-parkour 01");
                return false;
            }

            File file = new File(Parkour_zs.getInstance().getDataFolder(), "config.yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            String ID = args[0];


            if (config.contains("config.parkours." + ID)) {
                p.sendMessage(ChatColor.RED + "Erro: Já existe um parkour com este ID");
                return false;
            }
            p.getInventory().clear();

            ItemStack redstone = ItemCreate(Material.REDSTONE_BLOCK, ChatColor.BLUE + "Início");
            ItemStack gold = ItemCreate(Material.GOLD_BLOCK, ChatColor.GREEN + "Checkpoint");
            ItemStack wool = ItemCreate(Material.WOOL, ChatColor.GREEN + "Fim do parkour");
            ItemStack barrier = ItemCreate(Material.BARRIER,ChatColor.RED + "Cancelar");

            SetItem(p, 0, redstone);
            SetItem(p, 4, gold);
            SetItem(p, 7, wool);
            SetItem(p, 8, barrier);

            bootParkour(p, ID);
        }
        return true;
    }
}
