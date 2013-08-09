package com.trinary.tcg.cards

import org.springframework.dao.DataIntegrityViolationException
import com.trinary.tcg.FileService

class ImageController {
    def grailsApplication
    
    FileService fileService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [imageInstanceList: Image.list(params), imageInstanceTotal: Image.count()]
    }

    def create() {
        [imageInstance: new Image(params)]
    }

    def save() {
        def imageInstance = new Image(params)
        
        def f = request.getFile('uploadFile')
        def localPath = fileService.store(f)
        
        imageInstance.localPath = localPath
        imageInstance.invalid = fileService.validateImage(grailsApplication.config.root + "art/" + localPath, 327, 477)
        
        if (!imageInstance.save(flush: true)) {
            render(view: "create", model: [imageInstance: imageInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'image.label', default: 'Image'), imageInstance.id])
        redirect(action: "show", id: imageInstance.id)
    }

    def show(Long id) {
        def imageInstance = Image.get(id)
        if (!imageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'image.label', default: 'Image'), id])
            redirect(action: "list")
            return
        }

        [imageInstance: imageInstance]
    }

    def edit(Long id) {
        def imageInstance = Image.get(id)
        if (!imageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'image.label', default: 'Image'), id])
            redirect(action: "list")
            return
        }

        [imageInstance: imageInstance]
    }

    def update(Long id, Long version) {
        def imageInstance = Image.get(id)
        if (!imageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'image.label', default: 'Image'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (imageInstance.version > version) {
                imageInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'image.label', default: 'Image')] as Object[],
                          "Another user has updated this Image while you were editing")
                render(view: "edit", model: [imageInstance: imageInstance])
                return
            }
        }

        imageInstance.properties = params

        if (!imageInstance.save(flush: true)) {
            render(view: "edit", model: [imageInstance: imageInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'image.label', default: 'Image'), imageInstance.id])
        redirect(action: "show", id: imageInstance.id)
    }

    def delete(Long id) {
        def imageInstance = Image.get(id)
        if (!imageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'image.label', default: 'Image'), id])
            redirect(action: "list")
            return
        }

        try {
            imageInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'image.label', default: 'Image'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'image.label', default: 'Image'), id])
            redirect(action: "show", id: id)
        }
    }
    
    def fix(Long id) {
        [id: id]
    }
    
    def crop(Long id) {
        println "In crop()"
        println "JSON:   ${request.JSON}}"
        println "ID:     ${id}"
        println "PARAMS: ${params}}"
        
        Image image = Image.get(id)
        String path = fileService.crop(grailsApplication.config.root + "art/" + image.localPath, params.x.toInteger(), params.y.toInteger(), params.width.toInteger(), params.height.toInteger(), 327, 477)
        
        if (!path) {
            render "FAIL"
            return
        }
        
        image.localPath = path
        image.invalid = false
        
        image.save()
        
        render "OKAY"
    }
    
    def get(Long id) {
        def image = Image.get(id)

        if (!image || !image.localPath) {
            response.status = 404
            return
        }

        if (!fileService.writeImage(grailsApplication.config.root + "art/" + image.localPath, response.outputStream)) {
            response.status = 404
            return
        }

        return
    }
}
