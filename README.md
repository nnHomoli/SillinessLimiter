# **SillinessLimiter**

## Description

SillinessLimiter is a little strange iplock with the intention of providing freedom to the player while ensuring his safety and being simple

useful for offline server
Meant to be used somewhere else, but left unused, so I decided to finish it and make it public

## Commands

#### _/silly-limit_ 
-- limits your name to ip address you are currently on, however if you have op or access to console, you can limit someone to specific ip by arguments

#### _/silly-unlimit_ 
-- unlinks you name from any ip that's currently there, needs confirmation. If you're server op or have access to console, you can unlink someone's ip without confirmation

#### _/silly-move_
-- command for players to safely change their linked ip to whatever they want, needs confirmation

#### _/silly-confirm_
-- just to confirm changes, nothing else

#### _/silly-deny_
-- just to deny changes, nothing else

#### _/silly-reload_
-- config reload, op or console only

## Config

#### _Permission-by-default_
-- grants every permission of this plugin commands, except reload. True by default

#### _Login-link-message_
-- send a message to the player that has no ip linked. True by default
