package com.galid.hem.appserver.model.extension

import org.bson.types.ObjectId

fun String.toObjectId(): ObjectId = ObjectId(this)

