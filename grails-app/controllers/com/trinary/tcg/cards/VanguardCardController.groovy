package com.trinary.tcg.cards

import org.springframework.dao.DataIntegrityViolationException
import com.tcg.generator.cards.GenericCard
import grails.converters.JSON

class VanguardCardController {
    def grailsApplication

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = 4
        [vanguardCardInstanceList: VanguardCard.list(params), vanguardCardInstanceTotal: VanguardCard.count()]
    }

    def create() {
        [vanguardCardInstance: new VanguardCard(params)]
    }

    def save() {
        List list = params.effects.split("~")
        params.remove("effects")
        
        def vanguardCardInstance = new VanguardCard(params)
        list.each { effectText ->
            if (effectText != "") {
                vanguardCardInstance.addToEffects(new Effect(text: effectText.trim()))
            }
        }
        if (!vanguardCardInstance.save(flush: true)) {
            render(view: "create", model: [vanguardCardInstance: vanguardCardInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'vanguardCard.label', default: 'VanguardCard'), vanguardCardInstance.id])
        redirect(action: "show", id: vanguardCardInstance.id)
    }

    def show(Long id) {
        def vanguardCardInstance = VanguardCard.get(id)
        if (!vanguardCardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'vanguardCard.label', default: 'VanguardCard'), id])
            redirect(action: "list")
            return
        }

        [vanguardCardInstance: vanguardCardInstance]
    }

    def edit(Long id) {
        def vanguardCardInstance = VanguardCard.get(id)
        if (!vanguardCardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'vanguardCard.label', default: 'VanguardCard'), id])
            redirect(action: "list")
            return
        }

        [vanguardCardInstance: vanguardCardInstance]
    }

    def update(Long id, Long version) {
        def vanguardCardInstance = VanguardCard.get(id)
        if (!vanguardCardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'vanguardCard.label', default: 'VanguardCard'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (vanguardCardInstance.version > version) {
                vanguardCardInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'vanguardCard.label', default: 'VanguardCard')] as Object[],
                          "Another user has updated this VanguardCard while you were editing")
                render(view: "edit", model: [vanguardCardInstance: vanguardCardInstance])
                return
            }
        }
        
        List list = params.effects.split("~")
        List l = []
        l += vanguardCardInstance.effects
        l.each { effect ->
            vanguardCardInstance.removeFromEffects(effect)
            effect.delete()
        }
        list.each { effectText ->
            if (effectText != "") {
                vanguardCardInstance.addToEffects(new Effect(text: effectText.trim()))
            }
        }
        
        params.remove("effects")

        vanguardCardInstance.properties = params

        if (!vanguardCardInstance.save(flush: true)) {
            render(view: "edit", model: [vanguardCardInstance: vanguardCardInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'vanguardCard.label', default: 'VanguardCard'), vanguardCardInstance.id])
        redirect(action: "show", id: vanguardCardInstance.id)
    }

    def delete(Long id) {
        def vanguardCardInstance = VanguardCard.get(id)
        if (!vanguardCardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'vanguardCard.label', default: 'VanguardCard'), id])
            redirect(action: "list")
            return
        }

        try {
            vanguardCardInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'vanguardCard.label', default: 'VanguardCard'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'vanguardCard.label', default: 'VanguardCard'), id])
            redirect(action: "show", id: id)
        }
    }
    
    def generate(long id) {
        VanguardCard vcard = VanguardCard.get(id)
		
        List effects = vcard.effects.collect { it.text }
        String trigger = vcard.trigger?.name

        Map map = [:]

        map["grade"] = vcard.grade
        map["cardName"] = vcard.name
        map["clan"] = vcard.clan
        map["race"] = vcard.race
        map["power"] = vcard.power
        map["crit"] = vcard.crit
        map["shield"] = vcard.shield
        if (effects.size > 0) {
                map["effects"] = effects
        }
        map["trigger"] = trigger

        GenericCard card = new GenericCard()
        card.setCardData((map as JSON) as String)
                .setCardName(vcard.name)
                .setLayout(new File("${grailsApplication.config.root}layouts/vanguard.layout"))
                .setArtwork(new File("${grailsApplication.config.root}art/${vcard.artworkFile}"))

        card.draw(response.outputStream)

        return
    }
}
