# Vehicle "rubber band" handler

Minecraft Java Edition 1.12.2 forge mod to pause player movement updates when riding a vehicle (Horse, probably Boat,
etc.) to avoid "rubber banding" and getting kicked from a server when server TPS is low or chunks are not loading fast
enough for your trusty steed. When the mod detects server packet delays, or you are about to enter an unloaded chunk,
movement is paused until the server is responding properly again.

### Forge for Minecraft 1.12.2

*You will need to have forge loader installed and enabled in your Minecraft 1.12.2 instance!*

### Stay safe ...

Read the source code, or ask somebody you know who can understand code to do this. It isn't a large mod and won't take
long.

#### Verify the SHA256 signature of the build tool jar file:

`gradle/wrapper/gradle-wrapper.jar`

This is important because you may be running this tool to build the mod jar and will want to know it is not back-doored.

On Linux, you can run this:

`sha256sum gradle/wrapper/gradle-wrapper.jar`

Otherwise, there are online tools like:

- https://www.google.com/search?q=sha256+file+generator
- https://emn178.github.io/online-tools/sha256_checksum.html
- http://onlinemd5.com/

Check the signature is the one from the build tool vendor listed on this page:

https://gradle.org/release-checksums/#v4.9

It should match the signature for Gradle version 4.9:

I.e: `e55e7e47a79e04c26363805b31e2f40b7a9cc89ea12113be7de750a3b2cede85`

### Build the mod jar ...

You will need to have Java JDK installed.

https://openjdk.java.net/install/

If you have the JDK installed then on Linux you can run this:

`./gradlew build`

On Windows you can run this:

`.\gradlew.bat build`

### Install the mod jar ...

After building, copy the compiled jar file to your minecraft instance directory.

On Linux, the file to copy should be here:

`build/linux/libs/whatever-1.0.jar`

On Windows, the file to copy should be here:

`build/libs/whatever-1.0.jar`

I don't know where exactly your Minecraft instance directory is, but it is the *parent* directory to where your
screenshots are.

Inside that parent directory put the jar into

`mods/1.12.2/`
