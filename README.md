# Azure + Spring POC

Project initalized using https://start.spring.io/

## Docker
### Instructions
*See this page for additional Docker documentation:* https://spring.io/guides/gs/spring-boot-docker/ 

1. Install `Docker` and ensure the daemon is running
1. Build the maven project as a .jar 
    - (make `.jars`, not `.war`)
1. Build the `Docker` image by running `# docker build -t azure-poc .` in a terminal
    - `-t azure-poc` builds a `Docker` image and tags it as `azure-poc`
    - *Note:* the `#` denotes an administrator prompt, use `sudo` if you're on a *nix system
1. Run the `Docker` image you just built by running `# docker run azure-poc`
    
        