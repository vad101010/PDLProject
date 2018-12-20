# Wikipedia Matrix

A simple command line, java based, Wikipedia table extractor.

## Introduction

These introduction will tell you more about the objectives and the results that will get by using this software.

### Objective

The software aim to return you as much table as possible from a wikipedia page that you provide to it in CSV format. It will try to exclude the table that cannot be parsed into CSV and inform you about how much table it can parse.

### Results

If you give a wikipedia page link that contains tables, the software will generate you the CSV for every valid tables in the page and exclude the ones that cannot be converted into CSV.

If you give a wikipedia page link that doesn't contains any valid tables or no tables at all, the software will inform you of this and won't generate any file.

If you give any other link than wikipedia, the software will tell you that the page you provide is not compatible and won't generate any file.

At every steps you can check the list of link that you provided to the software and check if and how many table it can extract.

## Getting Started

These instructions will show you how to get a working copy of the project to allow you to run and test de software.

### Prerequisites

You will need a java compiler to compile and run the program properly. Alternatively you can choose one the bellow IDE to make it easier.

```
IntelliJ IDEA
Eclipse
NetBeans
```

### Installing

Depending on the IDE that you chose, you will have to import the pom.xml file to your project. This will allow Maven to automatically import the missing libraries to properly run the project.

Everything is now set and you are ready to run the program.

## Running the tests

The Tests are located under:

```
src/test/java/fr/istic
```

You can simply run them with your favorite IDE.

### Client Benchmark

```
src/test/java/fr/istic/BenchTest.java
```
 This is the class that the client asked us to add and implement into our project. You can use it to BenchMark the software with 300 Wikipedia Links.

## Deployment

No deployment of the project is planned on client servers/computers as the client didn't specifically asked to do it. The project can only be used via console commands.

## Built With

* [IntelliJ IDEA](https://www.jetbrains.com/idea/) - The IDE mainly used by our crew.
* [Maven](https://maven.apache.org/) - Dependency Management.
* [Sweble-Wikitext](https://github.com/sweble/sweble-wikitext) - The java based WikiText Parser.
* [Astah](http://astah.net/) - The UML editor.
* [Word](products.office.com/Microsoft/Office‎
) - The document editor used to create the specifications.

## Contributing

Every members of our crew had the opportunity to contribute as much as they want to there tasks, as long as they are assigned to it in the [Roadmap](https://github.com/vad101010/PDLProject/projects/1).

## Versioning

We aimed to release the version 1.0 were everything is supposed to work perfectly (project main tasks done and tested). We started on version 0.0.1 with the following update schema:
```
(X.Y.Z) where X, Y and Z are numbers e.g. 0.2.5
```
Every small update increment the Z number by one and a major update (with tested and working main functionality done) increment the Y number by 1. Finally only if every features are done the X number is incremented and it can only be incremented again if the client ask new features and if we implement them. You can find every versions changelog in the [CHANGELOG.md file](https://github.com/vad101010/PDLProject/blob/master/CHANGELOG.md)

## Authors

* **Kupratsevitch Vadim** - *Team leader - Java/UML developer* - [vad101010](https://github.com/vad101010)
* **Wacquet Adrien** - *Java/UML developer - External components integrator* - [awacquet](https://github.com/awacquet)
* **Legroux Thibaut** - *Java developer - Specification writer* - [thigroux](https://github.com/thigroux)
* **Mande Guillaume** - *Java developer - Specification writer* - [guiMande](https://github.com/guiMande)
* **Scrimali Gaetan** - *Java developer - Specification writer* - [Gueguette](https://github.com/Gueguette)

## License

This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/vad101010/PDLProject/blob/master/LICENSE.md) file for details.
