# push-to-slack
When posted on tech blog sites, it will deliver to your slack.
To see or not to see is your choice.

## How to run
### Init database
Create database using db.migration schema

    ./gradlew flywayClean -PflywayUsername="yourname" -PflywayPassword="yourpass"
    ./gradlew flywayMigrate -PflywayUsername="yourname" -PflywayPassword="yourpass"
    
### run server
    SPRING_DATASOURCE_USERNAME="yourname" \
        SPRING_DATASOURCE_PASSWORD="yourpass" \
        SLACK_WEBHOOK_URL="yourslack" \
        ./gradlew bootRun