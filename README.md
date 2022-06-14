# Nearest holiday checker

## Info

Application use [Holiday Api](https://holidayapi.com). With free api key you can only use previous year data.

## Before start

1. Open file `src/main/resources/application.properties`
2. Find variable `holidays.apiKey`
3. Fill it with correct apiKey

## How to run

I propose to use gradle command `bootBuildImage` as it use buildpacks

1. `./gradlew bootBuildImage --imageName=bartek/holidays_api`
2. `docker run -p 8080:8080 -t bartek/holidays_api`

## Example request

```bash
curl -X GET --location "http://localhost:8080/holidays?country1=PL&country2=NO&date=2021-06-14"
```

## Example response

```json
{
  "date": "2021-06-21",
  "name1": "June Solstice,Kupala Night",
  "name2": "June Solstice"
}
```