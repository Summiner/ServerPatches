# ServerPatches
This project simply fixes crashes and other important exploits for minecraft that PaperMC does not (typically for older versions)

## Features:
- Fixes the lectern exploit - [EXAMPLE](https://www.youtube.com/watch?v=SvdO8ZSHQdo)
- Fixes out of bounds inventory click packets - [EXAMPLE](https://www.youtube.com/watch?v=MIJR-nuwFi4)
- Fixes out of bounds button integers for inventory slot swap packets - EXAMPLE N/A
- Fixed data command exploits with invalid NBT, Caused by a [paper patch breaking something](https://github.com/PaperMC/Paper/blob/9e171ef8ff0a0ec57ebc75772fc9de578c987059/patches/server/0647-Check-requirement-before-suggesting-root-nodes.patch#L22)
  
## Credits:
- [Paper](https://github.com/PaperMC/) - Amazing community & Reference for multiple crash fixes
- [PacketEvents](https://github.com/retrooper/packetevents) - Amazing library we use to handle packets
- [Boosted-Yaml](https://github.com/dejvokep/boosted-yaml) - Cool library we use for configuration stuff

## Notes:
- If you know of any other exploits please open a [new issue](https://github.com/summiner/ServerPatches/issues/new) and give detail about the exploit
- In the provided examples under Features we do not directly or indirectly endorse the people who have made the videos/posts, they are simply to demonstrate the exploit
