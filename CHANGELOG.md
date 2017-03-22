

RestInParse 0.0.8
===

Rest In Parse now supports Batch operations 

It works by creating a `stream` of data returned by  `ParseTable.batchUpdate()`, `ParseTable.batchCreate()` and `ParseTable.batchDelete()`

You then call `RestInParse.batchExecutor(stream)` which will execute up to 20 operations for each http request


RestInParse 0.0.7
===

- Implements `Parse.Query.count()`
- New criteria `Parse.Query.isTrue()` and `Parse.Query.isNotTrue()` which search for either `false` `null` or `undefined`
- New criteria `notEqualToInt()`, `notEqualToPtr`, `notEqualToString`
- query.findAll() now fetch objects 1000 by request

RestInParse 0.0.6
===

- query.limit(limit).find() issue a single http request that fetch at most 1000 objects
- query.findAll() continue to query Parse until it find all objects (ordered by createdAt DESC)
- Addd ParseObject.createdAt() and ParseObject.updatedAt()
- Query.find() now rethrow Parse errors