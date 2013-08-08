package com.trinary.tcg.cards

class Effect {
	String text
	
	public String toString() {
		return "${text}"
	}
	
	static def belongsTo = [card: VanguardCard]

    static mapping = {
        sort: 'id'
    }
}
