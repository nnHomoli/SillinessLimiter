# **SillinessLimiter**

## Description

SillinessLimiter is a little strange IP address based authentication with the intention of providing an easy choice to the player
of how they want to secure their nickname on an offline server while also not being annoying

Also includes customizable language file and dynamic IP support

This plugin doesn't use any of the NMS stuff, so it should work on other versions too

However, tested versions can be found on [Modrinth page](https://modrinth.com/plugin/sillinesslimiter/versions)

![kick-example.jpg](kick-example.jpg)

## Commands
```
/silly-help
```
- displays in-game command documentation, only including basic ones 
![help-example.jpg](help-example.jpg)
  
permission: nnhomoli.sillinesslimiter.cmds.sillyhelp
```
/silly-limit
```
- links your nickname to IP address you are currently on, needs confirmation. Can be used with an argument to link a specific IP

permission: nnhomoli.sillinesslimiter.cmds.sillylimit
```
/silly-unlimit
```
- if linked, it unlinks your nickname from the currently used IP and needs confirmation. Can be used with an argument to unlink a specific IP

permission: nnhomoli.sillinesslimiter.cmds.sillyunlimit
```
/silly-list
```
- shows the list of linked IPs to your nickname
![](list-example.jpg)

permission: nnhomoli.sillinesslimiter.cmds.sillylist
```
/silly-confirm
```
- just to confirm changes, nothing else

permission: nnhomoli.sillinesslimiter.cmds.sillyconfirm
```
/silly-deny
```
- just to deny changes, nothing else

permission: nnhomoli.sillinesslimiter.cmds.sillydeny
```
/silly-reload
```
- config and language reload, server op and console only

permission: nnhomoli.sillinesslimiter.cmds.sillyreload
```
/silly-switch
```
- switches the auth status for the user individually, needs confirmation

permission: nnhomoli.sillinesslimiter.cmds.sillyswitch
```
/silly-dynamic-limit
```
- links your nickname to the dynamic ip, supports arguments. If used without arguments, will default to the second octet of current IP, needs confirmation

permission: nnhomoli.sillinesslimiter.cmds.sillydynamiclimit
```
/silly-dynamic-unlimit
```
- unlinks the dynamic ip from your nickname, needs confirmation 

permission: nnhomoli.sillinesslimiter.cmds.sillydynamicunlimit
___
## **Config**

```
Permission-by-defaul ## true/false
```
- Grants every permission of this plugin commands, except reload, true by default

```
Login-link-message ## true/false
```
- Display a message when the player joins without linked IP, this can be changed in lang.yml. True by default

```
Max-IP-Allowed ## Int value
```
- The maximum number of IP that can be linked to the same nickname, 4 by default

```
check-after-confirm ## true/false
```
- Check if player IP is still linked after confirm, false by default

```
confirmation-timeout ## Int value
```
- Time in seconds after which player confirmation request will be removed , 180 by default

## Language
- For more info about this one, check [lang.yml](src%2Fmain%2Fresources%2Fdefault%2Flang.yml)

## Player data
- For more info about this one, check [data.yml](src%2Fmain%2Fresources%2Fdefault%2Fdata.yml)

