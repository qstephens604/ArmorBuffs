# ArmorBuffs Paper 1.21.7 
ArmorBuffs is a simple plugin made to give player wearing full sets of armor specific Buffs (Potion Effecs)! This works by tagging armor pieces with the ID's you create in the config.yml (See example below). To You can add as many Armor Sets with as many effects as you so please! 

### AT THIS POINT ALL EFFECTS ARE PERMANENT. THIS WILL CHANGE!

<img width="634" height="348" alt="image" src="https://github.com/user-attachments/assets/0b148d36-b720-4480-9707-51620f0ebf40" />

## Commands
`/armorbuffs tag <setId>` - Tags the currently held item!

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
  # (setId) Name / ID of the Armor Set! 
  example_set:
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
