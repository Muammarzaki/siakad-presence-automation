# SIAKAD-PRESENCE-AUTOMATION

project ini adalah tool untuk melakukan absen secara otomatis tetapi tidak akurat pada (bisa saja telat tetapi pasti terabsen)

## Feature

- login ke siakad
- click absen button
- schedule presence each 30 minute

## Endpoint

- change new configuration
- add freeDay

## Require Enviroment

- [chromedriver](https://chromedriver.chromium.org/downloads)

## Enviroment Variable

- Require
  - AUTH_USERNAME
    > username for system login and using for access api
  - AUTH_PASSWORD
    > password for system login and using for access api
  - SIAKAD_USERNAME
    > username of siakad exp: 220705xx
  - SIAKAD_PASSWORD
    > password of siakad
  - WEBDRIVER
    > its for Path cromedriver
    > default its ./chromedriver.exe
- Optional
  - PROFILE
    > its for set the profile and change application mode
    >> Option profile
    >>
    >> - dev
    >>
    >>> log debug
    >>
    >> - prod
    >>
    >>> chromedriver headless mode
    >
    > default its dev

  - SCHEDULE_CONFIG
    > path for json file
    > default classpath:schedule-time-config.json
  - ARGS
    > add some argument for ChromeDriver
  - HEADLESS
    > make chromeDriver running at background
    > default false
  - TIMEOUT (minute)
    > timeout load chromedriver
    > default 1
  - VERBOSE
    > set verbose for chromedriver
    > default false

## Distributin Application

- Jar File
- Docker image

## example

- Regular UseCase

  ```bash
  java -jar "path/to/jar" \
  --USERNAME="22070XXXX" \
  --PASSWORD="*******" \
  --WEBDRIVER="path/to/wedriver.exe"  \
  --SCHEDULE_CONFIG="path/to/jsonfile/"
  ```

- Background running

  ```bash
  java -jar "path/to/jar" \
  --USERNAME="22070XXXX" \
  --PASSWORD="*******" \
  --WEBDRIVER="path/to/wedriver.exe"  \
  --SCHEDULE_CONFIG="path/to/jsonfile/" \
  --HEADLESS="true" \
  ```

  or

  ```bash
  java -jar "path/to/jar" \
  --USERNAME="22070XXXX" \
  --PASSWORD="*******" \
  --WEBDRIVER="path/to/wedriver.exe"  \
  --SCHEDULE_CONFIG="path/to/jsonfile/" \
  --ARGS="--headless" \
  ```
