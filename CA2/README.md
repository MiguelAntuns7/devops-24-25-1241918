# Class Assignment 2

### Introduction

This technical report outlines the procedures and results of Class Assignment 2 – Part 1, which focused on virtualization. The main goal was to gain practical experience in setting up and managing virtual environments, which is a fundamental skill for modern development and operations workflows.

The report details the creation and configuration of a virtual machine running Ubuntu, the setup of a Java-based development environment, and the execution of the projects previously developed in the last assignment. Emphasis is placed on the challenges faced during environment setup and how those were overcome inside the VM.

In addition to completing the required setup steps, particular attention was given to diagnosing and resolving environment-specific issues, which is an inevitable part of real-world DevOps workflows. This included dealing with version mismatches, build configuration errors, and Operative System differences between host and guest environments. By overcoming these obstacles, I gained valuable insight into the practical challenges of environment replication and consistency, which are essential for achieving reliability and portability across development, testing, and deployment pipelines.

## Part 1

## Creating a VM

The first step was to configure a Virtual Machine (VM). I already had a Linux VM set-up from my previous Computation Systems and Networks class, which meant that this issue was already tackled for me.
Although my virtual environment was set up with VMware, if you wish to use a different hypervisor, or if you do not have any, you can use the free versions of either one of these hypervisors: VMware, VirtualBox, Hyper-V.
The Linux Distribution (Distro) system that we were supposed to use for this assignment was Ubuntu.

