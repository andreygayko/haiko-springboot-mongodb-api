Требуется написать рест сервис на базе фреймфорка Spring Boot 2.

В качестве хранилища данных должна быть база данных Mongo DB.

В качестве сборщика проекта должен быть Gradle.

API должен позволять читать, создавать и изменять обьект описывающий обучающее событие:

Activity{
   id
   title (mandatory, max 100)
   summary (mandatory, max 25)
   description (mandatory, max 200)
   start date time (mandatory, ISO Date Time)
   end date time (mandatory, ISO Date Time)
   info
}

Также должна вестить история изменения обучающего события:

History {
   dateTime (ISO Date Time)
   type (UPDATE, COMPOSE)
   changes [
      {
         fieldName
         oldValue
         newValue
      }
   ]
}

Результат прошу выложить в публичном гитхаб репозитории и прислать ссылку.