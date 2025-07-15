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

RUNNING THE SERVER:

Step 1: Click your Start menu. Its default location will be on the bottom left of your screen unless you have moved your taskbar.
        Click your settings button. This will usually be in the shape of a cog.
        Click Network & Internet.
        Click VPN/Virtual Network found in the left menu.
        Select the VPN/Virtual Network you wish to disable.
        Click Disconnect.

Step 2: Open Start Menu
        Search CMD and press Enter
        Enter the following command to start the ORB daemon with the specified port number:
        "start orbd -ORBInitialPort 1050"
        Press Enter to execute the command.
        The ORB daemon will start, allowing the server and clients to communicate using the specified port.

Step 3: Start the Server Class in IntelliJ IDEA
        Locate the Server class in your project's source code.
        Right-click on the Server class and select "Run" or "Debug" to start the server.
        IntelliJ IDEA will compile and execute the Server class, starting the server application.

RUNNING THE CLIENT:
    Step 1: Obtain the IP address of the server
    Step 2: Go to Client Class
    Step 3: On the line number 20:
    ORB orb = ORB.init(new String[]{"-ORBInitialHost", "xxx.xxx.xxx.xxx", "-ORBInitialPort", "1050"}, null);
    change the xxx.xxx.xxx.xxx to the IP address of the server that you obtained
    Step 4: Start the Client by right-clicking on the Client class and Select "Run" or "Debug" to start the Client