package com.trinary.tcg.cards

class VanguardCard {
	String name
        String clan
        String race
	Integer grade
	Integer power
	Integer crit
	Integer shield
	
	String artworkFile
	
	TriggerEffect trigger
	
	public String toString() {
		return "${name}(Grade ${grade})"
	}
	
	static def hasMany = [effects: Effect]

    static constraints = {
        artworkFile nullable: true
        trigger nullable: true
        shield nullable: true
    }
    
    static mapping = {
        effects sort: 'id'
    }
}
