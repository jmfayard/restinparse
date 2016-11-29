
0.06
===

- query.limit(limit).find() issue a single http request that fetch at most 1000 objects
- query.findAll() continue to query Parse until it find all objects (ordered by createdAt DESC)
- Addd ParseObject.createdAt() and ParseObject.updatedAt()
- Query.find() now rethrow Parse errors