package de.blackforrestdevelopment.sample.webapp.common

interface SimpleDataObject<T : BusinessObject<*>> : DataObject {
    fun convert(): T

}
