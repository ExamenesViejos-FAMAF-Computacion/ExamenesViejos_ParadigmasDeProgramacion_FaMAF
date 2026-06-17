# RSS Feed Parser

A simple RSS feed parser written in Scala for educational purposes.

## Prerequisites

### Installing Scala

#### macOS (using Homebrew)
```bash
brew install coursier/formulas/coursier
cs setup
```

#### Linux
```bash
curl -fL https://github.com/coursier/coursier/releases/latest/download/cs-x86_64-pc-linux.gz | gzip -d > cs
chmod +x cs
./cs setup
```

#### Windows
Download and run the installer from: https://www.scala-lang.org/download/

### What gets installed
The `cs setup` command installs:
- Scala 3 compiler
- sbt (Scala Build Tool)
- Java JDK (if not already installed)

## Building and Running

> **Note:** You may see Java warnings about deprecated APIs. These are harmless and can be ignored, or suppressed using the included `.jvmopts` file.

### Compile the project
```bash
sbt compile
```

### Run the project
```bash
sbt run
```

### Run in interactive mode
```bash
sbt
> run
```

### Create a standalone executable (optional)
```bash
sbt package
scala target/scala-3.3.1/rss-parser_3-0.1.0.jar
```

## Project Structure
```
lab1/
├── build.sbt                 # Build configuration
├── src/
│   └── main/
│       └── scala/
│           └── Main.scala    # Main application entry point
└── README.md                 # This file
```

## Next Steps
- [ ] Implement RSS feed parsing
- [ ] Add XML processing
- [ ] Create feed subscription management
