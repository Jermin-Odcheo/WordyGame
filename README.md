
Project from the FINALS CIS-2 IT222 Integrative Technologies Lab
---

# Wordy Game

**Longest word wins**



## Installation

### 1. Install Zulu Java 8 JDK

1. Go to the official Zulu website:  
   https://www.azul.com/downloads/zulu-community/  
2. Select **Java 8** for your OS and download the installer.

### 2. Extract the JDK

- Run the downloaded installer or unzip the archive to a directory of your choice (e.g., `C:\zulu8` or `~/zulu8`).

### 3. Configure your PATH

**Windows**  
1. Open _Control Panel_ → _System_ → _Advanced system settings_ → _Environment Variables_.  
2. Under **System variables**, select **Path** → **Edit** → **New**.  
3. Add the path to the JDK’s `bin` folder, e.g.:  
4. Click **OK** to save.

## macOS / Linux Installation

1. **Edit Shell Profile**  
   Open your shell profile in an editor, e.g.:  
   ```bash
   nano ~/.bash_profile   # or ~/.bashrc on some distros
   ```

2. **Add Zulu Java 8 to PATH**  
   Add the following line to your shell profile (adjust path as needed):  
   ```bash
   export PATH="$PATH:$HOME/zulu8/bin"
   ```

3. **Save and Reload**  
   Save the file and reload the profile:  
   ```bash
   source ~/.bash_profile
   ```

4. **Verify Installation**  
   Open a new terminal and run:  
   ```bash
   java -version
   ```  
   Expected output:  
   ```
   openjdk version "1.8.0_362"
   Zulu8
   ```

5. **Configure in IntelliJ IDEA (Optional)**  
   - Open IntelliJ IDEA.
   - Go to `File → Project Structure` (or press `Ctrl+Alt+Shift+S` / `⌘;`).
   - Under `Platform Settings`, select `SDKs → click + → JDK`.
   - Navigate to your Zulu Java 8 installation directory and click `OK`.
   - Name it (e.g., “Zulu Java 8”) and click `Finish`.

## Running the Game

Wordy Game uses CORBA for networking. Follow these steps to start the ORB daemon, server, and client.

### Start the CORBA ORB Daemon

1. Open a terminal (macOS/Linux) or Command Prompt (Windows).
2. Run:  
   ```bash
   start orbd -ORBInitialPort 1050
   ```
3. Keep this window open to facilitate CORBA communications.

### Launch the Server

1. In IntelliJ IDEA (or your IDE), locate the `Server` class in the project tree.
2. Right-click on `Server.java` → `Run ‘Server.main()’`.
3. The server console will display logs confirming it’s listening on port 1050.

### Launch the Client

1. Obtain your server’s IP address.
2. Open `Client.java`. On the line initializing the ORB (around line 20), replace:  
   ```java
   ORB orb = ORB.init(new String[]{
       "-ORBInitialHost", "xxx.xxx.xxx.xxx",
       "-ORBInitialPort", "1050"
   }, null);
   ```  
   with your server’s IP, e.g.:  
   ```java
   ORB orb = ORB.init(new String[]{
       "-ORBInitialHost", "192.168.1.42",
       "-ORBInitialPort", "1050"
   }, null);
   ```
3. Right-click on `Client.java` → `Run ‘Client.main()’`.

### Team
---
The following individuals have contributed to the development of the Wordy Game project:
1. ANNE MARIE FACTOR
2. JAN MICHAEL VILLANUEVA
3. JOSH MARCO RABINO
4. KENNELY RAY BUCANG
