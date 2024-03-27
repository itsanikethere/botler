![Bot Logo](https://cdn.jsdelivr.net/gh/itsanikethere/botler@master/assets/logo.png)

# Botler - Your personal DJ

Botler is a Discord bot built to play songs from YouTube with control features.

## Features

- Play song by search term or url
- Play playlist by url
- Control features
- Automatic sharding
- Easy self-hosting with Docker

## Slash Commands

- `/about` - To see bot info
- `/fast-forward` - To fast-forward the player
- `/pause-resume` - To pause or resume the player
- `/play` - To play a song or playlist in ur voice channel
- `/replay` - To replay the current song
- `/rewind` - To rewind the player
- `/skip` - To skip the current song
- `/stop` - To stop the player

## Buttons

`PAUSE-RESUME`, `SKIP`, `STOP`,  
`REPLAY`, `FAST-FORWARD`, `REWIND`

## Screenshot

![Bot Screenshot](https://cdn.jsdelivr.net/gh/itsanikethere/botler@master/assets/screenshot.png)

## Required Intents

- Message Content Intent

## Required Permissions

- Read Messages
- Send Messages
- Embed Links
- Connect
- Speak
- Use Voice Activity
- Priority Speaker
- Request to Speak

## Running

You can either run Botler with Docker (recommended) or directly with JDK.

> [!WARNING]
> [Required Intents](#Required-Intents) must be enabled, and [Required Permissions](#Required-Permissions) must be given in order to make Botler work properly.

### 🐳 Docker

Pull the docker image

```bash
docker pull itsanikethere/botler
```

Run the docker image

```bash
docker run -e BOT_TOKEN=BOT_TOKEN_HERE itsanikethere/botler
```

### ☕ JDK

Download the latest JAR file from [releases](https://github.com/itsanikethere/botler/releases)

Run the JAR

```bash
java -jar botler.jar BOT_TOKEN_HERE
```

## Links

- **[Support Server](https://discord.gg/r46UaGKkuE)**

## License

Botler is distributed under the [MIT](https://github.com/itsanikethere/botler/blob/master/LICENSE)
