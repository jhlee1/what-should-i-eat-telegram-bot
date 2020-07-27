# what-to-eat-telegram-bot

- I have created it for my team members who have a hard time to pick one for a lunch from the restaurants around my company 
(점심시간마다 식당을 고르기 힘들어하는 팀원들을 위해 제작)

## How to run it?
1. To run it, you should create a bot in telegram using bot father (bot father로 봇을 만들고)
2. Add the following profile information in application.yml (필요한 정보를 application.yml에 넣어주기)
3. Add the bot in your chat room (만들어진 Bot을 채팅방에 초대)

Ex) application.yml
```
spring:
  data:
    mongodb:
        uri: YOUR_MONGODB_URL

telegram.bot.name: YOUR_BOT_NAME
telegram.bot.key: YOUR_SECRET_KEY

```
