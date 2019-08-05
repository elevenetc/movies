# Movies App
## Setup
Add Google API file with key `/google_key.properties`:

```
api.key=[key]
```

Add The Movie Database file with key `/movies_db.properties`:

```
api.key=[key]
```

## Build
```
./gradlew assembleDebug
```

## Features

Implemented:

- Popular movies screen
- Api search by movie title
- Popular movies offline mode / caching
- Details movie screen
- Trailer viewer screen

Not implemented / could be improved:

- Tests
- Key-Value cache could be replaced with SQLite database
- Error cases (user messages, retry mechanics)
- Search query caching
- Network requests layer caching