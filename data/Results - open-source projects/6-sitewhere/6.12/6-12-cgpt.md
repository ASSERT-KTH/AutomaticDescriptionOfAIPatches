In this change, the serverSelectionTimeout property of the MongoClientOptions.Builder is set to -1, which means that the client will wait indefinitely for server selection. This is done in the MongoDbClient abstract class constructor.