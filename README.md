# ImageRank

In supervised learning, one approach of analyzing follows these steps:

1. Have a dataset of images and its classes. For example, a dog is part of a particular breeed.
2. Extracts a derived vector from the pixels of the image. 
The generated multi-dimentional data is called feature vector and the process feature extraction.
The algorithm that transforms the image pixels to vector is called feature extractor.
3. Calculate the proximity of one feature related to the features of the same class.
The comparison is given by a distance function and the query is a k-NN query, i.e find the given the k neighbors independent of the class.
4. Based on the query results, check and the effectiveness of the extractor or the distance function in a graphical way. 
A precision vs.recall graph gives you the ratio _between false positives and false negatives_.

Based on this context, a researcher job is to find the best distance function and feature extractor that will cluster their future images to its belonging class.
This project helps you to introduce new extractors and distance functions and compare them easily among each other.

All of the extractions, queries and query results are stored in a Postgres database, so you don't need to recalculate all of the processes every time a new extractor or distance function is introduced.
Let's say that this project aims to be a EQP (Extract-Query-Plot) tool.

This project is called ImageRank, but it's not specific to images, so you can skip step 1 and store the features directly into the database.

<!-- But then you're trying to -->

<!-- This project aims to do just that. -->

<!-- Classifica os melhores extratores de características e funções de distância baseado no seu conjunto de dados. -->


        
## Initial motivation
Developed by me and [Pedro Tanaka](https://github.com/pedro-stanaka) for the digital signal processing course during our masters program.

The project was initially used to write a paper, which given a dataset of lung X-Rays clustered by the diseases, we would compare the [Wavelet](Put link) extractor with another paper that used histograms to identify the diseases.

One thing led to another and the abstraction bug bit us. 
So, ImageRank was created to yield the results in an automated fashion with no tight coupling to X-Rays, wavelets or any particular distance function.

The resulting paper is here (Link) (written in portuguese).
        
## Built-in extractors and distance functions

Refer to the seed file to check the supported

Wavelet and some features from JFeatureLib are supported

Distance functions

## Stages

## 0. Building the project
You need to have these installed:
- Java 8
- GNUPlot

### Running tests
``` shell
# Use
export TEST_DB=imagerank_test
export JDBC_URL="jdbc:postgresql://localhost/$TEST_DB?user=postgres&password=postgres"

psql -U postgres -h localhost imagerank < sql/schema-creation.sql

# Run the tests
mvn test
```

### Assembling the project

``` shell
export DB=imagerank
export JDBC_URL="jdbc:postgresql://localhost/$DB?user=postgres&password=postgres"
# Run the schema
psql -U postgres -h localhost imagerank < sql/schema-creation.sql
# Load distance function and extractors metadata
psql -U postgres -h localhost imagerank < sql/seeds.sql

Build a fat JAR with all the dependencies
mvn assembly:assembly -DskipTests
```

### 1. Persisting the images
Loading the images and its classes.

The directory is the name of the dataset and the class is extracted from the name.
In this example, there are 3 different classes Chihuahua, Groenendael and Saluki.

``` 
Dogs
├── Chihuahua_n02085620_199.jpg
├── Chihuahua_n02085620_242.jpg
├── Groenendael_n02105056_933.jpg
├── Groenendael_n02105056_961.jpg
└── Saluki_n02091831_97.jpg

java -jar target/image-wavelet-1.0-jar-with-dependencies.jar --image-extraction --images-path <path>
```
       
### 2. Feature extraction
Extracts the features from the images and saves the result into the `extractions` table.

``` shell
# Run all extractions. The time it takes is (extractors * number of images)
java -jar target/image-wavelet-1.0-jar-with-dependencies.jar --feature-extraction --all-extractors

# Run single extraction
java -jar target/image-wavelet-1.0-jar-with-dependencies.jar --feature-extraction --extractor-feature-id=1
java -jar target/image-wavelet-1.0-jar-with-dependencies.jar --feature-extraction --extractor-feature-id=100
```

Refer to the seeds file to get the [extractor id](https://github.com/gjhenrique/ImageRank/blob/3efaa5c2535a530fefbfca6636edc042ed53ee89/sql/seeds.sql#L2).

### 3. k-NNS queries

The cost for querying the whole database is `O(number of distance functions * number of extractions * rate of k)`.
By default, the tool performs 20 queries ranging from `5-nn` to `100-nn`. This can be configured by the `--rate-k` and `--max-k`

Depending of the dataset size, it might take a while to finish. A thread pool is used to parallelize the queries.

``` shell
java -jar target/image-wavelet-1.0-jar-with-dependencies.jar --knn-queries --all-extractions --all-distance-functions
java -jar target/image-wavelet-1.0-jar-with-dependencies.jar --knn-queries --extractor-query-id=100 --all-distance-functions
java -jar target/image-wavelet-1.0-jar-with-dependencies.jar --knn-queries --extractor-query-id=100 --distance-function-id=1
```

Refer to the seeds file to get the [extractor id](https://github.com/gjhenrique/ImageRank/blob/3efaa5c2535a530fefbfca6636edc042ed53ee89/sql/seeds.sql#L2) and [distance function id](https://github.com/gjhenrique/ImageRank/blob/3efaa5c2535a530fefbfca6636edc042ed53ee89/sql/seeds.sql#L40).

### 4. Plotting the queries

Based on the previous queries, a Precision-Recall curve chart is generated with GNUPlot.

Depending on the argument, 

https://blog.floydhub.com/a-pirates-guide-to-accuracy-precision-recall-and-other-scores/#precision

## Future endeavors

Since this project was aimed to write a paper that compares the introduction of Wavelets with another work plotting Precision vs. Recall, the design

It might be tiresome to analyze every extractor and distance function.
The idea in the future is to, based on some existing dataset, return the best extractor and distance function based on the Prec

## Commands
Java 8 is required
Maven
jOOQ classes are generated automatically
gnuplot installed
Guice for dependency injection

Loading database

Running tests

Bundling into a single jar


## Libraries
* [jOOQ](http://www.jooq.org)
* [JWave](https://github.com/pedro-stanaka/JWave)
* [Guice](https://github.com/google/guice)
* [JavaPlot](http://javaplot.panayotis.com/)
* [ImageJ](https://imagej.net/)
       

## New Readme

Add build instructions
Add run instructions
Add test instructions

New pictures about

java8 is required

gnuplot installation is required

This work was used to identify X-rays from lungs of different diseases.


EQP


psql -U postgres -h localhost imagerank < sql/schema-creation.sql
psql -U postgres -h localhost imagerank < sql/seeds.sql

java -jar target/image-wavelet-1.0-jar-with-dependencies.jar --image-extraction --images-path src/test/resources/imgs/dicom/Pulmao

# java -jar target/image-wavelet-1.0-jar-with-dependencies.jar --feature-extraction --all-extractors
java -jar target/image-wavelet-1.0-jar-with-dependencies.jar --feature-extraction --extractor-feature-id 1
java -jar target/image-wavelet-1.0-jar-with-dependencies.jar --feature-extraction --extractor-feature-id 100
java -jar target/image-wavelet-1.0-jar-with-dependencies.jar --feature-extraction --extractor-feature-id 101

# java -jar target/image-wavelet-1.0-jar-with-dependencies.jar --knn-queries --all-extractions --all-distance-functions
java -jar target/image-wavelet-1.0-jar-with-dependencies.jar --knn-queries --extractor-query-id=1 --all-distance-functions
java -jar target/image-wavelet-1.0-jar-with-dependencies.jar --knn-queries --extractor-query-id=100 --all-distance-functions
java -jar target/image-wavelet-1.0-jar-with-dependencies.jar --knn-queries --extractor-query-id=101 --all-distance-functions

java -jar target/image-wavelet-1.0-jar-with-dependencies.jar --precision-recall --precision-recall-extractor-id=1
java -jar target/image-wavelet-1.0-jar-with-dependencies.jar --precision-recall --precision-recall-distance-function=1

Features
https://github.com/locked-fg/JFeatureLib/tree/888d0d9f36381624cef28165bf19c0af022a10d1/src/main/java/de/lmu/ifi/dbs/jfeaturelib/features
