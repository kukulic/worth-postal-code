# Worth postal code

Application for administration of postal offices in UK

## Getting started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
 
 
### Prerequisites

Project lombok is used in project so it needs to be enabled in your IDE.
For Intellij:

First install project Lombok plugins
File -> Settings -> Plugins

Next enable annotation processing
File -> Settings -> Annotation Processors -> check "Enable annotation processing" 
 
### Installing

After making git clone next step will be to write in terminal

```bash
gradlew build
```

This will download all dependencies for backend and frontend into your project

### Running from CMD

Next step is to run backend of project which will be running by default on port 8080

```bash
gradlew bootrun
```

After this, in new terminal, line below needs to be executed in order to run frontend by default on port 3000 

```bash
npm run start --prefix frontend
```

### Running with Docker

```bash
docker build -t worth-postal-code:latest .
docker run -d  -p 8080:8080 worth-postal-code:latest
```
