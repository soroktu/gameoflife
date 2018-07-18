#Description

Application written on Spring Boot 2.0 with JPA to simulate Game of Life

#Usage
To start app just checkout project and run 
    
    gradlew bootRun

After application starts you can open localhost:8080 in your browser

There is several choices to define start field
1. Set Matrix at gameview.js
2. Change live cells coordinates in data.sql script & click "Load from DB" button after application starts
3. Click on board cells after web page loads

Application writes field state to DB after every turn, so any moment you can refresh page and click "Load from DB". Cause of use H2 (in memory database) all data will be lost after server stops.