# keywords

A library for making it a bit easier to work with maps of heterogenous data in Scala

## Motivation

On a recent project my teammates and I found ourselves with:
* a number of web services...
* communicating by passing data represented as JSON...
* serialized using the [lift-json](https://github.com/lift/lift/tree/master/framework/lift-base/lift-json) library
* the content of which was principally lists of objects...
* each of which usually contained lots of info that was extraneous to whatever the service was immediately doing
* and where the shape of the uninteresting data tended to change very frequently during the dev cycle

Our original approach was to deserialise the JSON to case classes. However we found this approach tedious under the conditions. Only a relatively small proportion of the members in the data were of interest to the services, and much of the uninteresting data was changing shape frequently. So every other day we were adding, removing or renaming the members on our case classes - and often enough this occurred with the members we cared not at all about. 

After exploring a few different approaches we found ourselves settling on converting our data (mostly deserialized to JArrays of JObjects) to Seqs of Map[String, Any]s. This worked well while since many of the keys we never actually accessed, though it did have a few downfalls. It led to a lot of code like this:

```scala
foo("name").asInstanceOf[String]
```

or

```scala
foo.get("name").map(_.asInstanceOf[String])
```

and also meant that we missed out on a lot of documentation that the types would otherwise provide. This library came about as a way to provide a bit more 'typed-ness' - a place to hide the type-testing, -casting and debugging, and the ability to declare what we expect the shape of the data to be. It was inspired by the [LazyRecords](https://code.google.com/p/lazyrecords) of Dan Bodart and friends and [core.typed](https://github.com/clojure/core.typed) feature of typing heterogenous maps; and was evolved by lots of good feedback from Dan Bodart along the way.  
