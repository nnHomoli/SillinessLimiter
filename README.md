# **SillinessLimiter**

## Description

SillinessLimiter is a little strange iplock with the intention of providing freedom to the player while ensuring his safety and being simple

Useful for offline servers

It was meant to be used somewhere but left unused, so I decided to finish it and make it public.

Also includes customizable language file

![kick_example.jpg](kick_example.jpg)

## Commands

#### _/silly-limit_ 
-- links your account to ip address you are currently on, needs confirmation. Can be used with an argument to link a specific IP

#### _/silly-unlimit_ 
-- if linked, it unlinks your account from the currently used IP and needs confirmation. Can be used with an argument to unlink a specific IP

#### _/silly-list_ 
-- shows the list of linked ips to your account

![](.README_images/ece83f9d.png)

#### _/silly-confirm_
-- just to confirm changes, nothing else

#### _/silly-deny_
-- just to deny changes, nothing else

#### _/silly-reload_
-- config and language reload, server op and console only

## Config

#### _Permission-by-default_ ## true/false
-- grants every permission of this plugin commands, except reload, true by default

#### _Login-link-message_ ## true/false
-- Display a message when player whose IP is not linked joins, can be changed in [lang.yml](src%2Fmain%2Fresources%2Fdefault%2Flang.yml), true by default

#### _Max-IP-Allowed_ ## Int value
-- The maximum number of IP that can be linked to the same account, 4 by default

#### _check-after-confirm_ ## true/false
-- Check if player IP is still linked after confirm, false by default

## Language
-- For more info about this one, check [lang.yml](src%2Fmain%2Fresources%2Fdefault%2Flang.yml)
