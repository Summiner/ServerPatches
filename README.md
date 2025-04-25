# ServerPatches
ServerPatches simply fixes crashes and other important exploits for older minecraft versions since paper doesn't have backwards compatibility. For 1.17-1.20+

> [!IMPORTANT]
> We have no plans to write support for any version below 1.17 and support for 1.19 and below will be dropped on September 22, 2024. After this date the API version will be updated to 1.20 and no patches will be written for versions prior to this, in addition all fixes for any crash prior to 1.20 could be removed at any time. **Private forks can be made for clients but you will be charged, email contact@jamie.rs if you are interested in this**. Thank you for your continued support <3

## Features:
- Fixes out of bounds button integers for inventory slot swap packets - EXAMPLE N/A
- Fixed data command exploits with invalid NBT, Caused by a [Paper patch breaking something](https://github.com/PaperMC/Paper/blob/9e171ef8ff0a0ec57ebc75772fc9de578c987059/patches/server/0647-Check-requirement-before-suggesting-root-nodes.patch#L22)
- Fixes a crash with the select bundle packet [Paper Patch](https://github.com/PaperMC/Paper/commit/a838a886dcbc93664283034a41673e802a6b3098)
  
## Credits:
- [Paper](https://github.com/PaperMC/) - Amazing community & Reference for multiple crash fixes
- [PacketEvents](https://github.com/retrooper/packetevents) - Amazing library we use to handle packets
- [Boosted-Yaml](https://github.com/dejvokep/boosted-yaml) - Cool library we use for configuration stuff

## Notes:
- If you know of any other exploits please open a [new issue](https://github.com/summiner/ServerPatches/issues/new) and give detail about the exploit
- In the provided examples under Features we do not directly or indirectly endorse the people who have made the videos/posts, they are simply to demonstrate the exploit
  
## BStats:
![bstats data](https://bstats.org/signatures/bukkit/ServerPatches.svg)
