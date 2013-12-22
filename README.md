# keywords

A library for making it a bit easier to work with maps of heterogenous data in Scala

## Motivation

On a recent project my teammates and I found ourselves with:
* a number of web services...
* communicating by passing data represented as JSON...
* serialized using the [lift-json]: https://github.com/lift/lift/tree/master/framework/lift-base/lift-json library
* the content of which was principally lists of objects...
* each of which usually contained lots of info that was extraneous to whatever the service was immediately doing
* and where the shape of the uninteresting data tended to change very frequently during the dev cycle

Our original approach was to deserialise the JSON to case classes. However we found this approach tedious under the conditions. Only a relatively small proportion of the members in the data were of interest to the services, and much of the uninteresting data was changing shape frequently. So every other day we were adding, removing or renaming the members on our case classes - and often enough this occurred with the members we cared not at all about. 