# **SillinessLimiter**

## Description

SillinessLimiter is a little strange iplock with the intention of providing freedom to the player while ensuring his safety and being simple

Useful for offline servers

It was meant to be used somewhere but left unused, so I decided to finish it and make it public.

Also includes customizable language file

![kick_example.jpg](kick_example.jpg)

## Commands

#### _/silly-limit_ 
-- limits your account to ip address you are currently on, needs confirmation. If you're server op or have access to console, you can limit someone to specific ip by arguments

#### _/silly-unlimit_ 
-- unlinks you account from any ip that's currently there, needs confirmation.

#### _/silly-list_ 
-- shows the list of linked ips to your account

![](.README_images/ece83f9d.png)

#### _/silly-confirm_
-- just to confirm changes, nothing else

#### _/silly-deny_
-- just to deny changes, nothing else

#### _/silly-reload_
-- config reload, op or console only

## Config

#### _Permission-by-default_
-- grants every permission of this plugin commands, except reload, true by default

#### _Login-link-message_
-- Display a message when you join if player ip is not linked, can be changed in [lang.yml](src%2Fmain%2Fresources%2Fdefault%2Flang.yml), true by default

#### _Max-IP-Allowed_
-- The maximum number of ip that can be linked to the same account, 4 by default

## Language
-- For more info about this one, check [lang.yml](src%2Fmain%2Fresources%2Fdefault%2Flang.yml)