After setting up a hypervisor of your choice, you should inject an image (ISO) with the minimal installation media (https://help.ubuntu.com/community/Installation/MinimalCD).
You should also attribute your Virtual Machine 2048 MB of RAM.


## Network Settings and Configurations

After having that all of that set up, you should create a Host-Only Network Adapter.

In __VMware__, you should go into the top right corner and click "Edit", and then "Virtual Network Editor". You should then click on "Add Network" and make it Host-Only, so that our Virtual Machine (guest) can communicate with our physical machine (host).

In __VirtualBox__, you should go to the Main Menu, select File -> Host Network Manager (Ctrl + W). When you see an empty white box saying "Host-Only Networks", you should click the "Create" button (Ctrl + A), creating a new Host-Only Network.

After having the Network Adapter set up, you should check the IP address range, which was ``192.168.56.1/24``. I chose ``192.168.56.5``, as it falls within the designated subnet.

After setting up the VM, we should update the package repositories with ``sudo apt update``.
Afterward, you should install the network tools (net-tools) package with ``sudo apt install net-tools`` to facilitate network configuration. This was already installed on my guest machine.

You should then assign the chosen IP address by editing the network configuration file. You can do this by executing ``sudo nano /etc/netplan/01-netcfg.yaml``.
After that, you should apply those changes with ``sudo netplan apply``.

To enable remote access of the Virtual Machine, you should install and configure the OpenSSH server. This should be done using the following command: ``sudo apt install openssh-server``.
After installation, you should modify the SSH configuration to allow password-based authentication. This involved editing the SSH daemon config file: ``sudo nano /etc/ssh/sshd_config``.
Inside the file, locate and uncomment the following line: ``PasswordAuthentication yes``.
After saving the changes, restart the SSH service to apply the newly introduced changes with ``sudo service ssh restart``.

This setup allows you to securely access the VM from your host machine or other devices within the same network, using an SSH client and the IP address previously configured on the Host-Only network adapter.

To facilitate file transfers between host and guest, I installed and configured an FTP server using vsftpd (Very Secure FTP Daemon). You can use the following command: ``sudo apt install vsftpd``.

Once installed, you should modify the configuration to allow write access enabling the ability to upload files to the VM.
This was done by editing the FTP file: ``sudo nano /etc/vsftpd.conf``.
Inside the file, locate and uncomment the following line: ``write_enable=YES``.

Now you are able to use an FTP client (like FileZilla, for example) from your physical machine to your virtual machine using its static IP address and transfer files in both directions.


## Cloning my private repository

To clone my own CA1 repository to inside my virtual machine, I configured SSH-based authentication. This allowed the VM to communicate with GitHub without requiring username and/or password.

1. Generate an SSH key

First of all, generate an SSH key by typing: ``ssh-keygen -t ed25519 -C "insert your email"``.
You can accept the default file location (``~/.ssh/id_ed25519``) and decide whether to add a password to access it or not. I did not.

You can verify if the key was correctly saved with: ``ls ~/.ssh/``.

![img.png](ImagesCA2/img.png)

2. Add the SSH key to GitHub

To authorize the VM, I accessed the public key with: ``cat ~/.ssh/id_ed25519.pub``.
I copied the output and added it to my GitHub account by navigating into:

```
GitHub → Settings → SSH and GPG keys → New SSH key
```

I named it ``VM SSH key`` and proceeded to paste my SSH key.

3. Clone the repository

Once the SSH key was added to GitHub, I cloned the repository into the VM with: ``git clone git@github.com:miguelantuns7/devops-24-25-1241918.git``.


## Set Up Development Environment

With the VM fully configured, I proceeded to install the essential dependencies/tools required to work with my java projects.
I started by updating the package list and upgrading existing software with:

```
sudo apt update
sudo apt upgrade
```

Then, I installed Git to enable source code management with: ``sudo apt install git``.

Since the projects target Java 17, I installed the appropriate java version with:
``sudo apt install openjdk-17-jdk openjdk-17-jre``.

Although some projects are configured to use Maven or Gradle, these are managed via their respective build tool wrappers (``mvnw`` and ``gradlew``).
Therefore, installing Maven or Gradle globally was not strictly necessary.

**Note: If you want to use Gradle outside of a wrapper, you can manually installed version 8.6 with ``sdk install gradle 8.13``, extracting it to /opt/gradle, and updating the PATH. This step is totally optional.**

To verify that everything was properly installed and accessible, I checked the versions of the key tools:

```
git --version
java --version
```

## Executing the repository's projects

In this section I will be showcasing the Class Assignment 1's projects running in the guest machine (the Linux VM).

### Executing the Spring-Boot and React Web App

One of the first tasks inside the VM was to run the Sprint Boot tutorial project developed in the previous assignment. The aim was to verify that the VM's Java environment could properly support a Maven-based Sprint Boot web application.

I started by _cd'ing_ into the project's ``basic`` folder inside CA1 - part1, which contains the Spring Boot source code along with the Maven wrapper script.

To run the application, I used the provided Maven wrapper. When already inside the basic folder, run ``./mvnw spring-boot:run`` to have the web application running locally.

Once the server started, access the running web app from your browser. Since the VM was configured with a static IP address on a Host-Only network (192.168.56.5), I simply visited http://192.168.56.5:8080/.

![img.png](ImagesCA2/img2.png)


### Executing the Gradle Chat App

Now I will be attempting to run the ``gradle_basic_demo`` from the CA1's part 2.
It consists of a client-server chat application built with Gradle.

Inside the VM, I navigated into the gradle_basic_demo's root project directory and built the project with ``./gradlew build``.

![img.png](ImagesCA2/img3.png)

After that, I intended to run the server with ``./gradlew runServer``, but ran into a problem with the java version. This chat app project is using java 17, which I, as you could see before in this ReadMe, made sure to have installed. But turns out that, when I ran ``java --version``, the system was recognizing java 23.
When trying to do ``./gradlew runServer``, the system threw the following error:

```
Value C:/Users/Miguel/.jdks/corretto-17.0.14 given for org.gradle.java.home
Gradle property is invalid.
```

This was confusing at first, because I ran into no issues when running the project through IntelliJ in my physical machine.

First, I did ``readlink -f $(which java)`` in order to see the real path of the current Java version being used under the hood. It confirmed to me that the system was using Java 23.

After inspecting the project setup, I discovered that the file ``gradle.properties`` was explicitly setting the Java home path to a Windows location, which in Linux obviously isn't possible. To ensure the Virtual Machine was pointing to the correct Java version, I:

- Did ``sudo update-alternatives --config java`` to list all the available Java versions the system had. I switched from Java 23 to 17.

- With ``nano`` as the text editor, I edited the ``gradle.properties`` file to comment out the invalid Windows path:

```
# org.gradle.java.home=C:/Users/Miguel/.jdks/corretto-17.0.14
```

This removed the hardcoded Java path in the project's configuration.

After these adjustments, I ran ``./gradlew runServer`` and the server launched successfully, confirming that the VM was now using Java 17 and that Gradle was no longer pointing to the wrong version.

![img.png](ImagesCA2/img5.png)

This was useful in understanding how configuration files influence build tools like Gradle with the previously handled ``gradle.properties`` file.

Considering the project is now ready to be executed, and the server is already running in my guest machine, I opened Windows Powershell, navigated into CA1/part2's root directory and ran the client-side on my Host machine with ``./gradlew runClient --args="192.168.56.5 59001"``.
This allowed the client on my physical machine to communicate with the server running in the VM by specifying the VM's IP address and respective port number.

The chat app was then working as expected:

![img.png](ImagesCA2/img4.png)


### Executing the Gradle-versioned Spring-Boot and React Web App

In this part, I showcase how it was possible to run the Gradle version of the Spring Boot application.

1. I navigated into ``devops-24-25-1241918/CA1/part3/react-and-spring-data-rest-basic``.

2. Did ``./gradlew build`` to build the Part 3 project, and ran the web app with ``./gradlew bootRun``.

Once the server was running in the VM, and just like the Maven version of the web app, I simply accessed it locally in http://192.168.56.5:8080/.


## Conclusion of Part 1

This report covered the setup and use of a virtual machine to execute the previously developed Java-based projects in a controlled environment. The process included configuring network access, secure file transfer, and installing development tools required for building and running Spring Boot and Gradle applications.

While running the Gradle chat app, I ran into an issue with Java versions. Even though I had Java 17 installed, the VM was still defaulting to Java 23, and Gradle was picking up the wrong path. Fixing this taught me how Gradle pulls configuration from both the system and the project's configuration files.

Overall, this assignment gave me practical experience with virtualization and environment management, both of which are extremely important DevOps skills in today's world.