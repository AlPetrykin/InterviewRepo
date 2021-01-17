package ua.alexp.redditinterview.models

data class DistPost (
    val modhash : String,
    val dist : Int,
    val children : List<ChildrenPost>,
    val after : String,
    val before : String?
)