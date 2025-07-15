# ArmorBuffs Paper 1.21.7 
ArmorBuffs is a simple plugin made to give player wearing full sets of armor specific Buffs (Potion Effecs)! It works by searching the Lore for a specific Keyword. The keyword must be on all 4 pieces, and match in order for a player to recieve the effect. You can add as many Armor Sets with as many effects as you so please! 

### AT THIS POINT ALL EFFECTS ARE PERMANENT. THIS WILL CHANGE!

<img width="634" height="348" alt="image" src="https://github.com/user-attachments/assets/0b148d36-b720-4480-9707-51620f0ebf40" />

Keyword Must be in the Items Lore! The Items name does not matter!

## Commands
`/armorbuffs reload` - Updates the plugin with any changes made to the config! 

## Example Config

```
#                                     ____         __  __         _____             __ _       
#     /\                             |  _ \       / _|/ _|       / ____|           / _(_)      
#    /  \   _ __ _ __ ___   ___  _ __| |_) |_   _| |_| |_ ___   | |     ___  _ __ | |_ _  __ _ 
#   / /\ \ | '__| '_ ` _ \ / _ \| '__|  _ <| | | |  _|  _/ __|  | |    / _ \| '_ \|  _| |/ _` |
#  / ____ \| |  | | | | | | (_) | |  | |_) | |_| | | | | \__ \  | |___| (_) | | | | | | | (_| |
# /_/    \_\_|  |_| |_| |_|\___/|_|  |____/ \__,_|_| |_| |___/   \_____\___/|_| |_|_| |_|\__, |
#                                                                                         __/ |
#                                                                                        |___/ 
#
# Create as many Armor Sets as you please!

armor-sets:
  # Internal name for the Armor Set
  example_set:
    # Make sure this keyword is in all pieces of the Armor Set's Lore
    # Keyword the plugin will look for in ALL pieces of armor.
    keyword: "Example"
    # Potion Effects to give the player wearing a FULL SET
    effects:
      # AT THIS POINT IN TIME ALL EFFECTS ARE PERMANENT. I MAY REMOVE THE DURATION OPTION COMPLETELY IN THE FUTURE.
      # All Available Effects https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/potion/PotionEffectType.html
      - type: SPEED
        # Config Level 0 = In Game Level 1
        level: 0
        # Duration in seconds, set to -1 for permanent/repeating effect
        duration: -1
      - type: REGENERATION
        level: 1
        duration: 60
```
