# Caching Proxy Tool

This is a CLI tool that starts a caching proxy server.
It will forward requests to the destination server, and cache the response(s).

If the same request is made again, it will return the cached response instead of simply forwarding the request to the server.


## Installation

The following steps details the installation process of this application:

### Clone the repository

    git clone https://github.com/islajr/caching-proxy-tool

### Clean and package the application

Once inside the repository directory, the next step is to package the application into a .jar file.

Depending on your Operating System, you'll need to use different commands:

* Windows


    .\mvnw.cmd clean

    .\mvnmw.cmd package


* Linux and MacOS


    ./mvnw clean

    ./mvnw package


This will clean, compile and package the source code to a **/target** folder within the project root folder.



## Usage

To run the application, simply run the command:


* Linux and MacOS:


    java -jar /target/caching-proxy-tool-0.0.1-SNAPSHOT.jar caching-proxy --port=<port> --origin=<origin>

Windows:

    java -jar \target\caching-proxy-tool-0.0.1-SNAPSHOT.jar caching-proxy --port=<port> --origin=<origin>

