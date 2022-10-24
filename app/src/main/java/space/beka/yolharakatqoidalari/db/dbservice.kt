package space.beka.yolharakatqoidalari.db

import space.beka.yolharakatqoidalari.models.Rule

interface dbservice {
    fun addLabel(belgi: Rule)
    fun editLabel(belgi: Rule):Int
    fun deleteLabel(belgi: Rule)
    fun getAllLabel():ArrayList<Rule>
}