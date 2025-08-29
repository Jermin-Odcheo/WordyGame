INSTALLING ZULU FILE:
Step 1: Download Zulu Java Development Kit (JDK)
Go to the official Zulu website: https://www.azul.com/downloads/zulu-community/
Choose the appropriate version of Zulu Java Development Kit (JDK) for your operating system (Windows, macOS, or Linux).
Select the Java 8 version and click on the download link to download the installer.

Step 2: Extract the JDK Archive
Once the installer is downloaded, extract the contents of the JDK archive to a directory of your choice.
Make a note of the JDK installation directory, as you will need it later.

Step 3: Set Up Environment Variable
Open your system's environment variable settings:
On Windows: Go to Control Panel → System → Advanced system settings → Environment Variables.
On macOS/Linux: Open a terminal and enter the command nano ~/.bash_profile (or nano ~/.bashrc for some Linux distributions).
In the environment variable settings, locate the "PATH" variable under the System Variables and edit it.
Add a new entry to the "PATH" variable by appending the path to the Zulu Java 8 JDK bin directory. For example, if your JDK installation directory is C:\zulu8, you would add C:\zulu8\bin to the "PATH" variable.
Save the changes and exit the environment variable settings.

Step 4: Verify the Installation
Open a new terminal or command prompt window.
Run the command java -version to verify that the Zulu Java 8 JDK is accessible.
If the command displays the Java version information, the installation was successful.

Step 5: Configure JDK in IntelliJ IDEA
Open IntelliJ IDEA.
Go to "File" → "Project Structure" (or press "Ctrl+Alt+Shift+S" on Windows/Linux or "⌘;⌘;⌘S" on macOS).
In the "Project Structure" dialog, click on "SDKs" under the "Platform Settings" section on the left.
Click on the "+" button on the right side to add a new JDK.
In the "Add SDK" dialog, select "JDK" and click "Next".
Choose the JDK home directory by clicking on the "..." button and navigating to the Zulu Java 8 JDK installation directory you noted earlier.
Select the JDK folder and click "OK".
Give a name to the JDK (e.g., "Zulu Java 8") and click "Finish".
Close the "Project Structure" dialog.

EASIEST WAY TO RUN (NO ORBD, NO IP EDITS):

1) Start the Server (writes an IOR file)
   - Optional env (PowerShell):
     - $env:WORDY_SERVER_PORT = "3700"        # server listen port
     - $env:WORDY_IOR_PATH    = "wordy.ior"    # where to write IOR file
     - $env:WORDY_DB_URL/USER/PASS (if using DB)
     - For multi‑PC use, also set: $env:WORDY_ORB_HOST = "<server-lan-ip>"
   - Run Server_Java/Server.main() in your IDE.
   - The console prints the path to the generated IOR file (e.g., wordy.ior).

2) Start the Client (reads the IOR file)
   - Place/copy the IOR file where the client can read it.
   - Optional env (PowerShell): $env:WORDY_IOR_PATH = "path\to\wordy.ior"
     (defaults to .\wordy.ior if not set)
   - Run Client_Java/Client.main() in your IDE.
   - No need to run orbd or change any IP in the code.

NOTES:
- For same‑machine runs, defaults work out of the box.
- For different machines, ensure the server sets WORDY_ORB_HOST to its LAN IP so the IOR contains a reachable address, and allow the chosen port through firewall.
- If the client can’t find the IOR file, it will fall back to using the NameService (below).

RUNNING WITH NAMESERVICE (OPTIONAL/LEGACY):

Step 1: Start NameService
        Open Start Menu → search CMD → Enter
        Enter the command:
        start orbd -ORBInitialPort 1050
        Keep this window open.

Step 2: Start the Server Class in IntelliJ IDEA
        (Optional env) set WORDY_ORB_HOST / WORDY_ORB_PORT
        Locate Server_Java/Server.java and Run → Server.main()

Step 3: Start the Client
        (Optional env) set WORDY_ORB_HOST / WORDY_ORB_PORT to the server’s IP and 1050
        Locate Client_Java/Client.java and Run → Client.main()
        (You no longer need to edit the IP inside Client.java.)
