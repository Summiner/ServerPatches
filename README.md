# ServerPatches
ServerPatches simply fixes crashes and other important exploits for older minecraft versions since paper doesn't have backwards compatibility. For 1.19-1.21+

> [!CAUTION]
> The latest and all future downloads have been moved. Releases will exist on GitHub without a pre-built version. Thank you for understanding. [Download Here](https://modrinth.com/plugin/serverpatches)

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
